package com.optima.oraclejdbc.daoimpl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.support.oracle.BeanPropertyStructMapper;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.data.jdbc.support.oracle.SqlStructArrayValue;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.model.CustomerInfo;
import com.optima.oraclejdbc.model.DDMastAccountList;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.model.backoffice.BOSendEmail;
import com.optima.oraclejdbc.model.estatement.DDIModel;
import com.optima.oraclejdbc.model.estatement.ESGiro;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.model.estatement.ESHGiro;
import com.optima.oraclejdbc.model.estatement.ESHMCBilingual;
import com.optima.oraclejdbc.model.estatement.ESHMultiCurrency;
import com.optima.oraclejdbc.model.estatement.ESMCBHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBilingual;
import com.optima.oraclejdbc.model.estatement.ESAccountInfo;
import com.optima.oraclejdbc.model.estatement.ESDdmast;
import com.optima.oraclejdbc.model.estatement.ESDdyAccount;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrency;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrencyHistory;
import com.optima.oraclejdbc.model.estatement.ESOdtier;
import com.optima.oraclejdbc.model.estatement.ViewESAccountInfo;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterHistoryReport;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterReport;
import com.optima.oraclejdbc.model.estatement.ViewESMultiCurrencyKurs;
import com.optima.oraclejdbc.report.ReportBulananStatement;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.tibco.customerinfo.CustomerInfoRequest;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.RsaUtil;

import oracle.jdbc.internal.OracleTypes;

@Component
public class DDIDao extends ParentDao<DDIModel> {

	@Autowired
	RsaUtil rsaUtil;
	
	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	CustomerInfoDao customerInfoDao;
	
	@Autowired
	CustomerInfoRequest customerInfoRequest;
	
	@Autowired
	BOBlockHardcopyDao blockHcDao;
	
