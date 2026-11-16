package com.optima.oraclejdbc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.CCDao;
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
import com.optima.oraclejdbc.model.CCAHChildModel;
import com.optima.oraclejdbc.model.CCAccountBalance;
import com.optima.oraclejdbc.model.CCAccountHeader;
import com.optima.oraclejdbc.model.CCAccountLevelMessage;
import com.optima.oraclejdbc.model.CCAccountTrailer;
import com.optima.oraclejdbc.model.CCCHChildModel;
import com.optima.oraclejdbc.model.CCChildModel;
import com.optima.oraclejdbc.model.CCCustomerHeader;
import com.optima.oraclejdbc.model.CCCustomerHeaderMessage;
import com.optima.oraclejdbc.model.CCCustomerTrailer;
import com.optima.oraclejdbc.model.CCDualCurrency;
import com.optima.oraclejdbc.model.CCFileTrailer;
import com.optima.oraclejdbc.model.CCFooterMessage;
import com.optima.oraclejdbc.model.CCLuckyDraw;
import com.optima.oraclejdbc.model.CCParentModel;
import com.optima.oraclejdbc.model.CCPromotionalMessage;
import com.optima.oraclejdbc.model.CCRateInfo;
import com.optima.oraclejdbc.model.CCRewardSummary;
import com.optima.oraclejdbc.model.CCStatement;
import com.optima.oraclejdbc.model.CCStatementMessage;
import com.optima.oraclejdbc.model.CCTransactionDetails;
import com.optima.oraclejdbc.utils.FileUtility;
import com.optima.oraclejdbc.utils.MailProcessing;

@Service
public class CCParser extends NISPParser<CCParentModel>{

	private static final Logger logger = Logger.getLogger(CCParser.class);
	
	@Autowired
	CCDao dao;
	
