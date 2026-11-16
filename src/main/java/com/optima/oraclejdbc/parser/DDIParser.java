package com.optima.oraclejdbc.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.daoimpl.BOBlockHardcopyDao;
import com.optima.oraclejdbc.daoimpl.DDIDao;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.db.DBThreadFactory;
import com.optima.oraclejdbc.exception.NISPIllegalAccessException;
import com.optima.oraclejdbc.exception.NISPInstantiationException;
import com.optima.oraclejdbc.exception.NISPInvocationTargetException;
import com.optima.oraclejdbc.exception.NISPNoSuchMethodException;
import com.optima.oraclejdbc.exception.NISPNumberFormatException;
import com.optima.oraclejdbc.exception.NISPParseException;
import com.optima.oraclejdbc.exception.NISPValueLengthNotMatchException;
import com.optima.oraclejdbc.exception.NISPValueNotValidException;
import com.optima.oraclejdbc.model.DDMastAccountList;
import com.optima.oraclejdbc.model.backoffice.BOBlockHardcopy;
import com.optima.oraclejdbc.model.estatement.DDIModel;
import com.optima.oraclejdbc.model.estatement.ESAccountInfo;
import com.optima.oraclejdbc.model.estatement.ESDdmast;
import com.optima.oraclejdbc.model.estatement.ESDdyAccount;
import com.optima.oraclejdbc.model.estatement.ESGiro;
import com.optima.oraclejdbc.model.estatement.ESHGiro;
import com.optima.oraclejdbc.model.estatement.ESHMCBilingual;
import com.optima.oraclejdbc.model.estatement.ESHMultiCurrency;
import com.optima.oraclejdbc.model.estatement.ESMCBilingual;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrency;
import com.optima.oraclejdbc.model.estatement.ESOdtier;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.FileUtility;
import com.optima.oraclejdbc.utils.MailProcessing;

@Service
public class DDIParser extends NISPParser<DDIModel> {

	private static final Logger logger = Logger.getLogger(DDIParser.class);
	
	@Autowired
	DDIDao dao;
	
	@Autowired
	DDIDao ddiDao;
	
	@Autowired
	private MailProcessing mailProcessing;
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	BOBlockHardcopyDao blockHcDao;
	
	private List<DDIModel> listInfo;
	
	@Override
	public void setFilename(String inputFilename) throws IOException {
		File inputFile = new File(inputFilename);
		String folderInput = inputFile.getParentFile().getParent() + CommonCons.SEPARATOR + "input";
		
		List<String> entries = unzip(inputFilename);
		if( entries == null )
			return;
		List<String> datas = new ArrayList<String>();
		List<String> listLine = new ArrayList<String>();
		boolean success[] = new boolean[1];
		success[0] = true;
		
		for (String filename : entries) {
			boolean canAccess = false;
			int startNumber = 0;
			do{
				FileInputStream is = null;
				Scanner sc = null;
				try {
					is = new FileInputStream(filename);
					sc = new Scanner(is, "UTF-8");
					
					System.out.println("readfile: " + filename);
					int counter = 0;
					
					String accNo = "";
					String accNoTemp = "";
					boolean accNoChange = false;
					
					System.out.println(new Date().getTime());
					boolean isNotSaving = filename.contains("DDI236MTX") 
							|| filename.contains("DDI236TXT") 
							|| filename.contains("DDI236MBL");
					while (sc.hasNextLine()) {
						String currentLine = sc.nextLine();
						if (currentLine.length() <= 1) {
							continue;
						}
						
						counter++;
						if ( isNotSaving ) {
							
							int bgnIdx = CommonCons.ES_BEGIN_IDX_ACC_NO;
							int endIdx = CommonCons.ES_END_IDX_ACC_NO;
							
							if (counter == 10000) {
								accNo = currentLine.substring(bgnIdx, endIdx);
								
								if (filename.contains("DDI236MTXS")) {
									accNo = this.paddingAccNoMcy(accNo);
								} else if (filename.contains("DDI236TXTS")) {
									accNo = this.paddingAccNoGiro(accNo);
								}
								
								accNoChange = false;
							} else if (counter > 10000) {
								accNoTemp = currentLine.substring(bgnIdx, endIdx);
								
								if (filename.contains("DDI236MTXS")) {
									accNoTemp = this.paddingAccNoMcy(accNoTemp);
								} else if (filename.contains("DDI236TXTS")) {
									accNoTemp = this.paddingAccNoGiro(accNoTemp);
								}
								
								if (!accNo.equals(accNoTemp)) {
									accNoChange = true;
								}
							}
							
							if (accNoChange) {
								List<String> finalData = new ArrayList<String>();
								finalData.addAll(datas);
								processData(filename, finalData, success, listLine, startNumber);
								accNoChange = false;
								datas.clear();
								startNumber+=counter;
								counter = 1;
								datas.add(currentLine);
							} else {
								datas.add(currentLine);
							}
						} else {
							datas.add(currentLine);
							if (counter == 10000) {
								List<String> finalData = new ArrayList<String>();
								finalData.addAll(datas);
								processData(inputFilename,finalData, success, listLine, startNumber);
								datas.clear();
								startNumber+=10000;
								counter = 0;
							} 
						}
					}
					
					processData(inputFilename, datas, success, listLine, startNumber);
					synchronized (DBThreadFactory.intances().getSyncObject()) {
						DBThreadFactory.intances().getSyncObject().wait();
					}
					synchronized (DBThreadFactory.intances().getSyncObject()) {
						DBThreadFactory.intances().getSyncObject().wait();
					}
					System.out.println(new Date().getTime());
					canAccess=true;
				} catch (Exception e) {
					if( e instanceof FileNotFoundException && e.getMessage().contains("being used by another process") ){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							logger.error("Error", e1);
						}
					}else{
						logger.error("Error", e);
					}
				} finally {
					try {
						if (is != null) {
							is.close();
							File df = new File(filename);
							if( df.exists() )
								df.delete();
						}
						
						if (sc != null) {
							sc.close();
						}
					} catch (IOException ex) {
						logger.error("Error", ex);
					}
				}
			} while (!canAccess);
		}
		