	@Override
	public void createBatch(Object[] dataArray) {
		try{
			if (dataArray.length > 0) {
				List<ESMultiCurrency> mcy = new ArrayList<ESMultiCurrency>();
				List<ESMultiCurrencyHistory> mcyHistory = new ArrayList<ESMultiCurrencyHistory>();			
				
				List<ESGiro> giro = new ArrayList<ESGiro>();
				List<ESGiroHistory> giroHistory = new ArrayList<ESGiroHistory>();
				
				List<ESMCBilingual> mcb = new ArrayList<ESMCBilingual>();
				List<ESMCBHistory> mcbHistory = new ArrayList<ESMCBHistory>();
				
				List<ESAccountInfo> emails = new ArrayList<ESAccountInfo>();
				List<ESOdtier> od = new ArrayList<ESOdtier>();
				List<ESDdyAccount> ddy = new ArrayList<ESDdyAccount>();
				
				List<ESAccountInfo> infos = new ArrayList<ESAccountInfo>();
				List<ESDdmast> mast = new ArrayList<ESDdmast>();
				
				//hardcopy
				List<ESHMultiCurrency> mcy_h = new ArrayList<ESHMultiCurrency>();
				List<ESHGiro> giro_h = new ArrayList<ESHGiro>();
				List<ESHMCBilingual> mcb_h = new ArrayList<ESHMCBilingual>();
				
				for (int i=0;i<dataArray.length;i++) {
					if (dataArray[i] instanceof ESMultiCurrency) {
						//mcy
						mcy.add((ESMultiCurrency) dataArray[i]);
					} else if (dataArray[i] instanceof ESGiro) {
						//giro
						giro.add((ESGiro) dataArray[i]);
					} else if (dataArray[i] instanceof ESMCBilingual) {
						//mcb
						mcb.add((ESMCBilingual) dataArray[i]);
					} else if (dataArray[i] instanceof ESAccountInfo) {
						//emails
						emails.add((ESAccountInfo) dataArray[i]);
					} else if (dataArray[i] instanceof ESOdtier) {
						//odtier
						od.add((ESOdtier) dataArray[i]);
					} else if (dataArray[i] instanceof ESDdyAccount) {
						//ddy
						ddy.add((ESDdyAccount) dataArray[i]);
					} else if (dataArray[i] instanceof ESDdmast) {
						//ddmast
						mast.add((ESDdmast) dataArray[i]);
					} else if (dataArray[i] instanceof ESAccountInfo) {
						infos.add((ESAccountInfo) dataArray[i]); 
					} else if (dataArray[i] instanceof ESHMultiCurrency) {
						//mcy-h
						mcy_h.add((ESHMultiCurrency) dataArray[i]);
					} else if (dataArray[i] instanceof ESHGiro) {
						//giro-h
						giro_h.add((ESHGiro) dataArray[i]);
					} else if (dataArray[i] instanceof ESHMCBilingual) {
						//mcb-h
						mcb_h.add((ESHMCBilingual) dataArray[i]);
					}
				}
				
				if (mcy.size() > 0) {
					super.createBatch(mcy.toArray());
					
					//print
					Date tglCetak = new Date();
					
					ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
					List<ViewESAccountInfo> records = getListAccountNumberMcy();
					
					List<BOSendEmail> listEmail = new ArrayList<BOSendEmail>();
					BOSendEmail email = null;
					
					String folder = CommonUtils.getMonthlyStatementDir(confProperties)+File.separator+"mcy";
					File dir = new File(folder);
					if(!dir.exists() )
						dir.mkdirs();
					
					String folderHc = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
					File dirHc = new File(folderHc);
					if(!dirHc.exists() )
						dirHc.mkdirs();
					List<String> processedAccounts = new ArrayList<String>();
					for (ViewESAccountInfo row : records) {
						ESMultiCurrency esMcy = getMcyByAccountNo(row.getAccountNumber());
						if (esMcy != null) {
							Calendar cal = Calendar.getInstance();
							//ambil periode dari tanggal transaksi
							cal.setTime(esMcy.getTanggal());
							//cal.add(Calendar.MONTH, -1);
							
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH);
							month++;
							String strMonth = "";
							if (month < 10) {
								strMonth = "0" + month;
							} else {
								strMonth = "" + month;
							}
							
							String period = year + strMonth;
							row.setPeriode(period);
							//GET TOTAL SALDO IDR
							double totalIdr = 0;
							List<ViewESMultiCurrencyKurs> listKurs = getListMcyKursReport(esMcy.getAccountNumber());
							for (ViewESMultiCurrencyKurs item : listKurs) {
								if (item.getSaldoIdrN() != null) {
									totalIdr = totalIdr + item.getSaldoIdrN().doubleValue();
								}
							}
							
							String periodeFolder = folder+File.separator+period;
							dir = new File(periodeFolder);
							if( !dir.exists() )
								dir.mkdirs();
							String filename = periodeFolder+File.separator+"e-Statement_" + esMcy.getAccountNumber() + "_" + esMcy.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
							String encryptedPass = accountListDao.getStatementPassword(row.getAccountNumber(), 0);
							String password = null;
							if( encryptedPass != null )
								password = rsaUtil.rsaDecrypt(encryptedPass);
							String fullpath = ReportBulananStatement.generateReportMCY(elModel, esMcy, row, tglCetak, filename, password, null, totalIdr);
							
							//add to list email
							email = new BOSendEmail();
							email.setAccountNumber(row.getAccountNumber());
							email.setCifKey(row.getCifNo());
							email.setFilename(fullpath);
							email.setPeriode(row.getPeriode());
							email.setStatus(0);
							email.setTglCetak(tglCetak);
							email.setCategory(1);
							email.setNamaRekening(esMcy.getNamaRekening());
							email.setProductName(esMcy.getProductTypeDesc());
							CustomerInfo ci = customerInfoDao.getUserByCIFKey(row.getCifNo());
							if( ci != null )
								email.setIsPasswordDefault(ci.getIsPasswordDefault());
							else
								email.setIsPasswordDefault(1);
							listEmail.add(email);
							
							//print-hc
							if ( row.getIsMonthly() != null && row.getIsMonthly() == 1) {
								if (row.getBlockHc() != 1) {
									String periodeFolderHc = folderHc + File.separator + period;
									dirHc = new File(periodeFolderHc);
									if( !dirHc.exists() )
										dirHc.mkdirs();
									String filenameHc = periodeFolderHc+File.separator+"e-Statement_Hardcopy_" + esMcy.getAccountNumber() + "_" + esMcy.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
									
									ReportBulananStatement.generateReportMCY(elModel, esMcy, row, tglCetak, filenameHc, null, null, totalIdr);
								}
							} else {
								//hapus block hc karena tidak request monthly
								blockHcDao.deleteByAccountNo(row.getAccountNumber());
							}
							
							//hapus by account no
							//deleteMcyByAccountNo(row.getAccountNumber());
							processedAccounts.add(row.getAccountNumber());
						}
					}
					
					//deleteMcyByAccountsNo(processedAccounts);
					//save to list email
					if (listEmail.size() > 0) {
						super.createBatch(listEmail.toArray());
					}
					
					//save to history
//					for (ESMultiCurrency item : mcy) {
//						ESMultiCurrencyHistory history = new ESMultiCurrencyHistory(item);
//						history.setPeriode(records.get(0).getPeriode());
//						history.setType(1);
//						history.setTglCetakStr(DateUtility.formatDate2(tglCetak));
//						
//						mcyHistory.add(history);
//					}
					
					if (mcyHistory.size() > 0) {
						super.createBatch(mcyHistory.toArray());
					}
				} else if (giro.size() > 0) {
					super.createBatch(giro.toArray());
					
					//print
					Date tglCetak = new Date();
					int giroType = 0;
					int cat = 0;
					
					ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
					List<ViewESAccountInfo> records = getListAccountNumberGiro();
					
					List<BOSendEmail> listEmail = new ArrayList<BOSendEmail>();
					BOSendEmail email = null;
					
					String folder = CommonUtils.getMonthlyStatementDir(confProperties)+File.separator+"giro";
					File dir = new File(folder);
					if(!dir.exists() )
						dir.mkdirs();
					
					String folderHc = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
					File dirHc = new File(folderHc);
					if(!dirHc.exists() )
						dirHc.mkdirs();
					List<String> processedAccounts = new ArrayList<String>();
					for (ViewESAccountInfo row : records) {
						ESGiro esGiro = getGiroByAccountNo(row.getAccountNumber());
						if (esGiro != null) {
							giroType = getGiroType(row.getAccountNumber(), row.getPeriode());
							cat = giroType;
							
							if (giroType == 4) {
								giroType = 3;
							}
							
							//print
							if (giroType != 0) {
								Calendar cal = Calendar.getInstance();
								//ambil periode dari tanggal transaksi
								cal.setTime(esGiro.getTanggal());
								//cal.add(Calendar.MONTH, -1);
								
								int year = cal.get(Calendar.YEAR);
								int month = cal.get(Calendar.MONTH);
								month++;
								String strMonth = "";
								if (month < 10) {
									strMonth = "0" + month;
								} else {
									strMonth = "" + month;
								}
								
								String period = year + strMonth;
								row.setPeriode(period);
								ViewESGiroFooterReport footer = getGiroFooter(row);
								if( footer == null )
									continue;
								String periodeFolder = folder+File.separator+period;
								dir = new File(periodeFolder);
								if( !dir.exists() )
									dir.mkdirs();
								String filename = periodeFolder+File.separator+"e-Statement_" + esGiro.getNoRekening() + "_" + esGiro.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
								String encryptedPass = accountListDao.getStatementPassword(row.getAccountNumber(), 0);						
								String password = null;
								if( encryptedPass != null )
									password = rsaUtil.rsaDecrypt(encryptedPass);
								
								String fullpath = ReportBulananStatement.generateReportGiro(elModel, esGiro, giroType, row, tglCetak, footer, filename, password, null);
								//add to list email
								email = new BOSendEmail();
								email.setAccountNumber(row.getAccountNumber());
								email.setCifKey(row.getCifNo());
								email.setFilename(fullpath);
								email.setPeriode(row.getPeriode());
								email.setStatus(0);
								email.setTglCetak(tglCetak);
								email.setCategory(cat);
								email.setNamaRekening(esGiro.getNamaRekening());
								email.setProductName(esGiro.getProductTypeDesc());
								CustomerInfo ci = customerInfoDao.getUserByCIFKey(row.getCifNo());
								if( ci != null )
									email.setIsPasswordDefault(ci.getIsPasswordDefault());
								else
									email.setIsPasswordDefault(1);
								listEmail.add(email);
								
								//print-hc
								if (row.getIsMonthly() != null && row.getIsMonthly() == 1) {
									if (row.getBlockHc() != 1) {
										String periodeFolderHc = folderHc + File.separator + period;
										dirHc = new File(periodeFolderHc);
										if( !dirHc.exists() )
											dirHc.mkdirs();
										String filenameHc = periodeFolderHc+File.separator+"e-Statement_Hardcopy_" + esGiro.getNoRekening() + "_" + esGiro.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
										
										ReportBulananStatement.generateReportGiro(elModel, esGiro, giroType, row, tglCetak, footer, filenameHc, null, null);
									}
								} else {
									//hapus block hc karena tidak request monthly
									blockHcDao.deleteByAccountNo(row.getAccountNumber());
								}
							}
							
							//hapus by accountNo
							//deleteGiroByAccountNo(row.getAccountNumber());
							processedAccounts.add(row.getAccountNumber());
						}
					}
					
					//deleteGiroByAccountsNo(processedAccounts);
					//save to list email
					if (listEmail.size() > 0) {
						super.createBatch(listEmail.toArray());
					}
					
					//save to history
//					for (ESGiro item : giro) {
//						ESGiroHistory history = new ESGiroHistory(item);
//						history.setPeriode(records.get(0).getPeriode());
//						history.setType(giroType);
//						history.setTglCetakStr(DateUtility.formatDate2(tglCetak));
//						
//						giroHistory.add(history);
//					}
					
					if (giroHistory.size() > 0) {
						super.createBatch(giroHistory.toArray());
					}
				} else if (mcb.size() > 0) {
					super.createBatch(mcb.toArray());
					
					//print mcb
					Date tglCetak = new Date();
					
					ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
					List<ViewESAccountInfo> records = getListAccountNumberMcb();
					
					List<BOSendEmail> listEmail = new ArrayList<BOSendEmail>();
					BOSendEmail email = null;
					
					String folder = CommonUtils.getMonthlyStatementDir(confProperties)+File.separator+"mcb";
					File dir = new File(folder);
					if(!dir.exists() )
						dir.mkdirs();
					
					String folderHc = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
					File dirHc = new File(folderHc);
					if(!dirHc.exists() )
						dirHc.mkdirs();
					
					List<String> processedAccounts = new ArrayList<String>();
					for (ViewESAccountInfo row : records) {
						ESMCBilingual esMcb = getMcbByAccountNo(row.getAccountNumber());
						if (esMcb != null) {
							Calendar cal = Calendar.getInstance();
							//ambil periode dari tanggal transaksi
							cal.setTime(esMcb.getTanggal());
							//cal.add(Calendar.MONTH, -1);
							
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH);
							month++;
							String strMonth = "";
							if (month < 10) {
								strMonth = "0" + month;
							} else {
								strMonth = "" + month;
							}
							
							String period = year + strMonth;
							row.setPeriode(period);
							//GET TOTAL SALDO IDR
							double totalIdr = 0;
							List<ViewESMultiCurrencyKurs> listKurs = getListMcbKursReport(esMcb.getNoRekening());
							for (ViewESMultiCurrencyKurs item : listKurs) {
								if (item.getSaldoIdrN() != null) {
									totalIdr = totalIdr + item.getSaldoIdrN().doubleValue();
								}
							}
							
							String periodeFolder = folder+File.separator+period;
							dir = new File(periodeFolder);
							if( !dir.exists() )
								dir.mkdirs();
							String filename = periodeFolder+File.separator+"e-Statement_" + esMcb.getNoRekening() + "_" + esMcb.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
							String encryptedPass = accountListDao.getStatementPassword(row.getAccountNumber(), 0);
							String password = null;
							if( encryptedPass != null )
								password = rsaUtil.rsaDecrypt(encryptedPass);
							String fullpath = ReportBulananStatement.generateReportMCB(elModel, esMcb, row, tglCetak, filename, password, null, totalIdr);
							
							//add to list email
							email = new BOSendEmail();
							email.setAccountNumber(row.getAccountNumber());
							email.setCifKey(row.getCifNo());
							email.setFilename(fullpath);
							email.setPeriode(row.getPeriode());
							email.setStatus(0);
							email.setTglCetak(tglCetak);
							email.setCategory(6); //mcb category 6
							email.setNamaRekening(esMcb.getNamaRekening());
							email.setProductName(esMcb.getProductTypeDesc());
							CustomerInfo ci = customerInfoDao.getUserByCIFKey(row.getCifNo());
							if( ci != null )
								email.setIsPasswordDefault(ci.getIsPasswordDefault());
							else
								email.setIsPasswordDefault(1);
							listEmail.add(email);
							
							//print-hc
							if (row.getIsMonthly() != null && row.getIsMonthly() == 1) {
								if (row.getBlockHc() != 1) {
									String periodeFolderHc = folderHc + File.separator + period;
									dirHc = new File(periodeFolderHc);
									if( !dirHc.exists() )
										dirHc.mkdirs();
									String filenameHc = periodeFolderHc+File.separator+"e-Statement_Hardcopy_" + esMcb.getNoRekening() + "_" + esMcb.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
									
									ReportBulananStatement.generateReportMCB(elModel, esMcb, row, tglCetak, filenameHc, null, null, totalIdr);
								}
							} else {
								//hapus block hc karena tidak request monthly
								blockHcDao.deleteByAccountNo(row.getAccountNumber());
							}
							
							//hapus by account no
							//deleteMcbByAccountNo(row.getAccountNumber());
							processedAccounts.add(row.getAccountNumber());
						}
					}
					
					deleteMcbByAccountsNo(processedAccounts);
					//save to list email
					if (listEmail.size() > 0) {
						super.createBatch(listEmail.toArray());
					}
					
					//save to history
					for (ESMCBilingual item : mcb) {
						ESMCBHistory history = new ESMCBHistory(item);
						history.setPeriode(records.get(0).getPeriode());
						history.setType(6);
						history.setTglCetakStr(DateUtility.formatDate2(tglCetak));
						
						mcbHistory.add(history);
					}
					
					if (mcbHistory.size() > 0) {
						super.createBatch(mcbHistory.toArray());
					}
				} else if (emails.size() > 0) {
					super.createBatch(emails.toArray());
				} else if (od.size() > 0) {
					//delete all records before insert new
					deleteAllOditer();
					
					super.createBatch(od.toArray());
				} else if (ddy.size() > 0) {
					//delete all records before insert new
					deleteAllDdymow();
					
					super.createBatch(ddy.toArray());
				} else if (mast.size() > 0) {
					super.createBatch(mast.toArray());
				} else if (infos.size() > 0) {
					super.createBatch(infos.toArray());
				} else if (mcy_h.size() > 0) {
					super.createBatch(mcy_h.toArray());
					
					//print mcy hardcopy
					Date tglCetak = new Date();
					
					ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
					List<ViewESAccountInfo> records = getListAccountNumberMcy();
					
					String folder = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
					File dir = new File(folder);
					if(!dir.exists() )
						dir.mkdirs();
					
					for (ViewESAccountInfo row : records) {
						if (row.getBlockHc() == 1) {
							continue;
						}
						
						ESMultiCurrency esMcy = getMcyByAccountNo(row.getAccountNumber());
						if (esMcy != null) {
							Calendar cal = Calendar.getInstance();
							//ambil periode dari tanggal transaksi
							cal.setTime(esMcy.getTanggal());
							//cal.add(Calendar.MONTH, -1);
							
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH);
							month++;
							String strMonth = "";
							if (month < 10) {
								strMonth = "0" + month;
							} else {
								strMonth = "" + month;
							}
							
							String period = year + strMonth;
							row.setPeriode(period);
							//GET TOTAL SALDO IDR
							double totalIdr = 0;
							List<ViewESMultiCurrencyKurs> listKurs = getListMcyKursReport(esMcy.getAccountNumber());
							for (ViewESMultiCurrencyKurs item : listKurs) {
								if (item.getSaldoIdrN() != null) {
									totalIdr = totalIdr + item.getSaldoIdrN().doubleValue();
								}
							}
							
							String periodeFolder = folder+File.separator+period;
							dir = new File(periodeFolder);
							if( !dir.exists() )
								dir.mkdirs();
							String filename = periodeFolder+File.separator+"e-Statement_Hardcopy_" + esMcy.getAccountNumber() + "_" + esMcy.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
//							String encryptedPass = accountListDao.getStatementPassword(row.getAccountNumber(), 0);
//							String password = null;
//							if( encryptedPass != null )
//								password = rsaUtil.rsaDecrypt(encryptedPass);
							
							ReportBulananStatement.generateReportMCY(elModel, esMcy, row, tglCetak, filename, null, null, totalIdr);
							
							//hapus by account no
							deleteMcyByAccountNo(row.getAccountNumber());
						}
					}
					
					//save to history
					for (ESMultiCurrency item : mcy) {
						ESMultiCurrencyHistory history = new ESMultiCurrencyHistory(item);
						history.setPeriode(records.get(0).getPeriode());
						history.setType(1);
						history.setTglCetakStr(DateUtility.formatDate2(tglCetak));
						
						mcyHistory.add(history);
					}
					
					if (mcyHistory.size() > 0) {
						super.createBatch(mcyHistory.toArray());
					}
				} else if (giro_h.size() > 0) {
					super.createBatch(giro_h.toArray());
					
					//print giro hardcopy
					Date tglCetak = new Date();
					int giroType = 0;
					
					ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
					List<ViewESAccountInfo> records = getListAccountNumberGiro();
					
					String folder = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
					File dir = new File(folder);
					if(!dir.exists() )
						dir.mkdirs();
					
					for (ViewESAccountInfo row : records) {
						if (row.getBlockHc() == 1) {
							continue;
						}
						
						ESGiro esGiro = getGiroByAccountNo(row.getAccountNumber());
						if (esGiro != null) {
							giroType = getGiroType(row.getAccountNumber(), row.getPeriode());
							
							if (giroType == 4) {
								giroType = 3;
							}
							
							//print
							if (giroType != 0) {
								Calendar cal = Calendar.getInstance();
								//ambil periode dari tanggal transaksi
								cal.setTime(esGiro.getTanggal());
								//cal.add(Calendar.MONTH, -1);
								
								int year = cal.get(Calendar.YEAR);
								int month = cal.get(Calendar.MONTH);
								month++;
								String strMonth = "";
								if (month < 10) {
									strMonth = "0" + month;
								} else {
									strMonth = "" + month;
								}
								
								String period = year + strMonth;
								row.setPeriode(period);
								
								ViewESGiroFooterReport footer = getGiroFooter(row);
								if( footer == null )
									continue;
								String periodeFolder = folder+File.separator+period;
								dir = new File(periodeFolder);
								if( !dir.exists() )
									dir.mkdirs();
								String filename = periodeFolder+File.separator+"e-Statement_Hardcopy_" + esGiro.getNoRekening() + "_" + esGiro.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
//								String encryptedPass = accountListDao.getStatementPassword(row.getAccountNumber(), 0);						
//								String password = null;
//								if( encryptedPass != null )
//									password = rsaUtil.rsaDecrypt(encryptedPass);
								
								ReportBulananStatement.generateReportGiro(elModel, esGiro, giroType, row, tglCetak, footer, filename, null, null);
								
							}
							
							//hapus by accountNo
							deleteGiroByAccountNo(row.getAccountNumber());
						}
					}
					
					//save to history
					for (ESGiro item : giro) {
						ESGiroHistory history = new ESGiroHistory(item);
						history.setPeriode(records.get(0).getPeriode());
						history.setType(giroType);
						history.setTglCetakStr(DateUtility.formatDate2(tglCetak));
						
						giroHistory.add(history);
					}
					
					if (giroHistory.size() > 0) {
						super.createBatch(giroHistory.toArray());
					}
				} else if (mcb_h.size() > 0) {
					super.createBatch(mcb_h.toArray());
					
					//print mcb hardcopy
					Date tglCetak = new Date();
					
					ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
					List<ViewESAccountInfo> records = getListAccountNumberMcb();
					
					String folder = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
					File dir = new File(folder);
					if(!dir.exists() )
						dir.mkdirs();
					
					for (ViewESAccountInfo row : records) {
						if (row.getBlockHc() == 1) {
							continue;
						}
						
						ESMCBilingual esMcb = getMcbByAccountNo(row.getAccountNumber());
						if (esMcb != null) {
							Calendar cal = Calendar.getInstance();
							//ambil periode dari tanggal transaksi
							cal.setTime(esMcb.getTanggal());
							//cal.add(Calendar.MONTH, -1);
							
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH);
							month++;
							String strMonth = "";
							if (month < 10) {
								strMonth = "0" + month;
							} else {
								strMonth = "" + month;
							}
							
							String period = year + strMonth;
							row.setPeriode(period);
							//GET TOTAL SALDO IDR
							double totalIdr = 0;
							List<ViewESMultiCurrencyKurs> listKurs = getListMcbKursReport(esMcb.getNoRekening());
							for (ViewESMultiCurrencyKurs item : listKurs) {
								if (item.getSaldoIdrN() != null) {
									totalIdr = totalIdr + item.getSaldoIdrN().doubleValue();
								}
							}
							
							String periodeFolder = folder+File.separator+period;
							dir = new File(periodeFolder);
							if( !dir.exists() )
								dir.mkdirs();
							String filename = periodeFolder+File.separator+"e-Statement_Hardcopy_" + esMcb.getNoRekening() + "_" + esMcb.getProductTypeDesc() + "_" + period + "_" + tglCetak.getTime() + ".pdf";
//							String encryptedPass = accountListDao.getStatementPassword(row.getAccountNumber(), 0);
//							String password = null;
//							if( encryptedPass != null )
//								password = rsaUtil.rsaDecrypt(encryptedPass);
							
							ReportBulananStatement.generateReportMCB(elModel, esMcb, row, tglCetak, filename, null, null, totalIdr);
							
							//hapus by account no
							deleteMcbByAccountNo(row.getAccountNumber());
						}
					}
					