	@Autowired
	private MailProcessing mailProcessing;
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Override
	public void setFilename(String inputFilename) throws IOException {
		List<String> entries = unzip(inputFilename);
		if( entries == null )
			return;
		List<String> datas = new ArrayList<String>();
		List<String> listLine = new ArrayList<String>();
		boolean success[] = new boolean[1];
		success[0] = true;
		
		for (String filename : entries) {
			BufferedReader br = null;
			boolean canAccess = false;
			int startNumber = 0;
			do{
				try {
					String currentLine;
					br = new BufferedReader(new FileReader(filename));
					
					int counter = 0;					
					while ((currentLine = br.readLine()) != null) {
//						System.out.println(currentLine);
						datas.add(currentLine);
						counter++;						
						if( counter >= 10000 && currentLine.startsWith("FT") ){
							List<String> finalData = new ArrayList<String>();
							finalData.addAll(datas);
							processData(inputFilename,finalData, success, listLine, startNumber);
							datas.clear();
							startNumber+=counter;
							counter = 0;
						}
					}
					processData(inputFilename,datas, success, listLine, startNumber);
					synchronized (DBThreadFactory.intances().getSyncObject()) {
						DBThreadFactory.intances().getSyncObject().wait();
					}
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
						if (br != null) {
							br.close();
							File delete = new File(filename);
							if( delete.exists() )
								delete.delete();
							canAccess = true;
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
	}
	
	@Override
	public List<CCParentModel> extract(List<String> datas, boolean success[], List<String> listLine, String filename, int startNumber) {
		ArrayList<CCParentModel> res = new ArrayList<CCParentModel>();
		
		//success[0] = true;
		int line = 1;
		
		Long ccStatementId = null;
		Long chId = null;
		Long ahId = null;
		for (String item : datas) {
			try {
				if (item.length() <= 1 && line == datas.size()) {
					continue;
				}
				
				CCParentModel data = null;
				
				if (item.startsWith("FH")) {
					data = extract(item, CCStatement.class);
					
					ccStatementId = dao.getNextId();
					((CCStatement) data).setId(ccStatementId);
				} else if (item.startsWith("CH")) {
					data = extract(item, CCCustomerHeader.class);
					
					if (ccStatementId != null) {
						((CCCustomerHeader) data).setCcStatementId(ccStatementId);
					}
					chId = dao.getNextChId();
					((CCCustomerHeader) data).setId(chId);
				} else if (item.startsWith("AM")) {
					data = extract(item, CCAccountLevelMessage.class);
					
					if (ccStatementId != null) {
						((CCAccountLevelMessage) data).setCcStatementId(ccStatementId);
					}
					
					((CCAccountLevelMessage) data).setChId(chId);
				} else if (item.startsWith("AH")) {
					data = extract(item, CCAccountHeader.class);
					
					if (ccStatementId != null) {
						((CCAccountHeader) data).setCcStatementId(ccStatementId);
					}
					ahId = dao.getNextAhId();
					((CCAccountHeader) data).setId(ahId);
					((CCAccountHeader) data).setChId(chId);
				} else if (item.startsWith("TD")) {
					data = extract(item, CCTransactionDetails.class);
					
					if (ccStatementId != null) {
						((CCTransactionDetails) data).setCcStatementId(ccStatementId);
					}
					((CCTransactionDetails) data).setAhId(ahId);
				} else if (item.startsWith("LD")) {
					data = extract(item, CCLuckyDraw.class);
					
					if (ccStatementId != null) {
						((CCLuckyDraw) data).setCcStatementId(ccStatementId);
					}
					((CCLuckyDraw) data).setChId(chId);
				} else if (item.startsWith("AB")) {
					data = extract(item, CCAccountBalance.class);
					
					if (ccStatementId != null) {
						((CCAccountBalance) data).setCcStatementId(ccStatementId);
					}
					((CCAccountBalance) data).setChId(chId);
				} else if (item.startsWith("BS")) {
					data = extract(item, CCRewardSummary.class);
					
					if (ccStatementId != null) {
						((CCRewardSummary) data).setCcStatementId(ccStatementId);
					}
					((CCRewardSummary) data).setChId(chId);
				} else if (item.startsWith("RI")) {
					data = extract(item, CCRateInfo.class);
					
					if (ccStatementId != null) {
						((CCRateInfo) data).setCcStatementId(ccStatementId);
					}
					((CCRateInfo) data).setChId(chId);
				} else if (item.startsWith("MS")) {
					data = extract(item, CCStatementMessage.class);
					
					if (ccStatementId != null) {
						((CCStatementMessage) data).setCcStatementId(ccStatementId);
					}
					((CCStatementMessage) data).setChId(chId);
				} else if (item.startsWith("MT")) {
					data = extract(item, CCPromotionalMessage.class);
					
					if (ccStatementId != null) {
						((CCPromotionalMessage) data).setCcStatementId(ccStatementId);
					}
					((CCPromotionalMessage) data).setChId(chId);
				} else if (item.startsWith("AT")) {
					data = extract(item, CCAccountTrailer.class);
					
					if (ccStatementId != null) {
						((CCAccountTrailer) data).setCcStatementId(ccStatementId);
					}
					((CCAccountTrailer) data).setChId(chId);
				} else if (item.startsWith("CM")) {
					data = extract(item, CCCustomerHeaderMessage.class);
					
					if (ccStatementId != null) {
						((CCCustomerHeaderMessage) data).setCcStatementId(ccStatementId);
					}
					((CCCustomerHeaderMessage) data).setChId(chId);
				} else if (item.startsWith("CT")) {
					data = extract(item, CCCustomerTrailer.class);
					
					if (ccStatementId != null) {
						((CCCustomerTrailer) data).setCcStatementId(ccStatementId);
					}
					((CCCustomerTrailer) data).setChId(chId);
				} else if (item.startsWith("DC")) {
					data = extract(item, CCDualCurrency.class);
					
					if (ccStatementId != null) {
						((CCDualCurrency) data).setCcStatementId(ccStatementId);
					}
					((CCDualCurrency) data).setChId(chId);
				} else if (item.startsWith("FT")) {
					data = extract(item, CCFileTrailer.class);
					
					if (ccStatementId != null) {
						((CCFileTrailer) data).setCcStatementId(ccStatementId);
					}
					((CCFileTrailer) data).setChId(chId);
				} else if (item.startsWith("MF")) {
					data = extract(item, CCFooterMessage.class);
					
					if (ccStatementId != null) {
						((CCFooterMessage) data).setCcStatementId(ccStatementId);
					}
					((CCFooterMessage) data).setChId(chId);
				}
				
				if (data != null) {
					res.add(data);
				}
				
			} catch (NISPNoSuchMethodException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPInvocationTargetException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPIllegalAccessException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPInstantiationException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPParseException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPNumberFormatException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPValueNotValidException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			} catch (NISPValueLengthNotMatchException e) {
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
				return null;
			}
			
			line++;
		}
		
		return res;
	}
	
	@Override
	public void saveToDbBatch(List<CCParentModel> models) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		dao.createBatch(models.toArray());
	}
	
	@Override
	public void saveToDb(List<CCParentModel> models) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Long id = null;
		Long chId = null;
		Long ahId = null;
		
		Date today = null;
		for (CCParentModel model : models) {
			today = new Date();
			
			if( model instanceof CCStatement ){
				((CCStatement) model).setCreatedDate(today);
				((CCStatement) model).setUpdatedDate(today);
				
				id = dao.insertOrUpdate(model);
				
				if (id != null) {
					((CCStatement) model).setId(id);
				}
			} else if (model instanceof CCChildModel) {
				((CCChildModel) model).setCcStatementId(id);
				((CCChildModel) model).setCreatedDate(today);
				((CCChildModel) model).setUpdatedDate(today);
				
				if (model instanceof CCCustomerHeader) {
					chId = null;
					ahId = null;
					
					chId = dao.insertOrUpdate(model);
//					chId = ((CCCustomerHeader) model).getId();
				} else if (model instanceof CCAccountHeader) {
					((CCAccountHeader) model).setChId(chId);
					ahId = dao.insertOrUpdate(model);
//					ahId = ((CCAccountHeader) model).getId();
				} else {
					if (model instanceof CCCHChildModel) {
						((CCCHChildModel) model).setChId(chId);
						dao.insertOrUpdate(model);
					} else if (model instanceof CCAHChildModel) {
						((CCAHChildModel) model).setAhId(ahId);
						dao.insertOrUpdate(model);
					}
				}
				
//				dao.insertOrUpdate(model);
			}
		}
	}
	
//	@Override
//	public List<CCParentModel> extract(List<String> datas, boolean success[], List<Integer> listLine) {
//		ArrayList<CCParentModel> res = new ArrayList<CCParentModel>();
//		
//		success[0] = true;
//		int line = 1;
//		List<CCParentModel> tempRes = new ArrayList<CCParentModel>();
//		
//		Long ccStatementId = null;
//		boolean isSkip = false;
//		for (String item : datas) {
//			CCParentModel data=null;
//			
//			boolean defined = false;
//			
//			if (item.startsWith("CH") && isSkip) {
//				res.removeAll(tempRes);
//				isSkip = false;
//			}
//			
//			if (!isSkip) {
//				if (item.startsWith("FH")) {
//					defined = true;
//					data = extract(item, CCStatement.class);
//					ccStatementId = ((CCStatement) data).getId();
//				} else if (item.startsWith("CH")) {
//					defined = true;
//					data = extract(item, CCCustomerHeader.class);
//					
//					if (ccStatementId != null) {
//						((CCCustomerHeader) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("AM")) {
//					defined = true;
//					data = extract(item, CCAccountLevelMessage.class);
//					
//					if (ccStatementId != null) {
//						((CCAccountLevelMessage) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("AH")) {
//					defined = true;
//					data = extract(item, CCAccountHeader.class);
//					
//					if (ccStatementId != null) {
//						((CCAccountHeader) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("TD")) {
//					defined = true;
//					data = extract(item, CCTransactionDetails.class);
//					
//					if (ccStatementId != null) {
//						((CCTransactionDetails) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("LD")) {
//					defined = true;
//					data = extract(item, CCLuckyDraw.class);
//					
//					if (ccStatementId != null) {
//						((CCLuckyDraw) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("AB")) {
//					defined = true;
//					data = extract(item, CCAccountBalance.class);
//					
//					if (ccStatementId != null) {
//						((CCAccountBalance) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("BS")) {
//					defined = true;
//					data = extract(item, CCRewardSummary.class);
//					
//					if (ccStatementId != null) {
//						((CCRewardSummary) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("RI")) {
//					defined = true;
//					data = extract(item, CCRateInfo.class);
//					
//					if (ccStatementId != null) {
//						((CCRateInfo) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("MS")) {
//					defined = true;
//					data = extract(item, CCStatementMessage.class);
//					
//					if (ccStatementId != null) {
//						((CCStatementMessage) data).setCcStatementId(ccStatementId);
//					}
//					((CCStatementMessage) data).setCcStatementId(ccStatementId);
//				} else if (item.startsWith("MT")) {
//					defined = true;
//					data = extract(item, CCPromotionalMessage.class);
//					
//					if (ccStatementId != null) {
//						((CCPromotionalMessage) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("AT")) {
//					defined = true;
//					data = extract(item, CCAccountTrailer.class);
//					
//					if (ccStatementId != null) {
//						((CCAccountTrailer) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("CM")) {
//					defined = true;
//					data = extract(item, CCCustomerHeaderMessage.class);
//					
//					if (ccStatementId != null) {
//						((CCCustomerHeaderMessage) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("CT")) {
//					defined = true;
//					data = extract(item, CCCustomerTrailer.class);
//					
//					if (ccStatementId != null) {
//						((CCCustomerTrailer) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("DC")) {
//					defined = true;
//					data = extract(item, CCDualCurrency.class);
//					
//					if (ccStatementId != null) {
//						((CCDualCurrency) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("FT")) {
//					defined = true;
//					data = extract(item, CCFileTrailer.class);
//					
//					if (ccStatementId != null) {
//						((CCFileTrailer) data).setCcStatementId(ccStatementId);
//					}
//				} else if (item.startsWith("MF")) {
//					defined = true;
//					data = extract(item, CCFooterMessage.class);
//					
//					if (ccStatementId != null) {
//						((CCFooterMessage) data).setCcStatementId(ccStatementId);
//					}
//				}
//			}
//			
//			if (data != null) {
//				res.add(data);
//				if (!item.startsWith("FH")) {
//					tempRes.add(data);
//				}
//			} else if (defined) {
//				listLine.add(line);
//				isSkip = true;
//			}
//			
//			line++;
//		}
//		
//		return res;
//	}
}