		if (success[0]) {
        	FileUtility.moveFileToSuccess(inputFilename);
    	} else {
    		FileUtility.moveFileToFailedPartial(inputFilename, listLine, mailProcessing, confProperties, sysParamDao);
    	}
		
		//disini utk antrian ddi
		//harus scan folder input
		boolean odtierExist = false;
		boolean ddyExist = false;
		boolean ddiExist = false;
		
		String fileOdtier = "";
		String fileDdy = "";
		String fileDdi = "";
				
		File folder = new File(folderInput);
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File item : files) {
				if (item.getName().contains(".CHK")) {
					if (item.getName().contains("ODTIER")) {
						odtierExist = true;
						fileOdtier = item.getPath();
					} else if (item.getName().contains("DDYMOW")) {
						ddyExist = true;
						fileDdy = item.getPath();
					} else if (item.getName().contains("DDI")) {
						ddiExist = true;
						fileDdi = item.getPath();
					}
				}
			}
		}
		
		if (odtierExist) {
			//process file odtier
			if (!fileOdtier.equals("")) {
				String fileDat = fileOdtier.replace(".CHK", ".DAT");
				String input = FileUtility.moveFileToPending(fileOdtier, fileDat);
				this.setFilename(input);
			}
		} else if (ddyExist) {
			//process file ddy
			if (!fileDdy.equals("")) {
				String fileDat = fileDdy.replace(".CHK", ".DAT");
				String input = FileUtility.moveFileToPending(fileDdy, fileDat);
				this.setFilename(input);
			}
		} else if (ddiExist) {
			//process file ddi
			if (!fileDdi.equals("")) {
				String fileDat = fileDdi.replace(".CHK", ".DAT");
				String input = FileUtility.moveFileToPending(fileDdi, fileDat);
				this.setFilename(input);
			}
		}
	}

	@Override
	public List<DDIModel> extract(List<String> datas, boolean[] success, List<String> listLine, String filename, int startNumber) {
		List<DDIModel> rd = new ArrayList<DDIModel>();
		listInfo = new ArrayList<DDIModel>();
		List<DDMastAccountList> ddMastAccountList = new ArrayList<DDMastAccountList>();
		try {
			int line = 1;
			String accountNoTemp = "";
			
			for (String item : datas) {
				try {
					if (item.length() <= 1 && line == datas.size()) {
						continue;
					}
					
					DDIModel data = null;
					if (filename.contains("DDI236MTXS")) {
						//mcy
						data = extract(item, ESMultiCurrency.class);
						
						String accountNumber = ((ESMultiCurrency) data).getAccountNumber();
						accountNumber = this.paddingAccNoMcy(accountNumber);
						accountNumber = CommonUtils.normalizeAccNo(accountNumber);
						((ESMultiCurrency) data).setAccountNumber(accountNumber);
						
						if (!accountNoTemp.equals(accountNumber)) {
							accountNoTemp = accountNumber;
							
							ESAccountInfo info = ddiDao.getEsAccountInfo(accountNumber);
							if (info != null) {
								ESAccountInfo updateInfo = new ESAccountInfo();
								updateInfo.setCifNo(info.getCifNo());
								String email = ((ESMultiCurrency) data).getElecAddrTypeDesc();
								if (email == null) {
									updateInfo.setEmail("");
								} else {
									updateInfo.setEmail(email);
								}
								listInfo.add(updateInfo);
							}
						}
					} else if (filename.contains("DDI236TXTS")) {
						//giro
						data = extract(item, ESGiro.class);
						
						String accountNumber = ((ESGiro) data).getNoRekening();
						accountNumber = this.paddingAccNoGiro(accountNumber);
						accountNumber = CommonUtils.normalizeAccNo(accountNumber);
						((ESGiro) data).setNoRekening(accountNumber);
						
						if (!accountNoTemp.equals(accountNumber)) {
							accountNoTemp = accountNumber;
							
							ESAccountInfo info = ddiDao.getEsAccountInfo(accountNumber);
							if (info != null) {
								ESAccountInfo updateInfo = new ESAccountInfo();
								updateInfo.setCifNo(info.getCifNo());
								String email = ((ESGiro) data).getElecAddrTypeDesc();
								if (email == null) {
									updateInfo.setEmail("");
								} else {
									updateInfo.setEmail(email);
								}
								listInfo.add(updateInfo);
							}
						}
					} else if (filename.contains("DDI236MBLS")) {
						//mcb
						data = extract(item, ESMCBilingual.class);
						
						String accountNumber = ((ESMCBilingual) data).getNoRekening();
						accountNumber = this.paddingAccNoMcy(accountNumber);
						accountNumber = CommonUtils.normalizeAccNo(accountNumber);
						
						((ESMCBilingual) data).setNoRekening(accountNumber);
						
						if (!accountNoTemp.equals(accountNumber)) {
							accountNoTemp = accountNumber;
							
							ESAccountInfo info = ddiDao.getEsAccountInfo(accountNumber);
							if (info != null) {
								ESAccountInfo updateInfo = new ESAccountInfo();
								updateInfo.setCifNo(info.getCifNo());
								String email = ((ESMCBilingual) data).getElectronicAddressDesc();
								if (email == null) {
									updateInfo.setEmail("");
								} else {
									updateInfo.setEmail(email);
								}
								listInfo.add(updateInfo);
							}
						}
					} else if (filename.contains("ODTIER")) {
						data = extract(item, ESOdtier.class);
						
						String accountNumber = ((ESOdtier) data).getAccountNumber();
						if (accountNumber.length() == 11) {
							accountNumber = "0" + accountNumber;
						} else if (accountNumber.length() == 13) {
							accountNumber = accountNumber.substring(1);
						} else if (accountNumber.length() == 14) {
							accountNumber = accountNumber.substring(2);
						}
						
						((ESOdtier) data).setAccountNumber(CommonUtils.normalizeAccNo(accountNumber));
					} else if (filename.contains("DDYMOW")) {
						data = extract(item, ESDdyAccount.class);
						
						String accountNumber = ((ESDdyAccount) data).getAccountNumber();
						if (accountNumber.length() == 11) {
							accountNumber = "0" + accountNumber;
						} else if (accountNumber.length() == 13) {
							accountNumber = accountNumber.substring(1);
						} else if (accountNumber.length() == 14) {
							accountNumber = accountNumber.substring(2);
						}
						
						((ESDdyAccount) data).setAccountNumber(CommonUtils.normalizeAccNo(accountNumber));
					} else if (filename.contains("DDMAST")) {
						data = extract(item, ESDdmast.class);
						
						String cifNo = ((ESDdmast) data).getCifNo();
						cifNo = paddingStr(cifNo, 20);
						
						String accountNumber = ((ESDdmast) data).getAccountNumber();
						accountNumber = paddingStr(accountNumber, 12);
						
						((ESDdmast) data).setCifNo(CommonUtils.normalizeCifKey(cifNo));
						((ESDdmast) data).setAccountNumber(CommonUtils.normalizeAccNo(accountNumber));
						
//						ESAccountInfo info = new ESAccountInfo();
//						info.setCifNo(cifNo);
//						listInfo.add(info);	
						
						DDMastAccountList al = new DDMastAccountList();
						al.setCifKey(CommonUtils.normalizeCifKey(cifNo));
						al.setAccountNumber(CommonUtils.normalizeAccNo(accountNumber));	
						ddMastAccountList.add(al);
					} else if (filename.contains("DDI236MTXH")) {
						//mcy-h
						data = extract(item, ESHMultiCurrency.class);
						
						String accountNumber = ((ESHMultiCurrency) data).getAccountNumber();
						accountNumber = this.paddingAccNoMcy(accountNumber);
						
						accountNumber = CommonUtils.normalizeAccNo(accountNumber);
						
						((ESHMultiCurrency) data).setAccountNumber(accountNumber);
						((ESHMultiCurrency) data).setBlockHc(0);
					} else if (filename.contains("DDI236TXTH")) {
						//giro-h
						data = extract(item, ESHGiro.class);
						
						String accountNumber = ((ESHGiro) data).getNoRekening();
						accountNumber = this.paddingAccNoMcy(accountNumber);
						
						accountNumber = CommonUtils.normalizeAccNo(accountNumber);
						
						((ESHGiro) data).setNoRekening(accountNumber);
						((ESHGiro) data).setBlockHc(0);
					} else if (filename.contains("DDI236MBLH")) {
						//mcb-h
						data = extract(item, ESHMCBilingual.class);
						
						String accountNumber = ((ESHMCBilingual) data).getNoRekening();
						accountNumber = this.paddingAccNoMcy(accountNumber);
						
						accountNumber = CommonUtils.normalizeAccNo(accountNumber);
						
						((ESHMCBilingual) data).setNoRekening(accountNumber);
						((ESHMCBilingual) data).setBlockHc(0);
					}
					
					rd.add(data);
				} catch (NISPNoSuchMethodException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPInvocationTargetException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPIllegalAccessException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPInstantiationException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPParseException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPNumberFormatException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPValueNotValidException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				} catch (NISPValueLengthNotMatchException e) {
					e.printStackTrace();
					success[0] = false;
					listLine.add(startNumber+line + ", error code: " + e.getMessage());
				}
				
				line++;
			}
			
			
			if (filename.contains("DDMAST")) {
				dao.saveDDMastAccountList(ddMastAccountList);
			} else if (filename.contains("DDI236MTXH") 
					|| filename.contains("DDI236TXTH") 
					|| filename.contains("DDI236MBLH")
					|| filename.contains("DDI236MTXS") 
					|| filename.contains("DDI236TXTS") 
					|| filename.contains("DDI236MBLS")) {
				//sort mcy - giro - mcb
				if (rd.size() > 0) {
					Collections.sort(rd, new Comparator<DDIModel>() {

						@Override
						public int compare(DDIModel o1, DDIModel o2) {
							if (o1 instanceof ESHMultiCurrency && o2 instanceof ESHMultiCurrency) {
								return ((ESHMultiCurrency) o1).getAccountNumber().compareTo(((ESHMultiCurrency) o2).getAccountNumber());
							} else if (o1 instanceof ESHGiro && o2 instanceof ESHGiro) {
								return ((ESHGiro) o1).getNoRekening().compareTo(((ESHGiro) o2).getNoRekening());
							} else if (o1 instanceof ESHMCBilingual && o2 instanceof ESHMCBilingual) {
								return ((ESHMCBilingual) o1).getNoRekening().compareTo(((ESHMCBilingual) o2).getNoRekening());
							} else if (o1 instanceof ESMultiCurrency && o2 instanceof ESMultiCurrency) {
								return ((ESMultiCurrency) o1).getAccountNumber().compareTo(((ESMultiCurrency) o2).getAccountNumber());
							} else if (o1 instanceof ESGiro && o2 instanceof ESGiro) {
								return ((ESGiro) o1).getNoRekening().compareTo(((ESGiro) o2).getNoRekening());
							} else {
								return ((ESMCBilingual) o1).getNoRekening().compareTo(((ESMCBilingual) o2).getNoRekening());
							}
						}
					});
					
					//check to block HC
					List<BOBlockHardcopy> listBlockIns = new ArrayList<BOBlockHardcopy>();
					List<BOBlockHardcopy> listBlock = blockHcDao.getAllDataNonCC();
					
					String accNoTemp = "";
					int dbCount = 0;
					int fileCount = 0;
					int isBlock = 0;
					
					while (fileCount < rd.size()) {
						DDIModel item = rd.get(fileCount);
						
						String accNo = "";
						String name = "";
						if (item instanceof ESHMultiCurrency) {
							accNo = ((ESHMultiCurrency) item).getAccountNumber();
							name = ((ESHMultiCurrency) item).getNamaRekening();
						} else if (item instanceof ESHGiro) {
							accNo = ((ESHGiro) item).getNoRekening();
							name = ((ESHGiro) item).getNamaRekening();
						} else if (item instanceof ESHMCBilingual) {
							accNo = ((ESHMCBilingual) item).getNoRekening();
							name = ((ESHMCBilingual) item).getNamaRekening();
						} else if (item instanceof ESMultiCurrency) {
							accNo = ((ESMultiCurrency) item).getAccountNumber();
							name = ((ESMultiCurrency) item).getNamaRekening();
						} else if (item instanceof ESGiro) {
							accNo = ((ESGiro) item).getNoRekening();
							name = ((ESGiro) item).getNamaRekening();
						} else {
							accNo = ((ESMCBilingual) item).getNoRekening();
							name = ((ESMCBilingual) item).getNamaRekening();
						}
						
						if (!accNoTemp.equals(accNo)) {
							//process disini
							accNoTemp = accNo;
							isBlock = 0;
							
							if (listBlock.size() > 0 && listBlock.size() > dbCount) {
								BOBlockHardcopy block = listBlock.get(dbCount);
								int compare = accNo.compareTo(block.getAccountNumber());
								if (compare == 0) {
									if (block.getStatus() == 1) {
										//block, tidak print
										isBlock = 1;
									}
									
									fileCount++;
									dbCount++;
								} else if (compare > 0) {
									dbCount++;
								} else if (compare < 0) {
									BOBlockHardcopy newBlock = new BOBlockHardcopy();
									newBlock.setAccountNumber(accNo);
									newBlock.setName(name);
									newBlock.setStatus(0); //tidak block
									newBlock.setType(1); //noncc
									listBlockIns.add(newBlock);
									
									fileCount++;
								}
							} else {
								BOBlockHardcopy newBlock = new BOBlockHardcopy();
								newBlock.setAccountNumber(accNo);
								newBlock.setName(name);
								newBlock.setStatus(0); //tidak block
								newBlock.setType(1); //noncc
								listBlockIns.add(newBlock);
								
								fileCount++;
							}
						} else {
							fileCount++;
						}
						
						//set block hc
						if (item instanceof ESHMultiCurrency) {
							((ESHMultiCurrency) item).setBlockHc(isBlock);
						} else if (item instanceof ESHGiro) {
							((ESHGiro) item).setBlockHc(isBlock);
						} else if (item instanceof ESHMCBilingual) {
							((ESHMCBilingual) item).setBlockHc(isBlock);
						} else if (item instanceof ESMultiCurrency) {
							((ESMultiCurrency) item).setBlockHc(isBlock);
						} else if (item instanceof ESGiro) {
							((ESGiro) item).setBlockHc(isBlock);
						} else {
							((ESMCBilingual) item).setBlockHc(isBlock);
						}
					}
					
					//insert to block HC
					if (listBlockIns.size() > 0) {
						blockHcDao.createBatch(listBlockIns.toArray());
					}
				}
			} 
			if( listInfo.size() > 0 ){
				dao.insertOrUpdateBatch(listInfo.toArray());
			}
			
//			for (DDIModel info : listInfo) {
//				dao.insertOrUpdate(info);
//			}
			
			return rd;
		} catch (Exception e) {
			logger.error("Error", e);
			success[0] = false;
			return null;
		}
	}
	
	@Override
	public void saveToDbBatch(List<DDIModel> models) {
		dao.createBatch(models.toArray());
	}

	private String paddingStr(String param, int totalPad) {
		String result = "";
		
		int length = param.length();
		int sisa = totalPad - length;
		
		String padding = "";
		for (int i = 0; i < sisa; i++) {
			padding += "0";
		}
		
		result = padding + param;
		
		return result;
	}
	
	private String paddingAccNoMcy(String param) {
		String accountNumber = param.trim();
		
		if (accountNumber.length() == 11) {
			accountNumber = "0" + accountNumber;
		} else if (accountNumber.length() == 13) {
			accountNumber = accountNumber.substring(1);
		} else if (accountNumber.length() == 14) {
			accountNumber = accountNumber.substring(2);
		}
		
		return accountNumber;
	}
	
	private String paddingAccNoGiro(String param) {
		String accountNumber = param.trim();
		
		if (accountNumber.length() == 9) {
			accountNumber = "000" + accountNumber;
		} else if (accountNumber.length() == 10) {
			accountNumber = "00" + accountNumber;
		} else if (accountNumber.length() == 11) {
			accountNumber = "0" + accountNumber;
		}
		
		return accountNumber;
	}
}