					//save to history
					for (ESMCBilingual item : mcb) {
						ESMCBHistory history = new ESMCBHistory(item);
						history.setPeriode(records.get(0).getPeriode());
						history.setType(6);
						history.setTglCetakStr(DateUtility.formatDate2(tglCetak));
						
						mcbHistory.add(history);
					}
					
					if (mcbHistory.size() > 0) {
						super.createBatch(mcbHistory.toArray());
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public ViewESAccountInfo getAccountNumberMcy(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_ACCOUNT_INFO_MCY WHERE ACCOUNT_NUMBER = ? AND ROWNUM=1";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNumber));
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		
		if( trans.size() == 0 )
			return null;
		else
			return serialize(ViewESAccountInfo.class, trans.get(0));		
	}
	
	public List<ViewESAccountInfo> getListAccountNumberMcy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_ACCOUNT_INFO_MCY";
		
		List<Map<String, Object>> trans = template.queryForList(sql);
		List<ViewESAccountInfo> records = new ArrayList<ViewESAccountInfo>();
		for (Map<String, Object> item : trans) {
			ViewESAccountInfo row = serialize(ViewESAccountInfo.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public List<ViewESAccountInfo> getListAccountNumberMcb() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_ACCOUNT_INFO_MCB";
		
		List<Map<String, Object>> trans = template.queryForList(sql);
		List<ViewESAccountInfo> records = new ArrayList<ViewESAccountInfo>();
		for (Map<String, Object> item : trans) {
			ViewESAccountInfo row = serialize(ViewESAccountInfo.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public ViewESAccountInfo getAccountNumberGiro(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_ACCOUNT_INFO_GIRO WHERE ACCOUNT_NUMBER = ? AND ROWNUM=1";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNumber));
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		
		if( trans.size() == 0 )
			return null;
		else
			return serialize(ViewESAccountInfo.class, trans.get(0));		
	}
	
	public List<ViewESAccountInfo> getListAccountNumberGiro() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_ACCOUNT_INFO_GIRO";
		
		List<Map<String, Object>> trans = template.queryForList(sql);
		List<ViewESAccountInfo> records = new ArrayList<ViewESAccountInfo>();
		for (Map<String, Object> item : trans) {
			ViewESAccountInfo row = serialize(ViewESAccountInfo.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public ESMultiCurrency getMcyByAccountNo(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM ES_MULTI_CURRENCY WHERE ACCOUNT_NUMBER = ? ORDER BY ID ASC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if (rows.size() > 0) {
			ESMultiCurrency mcy = serialize(ESMultiCurrency.class, rows.get(0));
			
			return mcy;
		}
		
		return null;
	}
	
	public ESMCBilingual getMcbByAccountNo(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM ES_MC_BILINGUAL WHERE NO_REKENING = ? ORDER BY ID ASC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if (rows.size() > 0) {
			ESMCBilingual mcb = serialize(ESMCBilingual.class, rows.get(0));
			
			return mcb;
		}
		
		return null;
	}
	
	public ESMultiCurrencyHistory getMcyHistoryByAccountNo(String accountNo, String periode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("ES_MCY_HISTORY");
		caller.declareParameters(
				new SqlParameter("ACCOUNT_NUMBER", OracleTypes.VARCHAR),
				new SqlParameter("PERIOD", OracleTypes.VARCHAR),
				new SqlOutParameter("REF_CURSOR", OracleTypes.CURSOR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNo), periode);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("REF_CURSOR");
		
		if (rows.size() > 0) {
			ESMultiCurrencyHistory mcy = serialize(ESMultiCurrencyHistory.class, rows.get(0));
			return mcy;
		}
		
		return null;
	}
	
	public ESMCBHistory getMcbHistoryByAccountNo(String accountNo, String periode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_ES_MCB_HISTORY");
		caller.declareParameters(
				new SqlParameter("ACCOUNT_NUMBER", OracleTypes.VARCHAR),
				new SqlParameter("PERIOD", OracleTypes.VARCHAR),
				new SqlOutParameter("REF_CURSOR", OracleTypes.CURSOR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNo), periode);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("REF_CURSOR");
		
		if (rows.size() > 0) {
			ESMCBHistory mcb = serialize(ESMCBHistory.class, rows.get(0));
			return mcb;
		}
		
		return null;
	}
	
	public ESGiro getGiroByAccountNo(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM ES_GIRO WHERE NO_REKENING = ? ORDER BY ID ASC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if (rows.size() > 0) {
			ESGiro giro = serialize(ESGiro.class, rows.get(0));
			
			return giro;
		}
		
		return null;
	}
	
	public ESGiroHistory getGiroHistoryByAccountNo(String accountNo, String periode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_ES_GIRO_STATEMENT");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR),
				new SqlParameter("P_PERIODE", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNo), periode);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		
		if (rows.size() > 0) {
			ESGiroHistory giro = serialize(ESGiroHistory.class, rows.get(0));
			return giro;
		}
		
		return null;		
	}
	
	public int getGiroType(String accountNo, String period) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_TYPE_GIRO");
		caller.declareParameters(
				new SqlParameter("P_ACCOUNT_NUMBER", OracleTypes.VARCHAR),
				new SqlParameter("P_PERIOD", OracleTypes.VARCHAR),
				new SqlOutParameter("GIRO_TYPE", OracleTypes.INTEGER));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNo), period);
		
		return (Integer) mValues.get("GIRO_TYPE");
	}
	
	public List<ViewESGiroFooterReport> getListGiroFooterReport(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_GIRO WHERE ACCOUNT_NUMBER = ? ORDER BY ID DESC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<ViewESGiroFooterReport> records = new ArrayList<ViewESGiroFooterReport>();
		for (Map<String, Object> item : trans) {
			ViewESGiroFooterReport row = serialize(ViewESGiroFooterReport.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public ViewESGiroFooterReport getGiroFooter(ViewESAccountInfo row) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		//footer giro
		String noFasilitas = "";
		double totalPlafond = 0;
		double totalPersenBunga = 0;
		String tanggalAwal = "";
		String tanggalAkhir = "";
		
		double totalTunggakanBunga = 0;
		double totalTunggakanDenda = 0;
		double totalTunggakanBiayaLain = 0;
		double totalTunggakan = 0;
		
		double totalDebet = 0;
		double totalCredit = 0;
		double totalSaldo = 0;
		double totalSaldoAwal = 0;
		
		List<ViewESGiroFooterReport> listFooter = getListGiroFooterReport(row.getAccountNumber());
		if( listFooter.size() == 0 )
			return null;
		for (ViewESGiroFooterReport item : listFooter) {
			//1
			if (!item.getNoFasilitas().trim().equals("")) {
				noFasilitas = item.getNoFasilitas();
			}
			totalPlafond = totalPlafond + item.getPlafond();
			totalPersenBunga = totalPersenBunga + item.getPersenBunga();
			if (!item.getTanggalAwal().trim().equals("")) {
				tanggalAwal = item.getTanggalAwal();
			}
			if (!item.getTanggalAkhir().trim().equals("")) {
				tanggalAkhir = item.getTanggalAkhir();
			}
			
			//2
			totalTunggakanBunga = totalTunggakanBunga + item.getTunggakanBunga();
			totalTunggakanDenda = totalTunggakanDenda + item.getTunggakanDenda();
			totalTunggakanBiayaLain = totalTunggakanBiayaLain + item.getTunggakanBiayaLain();
			totalTunggakan = totalTunggakan + item.getTotalTunggakan();
			
			//3
			totalDebet = totalDebet + item.getDebet();
			totalCredit = totalCredit + item.getCredit();
//			totalSaldo = totalSaldo + item.getSaldo();
			totalSaldoAwal = totalSaldoAwal + item.getSaldoAwal();
		}
		
		totalSaldo = listFooter.get(0).getSaldo();
		
		ViewESGiroFooterReport footer = new ViewESGiroFooterReport();
		footer.setAccountNumber(listFooter.get(0).getAccountNumber());
		footer.setNoFasilitas(noFasilitas);
		footer.setPlafond(totalPlafond);
		footer.setPersenBunga(totalPersenBunga);
		footer.setTanggalAwal(tanggalAwal);
		footer.setTanggalAkhir(tanggalAkhir);
		footer.setTunggakanBunga(totalTunggakanDenda);
		footer.setTunggakanDenda(totalTunggakanDenda);
		footer.setTunggakanBiayaLain(totalTunggakanBiayaLain);
		footer.setTotalTunggakan(totalTunggakan);
		footer.setDebet(totalDebet);
		footer.setCredit(totalCredit);
		footer.setSaldo(totalSaldo);
		footer.setSaldoAwal(totalSaldoAwal);
		
		return footer;
	}

	public List<ViewESGiroFooterHistoryReport> getListGiroFooterHistoryReport(String accountNo, String periode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_GIRO_HISTORY WHERE ACCOUNT_NUMBER = ? AND PERIODE = ? ORDER BY ID DESC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		args.add(periode);
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<ViewESGiroFooterHistoryReport> records = new ArrayList<ViewESGiroFooterHistoryReport>();
		for (Map<String, Object> item : trans) {
			ViewESGiroFooterHistoryReport row = serialize(ViewESGiroFooterHistoryReport.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public ViewESGiroFooterHistoryReport getGiroFooterHistory(ViewESAccountInfo row) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		//footer giro
		String noFasilitas = "";
		double totalPlafond = 0;
		double totalPersenBunga = 0;
		String tanggalAwal = "";
		String tanggalAkhir = "";
		
		double totalTunggakanBunga = 0;
		double totalTunggakanDenda = 0;
		double totalTunggakanBiayaLain = 0;
		double totalTunggakan = 0;
		
		double totalDebet = 0;
		double totalCredit = 0;
		double totalSaldo = 0;
		double totalSaldoAwal = 0;
		
		List<ViewESGiroFooterHistoryReport> listFooter = getListGiroFooterHistoryReport(row.getAccountNumber(), row.getPeriode());
		if( listFooter.size() == 0 )
			return null;
		for (ViewESGiroFooterHistoryReport item : listFooter) {
			//1
			if (!item.getNoFasilitas().trim().equals("")) {
				noFasilitas = item.getNoFasilitas();
			}
			totalPlafond = totalPlafond + item.getPlafond();
			totalPersenBunga = totalPersenBunga + item.getPersenBunga();
			if (!item.getTanggalAwal().trim().equals("")) {
				tanggalAwal = item.getTanggalAwal();
			}
			if (!item.getTanggalAkhir().trim().equals("")) {
				tanggalAkhir = item.getTanggalAkhir();
			}
			
			//2
			totalTunggakanBunga = totalTunggakanBunga + item.getTunggakanBunga();
			totalTunggakanDenda = totalTunggakanDenda + item.getTunggakanDenda();
			totalTunggakanBiayaLain = totalTunggakanBiayaLain + item.getTunggakanBiayaLain();
			totalTunggakan = totalTunggakan + item.getTotalTunggakan();
			
			//3
			totalDebet = totalDebet + item.getDebet();
			totalCredit = totalCredit + item.getCredit();
			totalSaldo = totalSaldo + item.getSaldo();
			totalSaldoAwal = totalSaldoAwal + item.getSaldoAwal();
		}
		
		totalSaldo = listFooter.get(0).getSaldo();
		
		ViewESGiroFooterHistoryReport footer = new ViewESGiroFooterHistoryReport();
		footer.setAccountNumber(listFooter.get(0).getAccountNumber());
		footer.setNoFasilitas(noFasilitas);
		footer.setPlafond(totalPlafond);
		footer.setPersenBunga(totalPersenBunga);
		footer.setTanggalAwal(tanggalAwal);
		footer.setTanggalAkhir(tanggalAkhir);
		footer.setTunggakanBunga(totalTunggakanDenda);
		footer.setTunggakanDenda(totalTunggakanDenda);
		footer.setTunggakanBiayaLain(totalTunggakanBiayaLain);
		footer.setTotalTunggakan(totalTunggakan);
		footer.setDebet(totalDebet);
		footer.setCredit(totalCredit);
		footer.setSaldo(totalSaldo);
		footer.setSaldoAwal(totalSaldoAwal);
		
		return footer;
	}
	
	public void deleteGiro() {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_GIRO";
		
		template.update(sql);
	}
	
	public void deleteGiroByAccountsNo(final List<String> accountNumbers){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_DELETE_GIRO_ACCOUNTS");
		caller.declareParameters(
				new SqlParameter("P_ACCOUNT_NO_S", OracleTypes.ARRAY, CommonCons.DB_SCHEMA + ".T_STRING"));
		
		String[] aKeys = accountNumbers.toArray(new String[0]);//Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
		SqlArrayValue<String> data = new SqlArrayValue<String>(aKeys);
		caller.execute(data);						
	}
	
	public void deleteGiroByAccountNo(String accountNo) {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_GIRO WHERE NO_REKENING = ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		template.update(sql, args.toArray());
	}
	
	public void deleteMcyByAccountsNo(final List<String> accountNumbers){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_DELETE_ES_MCY_ACCOUNTS");
		caller.declareParameters(
				new SqlParameter("P_ACCOUNT_NO_S", OracleTypes.ARRAY, CommonCons.DB_SCHEMA + ".T_STRING"));
		
		String[] aKeys = accountNumbers.toArray(new String[0]);//Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
		SqlArrayValue<String> data = new SqlArrayValue<String>(aKeys);
		caller.execute(data);						
	}
	
	public void deleteMcy() {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_MULTI_CURRENCY";
		
		template.update(sql);
	}
	
	public void deleteMcyByAccountNo(String accountNo) {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_MULTI_CURRENCY WHERE ACCOUNT_NUMBER = ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		template.update(sql, args.toArray());
	}
	
	public void deleteMcbByAccountsNo(final List<String> accountNumbers){
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_DELETE_ES_MCB_ACCOUNTS");
		caller.declareParameters(
				new SqlParameter("P_ACCOUNT_NO_S", OracleTypes.ARRAY, CommonCons.DB_SCHEMA + ".T_STRING"));
		
		String[] aKeys = accountNumbers.toArray(new String[0]);//Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
		SqlArrayValue<String> data = new SqlArrayValue<String>(aKeys);
		caller.execute(data);						
	}
	
	public void deleteMcbByAccountNo(String accountNo) {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_MC_BILINGUAL WHERE NO_REKENING = ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		template.update(sql, args.toArray());
	}
	
	public void deleteAllOditer() {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_ODTIER";
		
		template.update(sql);
	}
	
	public void deleteAllDdymow() {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".ES_DDY_ACCOUNT";
		
		template.update(sql);
	}
	
	public List<ViewESMultiCurrencyKurs> getListMcyKursReport(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT DISTINCT CURRENCY, KURS_VALAS_IDR, SALDO, SALDO_IDR, SALDO_IDR_N "
				+ "FROM VIEW_ES_MCY_KURS WHERE ACCOUNT_NUMBER = ? AND KURS_VALAS_IDR <> '0.00' "
				+ "ORDER BY CURRENCY";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<ViewESMultiCurrencyKurs> records = new ArrayList<ViewESMultiCurrencyKurs>();
		for (Map<String, Object> item : trans) {
			ViewESMultiCurrencyKurs row = serialize(ViewESMultiCurrencyKurs.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public List<ViewESMultiCurrencyKurs> getListMcbKursReport(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT DISTINCT CURRENCY, KURS_VALAS_IDR, SALDO, SALDO_IDR, SALDO_IDR_N "
				+ "FROM VIEW_ES_MCB_KURS WHERE ACCOUNT_NUMBER = ? AND KURS_VALAS_IDR <> '0.00' "
				+ "ORDER BY CURRENCY";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<ViewESMultiCurrencyKurs> records = new ArrayList<ViewESMultiCurrencyKurs>();
		for (Map<String, Object> item : trans) {
			ViewESMultiCurrencyKurs row = serialize(ViewESMultiCurrencyKurs.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public List<ViewESMultiCurrencyKurs> getListMcyKursHistoryReport(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT DISTINCT CURRENCY, KURS_VALAS_IDR, SALDO, SALDO_IDR, SALDO_IDR_N "
				+ "FROM VIEW_ES_MCY_KURS_HISTORY WHERE ACCOUNT_NUMBER = ? AND KURS_VALAS_IDR <> '0.00' "
				+ "ORDER BY CURRENCY";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<ViewESMultiCurrencyKurs> records = new ArrayList<ViewESMultiCurrencyKurs>();
		for (Map<String, Object> item : trans) {
			ViewESMultiCurrencyKurs row = serialize(ViewESMultiCurrencyKurs.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public List<ViewESMultiCurrencyKurs> getListMcbKursHistoryReport(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT DISTINCT CURRENCY, KURS_VALAS_IDR, SALDO, SALDO_IDR, SALDO_IDR_N "
				+ "FROM VIEW_ES_MCB_KURS_HISTORY WHERE ACCOUNT_NUMBER = ? AND KURS_VALAS_IDR <> '0.00' "
				+ "ORDER BY CURRENCY";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		List<ViewESMultiCurrencyKurs> records = new ArrayList<ViewESMultiCurrencyKurs>();
		for (Map<String, Object> item : trans) {
			ViewESMultiCurrencyKurs row = serialize(ViewESMultiCurrencyKurs.class, item);
			
			records.add(row);
		}
		
		return records;
	}
	
	public ESDdmast getDdmast(String accountNo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_ES_GET_DDMAST");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNo));
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		
		if (rows.size() > 0) {
			ESDdmast ddmast = serialize(ESDdmast.class, rows.get(0));
			return ddmast;
		}
		
		return null;
	}
	
	public ESAccountInfo getEsAccountInfo(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_ES_GET_ACCOUNT_INFO");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNumber));
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		
		if (rows.size() > 0) {
			ESAccountInfo info = serialize(ESAccountInfo.class, rows.get(0));
			return info;
		}
		
		return null;
	}
	
	public ESAccountInfo getEsAccountInfoByCif(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_ES_ACCOUNT_INFO_BY_CIF");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CIF_NO", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeCifKey(cifKey));
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		
		if (rows.size() > 0) {
			ESAccountInfo info = serialize(ESAccountInfo.class, rows.get(0));
			return info;
		}
		
		return null;
	}
	
	public void saveDDMastAccountList(final List<DDMastAccountList> ddMastAccountList) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_DDMAST_SAVE_ACCOUNT_LIST");
		caller.declareParameters(
				new SqlParameter("P_DDMAST_ACC_LIST", OracleTypes.ARRAY, "A_DDMAST_ACCOUNT_LIST"));
		
		StructMapper<DDMastAccountList> mapper = new BeanPropertyStructMapper<DDMastAccountList>(DDMastAccountList.class);
		DDMastAccountList[] aModel = ddMastAccountList.toArray(new DDMastAccountList[0]);
		SqlStructArrayValue<DDMastAccountList> data = 
				new SqlStructArrayValue<DDMastAccountList>(aModel, mapper, "T_DDMAST_ACCOUNT_LIST","A_DDMAST_ACCOUNT_LIST");
		caller.execute(data);
		setDefaultPassword();						
	}
	
	private void setDefaultPassword() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		int dataCount = 0;
		int start = 1;
		int end = 0;
		do{
			SimpleJdbcCall caller = new SimpleJdbcCall(template);
			caller.withSchemaName(CommonCons.DB_SCHEMA);
			caller.withProcedureName("SP_GET_NULL_STATEMENT_PASSWORD");
			caller.declareParameters(
					new SqlOutParameter("C1", OracleTypes.CURSOR)			
			);
			Map<String, Object> mValues = caller.execute();
			
			List<ESAccountInfo> listESai = new ArrayList<ESAccountInfo>();
			List<CustomerInfo> listCustomerInfo = new ArrayList<CustomerInfo>();
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
			dataCount = rows.size();
			if( dataCount > 0 ){
				end += dataCount;
				System.out.println("Process default password row "+start+" to "+end);
				start += dataCount;
			}
			for (Map<String, Object> row : rows) {
				String cifKey = (String)row.get("CIF_KEY");
				long customerInfoId = ((BigDecimal)row.get("ID")).longValue();
				Response res = customerInfoRequest.getUserInfo(cifKey);
				if( res.getResultCode() == ResponseErrorCode.SUCCESS ){
					UserInfo userInfo = (UserInfo)res.getData();
					ESAccountInfo esai = new ESAccountInfo();	
					esai.setCifNo(cifKey);
					esai.setDob(userInfo.getDob());
					esai.setEmail(userInfo.getEmail());
					listESai.add(esai);
					
					CustomerInfo ci = new CustomerInfo();
					ci.setId(customerInfoId);
					ci.setCifKey(cifKey);
					SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
					String password = rsaUtil.rsaEncrypt(sdf.format(userInfo.getDob()));
					ci.setStatementPassword(password);
					listCustomerInfo.add(ci);
				}
			}
			
			insertOrUpdateBatch(listESai.toArray());
			updateBatch(listCustomerInfo.toArray());
		}while(dataCount > 0 );
				
	}
	
	public List<ESGiroHistory> getRevESGiroHistory(String cifNo, String accountNo, String periode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_GIRO_HISTORY");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CIF", OracleTypes.VARCHAR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR),
				new SqlParameter("P_PERIODE", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeCifKey(cifNo), CommonUtils.normalizeAccNo(accountNo), periode);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		List<ESGiroHistory> datas = new ArrayList<ESGiroHistory>();
		for (Map<String, Object> row : rows) {
			ESGiroHistory giro = serialize(ESGiroHistory.class, row);
			
			datas.add(giro);
		}
		
		return datas;		
	}
	
	public List<ESMultiCurrencyHistory> getRevESMcyHistory(String cifNo, String accountNo, String periode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_REV_MCY_HISTORY");
		caller.declareParameters(
				new SqlOutParameter("C1", OracleTypes.CURSOR),
				new SqlParameter("P_CIF", OracleTypes.VARCHAR),
				new SqlParameter("P_ACCOUNT_NO", OracleTypes.VARCHAR),
				new SqlParameter("P_PERIODE", OracleTypes.VARCHAR));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeCifKey(cifNo), CommonUtils.normalizeAccNo(accountNo), periode);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> rows = (List<Map<String, Object>>) mValues.get("C1");
		List<ESMultiCurrencyHistory> datas = new ArrayList<ESMultiCurrencyHistory>();
		for (Map<String, Object> row : rows) {
			ESMultiCurrencyHistory mcy = serialize(ESMultiCurrencyHistory.class, row);
			
			datas.add(mcy);
		}
		
		return datas;		
	}
	
	public ViewESAccountInfo getAccountNumberGiroHistory(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM VIEW_ES_ACC_INFO_GIRO_HISTORY WHERE ACCOUNT_NUMBER = ? AND ROWNUM=1";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNumber));
		List<Map<String, Object>> trans = template.queryForList(sql, args.toArray());
		
		if( trans.size() == 0 )
			return null;
		else
			return serialize(ViewESAccountInfo.class, trans.get(0));		
	}
	
	public int getMcyType(String accountNo) {
		SimpleJdbcCall caller = new SimpleJdbcCall(template);
		caller.withSchemaName(CommonCons.DB_SCHEMA);
		caller.withProcedureName("SP_GET_MCY_TYPE");
		caller.declareParameters(
				new SqlParameter("P_ACCOUNT_NUMBER", OracleTypes.VARCHAR),
				new SqlOutParameter("MCY_TYPE", OracleTypes.INTEGER));
		
		Map<String, Object> mValues = caller.execute(CommonUtils.normalizeAccNo(accountNo));
		
		return (Integer) mValues.get("MCY_TYPE");
	}
}