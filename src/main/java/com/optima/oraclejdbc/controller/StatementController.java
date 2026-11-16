package com.optima.oraclejdbc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.lingala.zip4j.io.ZipOutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optima.oraclejdbc.api.ReqHardcopyApi;
import com.optima.oraclejdbc.api.ReqHardcopyDetailsApi;
import com.optima.oraclejdbc.api.ReverseRequestApi;
import com.optima.oraclejdbc.api.ReverseRequestDetailApi;
import com.optima.oraclejdbc.cons.HardcopyRequestStatus;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.CCDao;
import com.optima.oraclejdbc.daoimpl.DDIDao;
import com.optima.oraclejdbc.daoimpl.ESGiroDao;
import com.optima.oraclejdbc.daoimpl.ESMultiCurrencyDao;
import com.optima.oraclejdbc.daoimpl.ReqHardcopyDao;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.model.CCAccountBalance;
import com.optima.oraclejdbc.model.CCAccountHeader;
import com.optima.oraclejdbc.model.CCAccountLevelMessage;
import com.optima.oraclejdbc.model.CCAccountTrailer;
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
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.ReqHardcopy;
import com.optima.oraclejdbc.model.estatement.DDIModel;
import com.optima.oraclejdbc.model.estatement.ESDdmast;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBHistory;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrencyHistory;
import com.optima.oraclejdbc.model.estatement.ViewESAccountInfo;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterHistoryReport;
import com.optima.oraclejdbc.model.estatement.ViewESMultiCurrencyKurs;
import com.optima.oraclejdbc.parser.NISPReverse;
import com.optima.oraclejdbc.report.ReportBulananStatement;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.ResponseDataTable;
import com.optima.oraclejdbc.response.estatement.ReqHardcopyProcessResponse;
import com.optima.oraclejdbc.response.estatement.RetrieveAddressResponse;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.DateUtility;
import com.optima.oraclejdbc.utils.ReverseUtil;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StringUtility;

@RestController
@RequestMapping(value="/statement")
public class StatementController {

	private static final Logger logger = Logger.getLogger(StatementController.class);
			
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	DDIDao ddiDao;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	RsaUtil rsaUtil;
	
	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	ESGiroDao esGiroDao;
	
	@Autowired
	ESMultiCurrencyDao esMcyDao;
	
	@Autowired
	ReqHardcopyDao reqHardcopyDao;
	
	@Autowired
	NISPReverse<DDIModel> reverse;
	
	@Autowired
	NISPReverse<CCParentModel> ccReverse;
	
	@Autowired
	CCDao ccDao;
	
	@RequestMapping(value="check", method=RequestMethod.POST)
	public Response check(@RequestParam String accountNumber, @RequestParam String periode, @RequestParam String type){
		try{
			Response response = new Response();
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			boolean exists;
			if( type.equals("1") ){
				exists = esGiroDao.exists(accountNumber, periode);
			}else{
				exists = esMcyDao.exists(accountNumber, periode); 
			}
			
			response.setData(exists?1:0);
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value="print-hc", method=RequestMethod.POST)
	public Response printHc(@RequestBody ReqHardcopyApi data){
		try{
			ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
			String folder = CommonUtils.getMonthlyStatementHardcopyDir(confProperties);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			folder += sdf.format(cal.getTime())+File.separator;
			File fdFolder = new File(folder);
			if( !fdFolder.isDirectory() )
				fdFolder.mkdirs();
			List<Long> hcIds = new ArrayList<Long>();
			String alamat1 = "";
			String alamat2 = "";
			String alamat3 = "";
			String alamat4 = "";
			String[] alamatsAr = null;
			
			for (ReqHardcopyDetailsApi item : data.getDetails()) {
				hcIds.add(item.getId());
				
				alamatsAr = item.getAddress().split("[\\r\\n]+");
				for (int i = 0; i < alamatsAr.length; i++) {
					if (i == 0) {
						alamat1 = alamatsAr[i];
					} else if (i == 1) {
						alamat2 = alamatsAr[i];
					} else if (i == 2) {
						alamat3 = alamatsAr[i];
					} else if (i == 3) {
						alamat4 = alamatsAr[i];
					}
				}
				String accountNumber = item.getAccountNumber();
				String periode = item.getPeriode();
				int mcyType = ddiDao.getMcyType(accountNumber);
				if( mcyType == 1 ){
					ESMultiCurrencyHistory esMcy = ddiDao.getMcyHistoryByAccountNo(accountNumber, periode);
					esMcy.setAlamat1(alamat1);
					esMcy.setAlamat2(alamat2);
					esMcy.setAlamat3(alamat3);
					esMcy.setAlamat4(alamat4);
					String filename = folder+"e-Statement_" + esMcy.getAccountNumber() + "_" + esMcy.getProductTypeDesc() + "_" + periode + ".pdf";				
					double totalIdr = 0;
					List<ViewESMultiCurrencyKurs> listKurs = ddiDao.getListMcyKursHistoryReport(esMcy.getAccountNumber());
					for( ViewESMultiCurrencyKurs itemKurs : listKurs ){
						if( itemKurs.getSaldoIdrN() != null ){
							totalIdr+=itemKurs.getSaldoIdrN().doubleValue();
						}
					}
					ReportBulananStatement.generateReportMCYHistory(elModel, esMcy, filename, null, 1, 0, null, totalIdr, "");
				} else if( mcyType == 2 ){
					//mcb
					ESMCBHistory esMcb = ddiDao.getMcbHistoryByAccountNo(accountNumber, periode);
					esMcb.setAlamat1(alamat1);
					esMcb.setAlamat2(alamat2);
					esMcb.setAlamat3(alamat3);
					esMcb.setAlamat4(alamat4);
					String filename = folder+"e-Statement_" + esMcb.getNoRekening() + "_" + esMcb.getProductTypeDesc() + "_" + periode + ".pdf";				
					double totalIdr = 0;
					List<ViewESMultiCurrencyKurs> listKurs = ddiDao.getListMcbKursHistoryReport(esMcb.getNoRekening());
					for( ViewESMultiCurrencyKurs itemKurs : listKurs ){
						if( itemKurs.getSaldoIdrN() != null ){
							totalIdr+=itemKurs.getSaldoIdrN().doubleValue();
						}
					}
					ReportBulananStatement.generateReportMCBHistory(elModel, esMcb, filename, null, 1, 0, null, totalIdr, "");
				}else{
					ESGiroHistory esGiro = ddiDao.getGiroHistoryByAccountNo(accountNumber, periode);
					esGiro.setAlamat1(alamat1);
					esGiro.setAlamat2(alamat2);
					esGiro.setAlamat3(alamat3);
					esGiro.setAlamat4(alamat4);
					String filename = folder+"e-Statement_" + esGiro.getNoRekening() + "_" + esGiro.getProductTypeDesc() + "_" + periode + ".pdf";
					ViewESAccountInfo row = ddiDao.getAccountNumberGiroHistory(accountNumber);
					ViewESGiroFooterHistoryReport footer = ddiDao.getGiroFooterHistory(row);
					ReportBulananStatement.generateReportGiroHistory(elModel, esGiro, footer, filename, null, 1, 0, null, "");
				}
			}
			//process req hc
			Long batchId = reqHardcopyDao.processReqHardcopy(hcIds);
			String batchIdStr = this.paddingBatchId(batchId);
			
			//response
			ReqHardcopyProcessResponse processResponse = new ReqHardcopyProcessResponse();
			processResponse.setBatchId(batchIdStr);
			//processResponse.setFilename(filename);
			processResponse.setHcReqIds(hcIds);
			Response res = new Response();
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			res.setData(processResponse);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value="print", method=RequestMethod.POST)
	public void print(@RequestParam String accountNumber, @RequestParam String periode,
			@RequestParam Integer category, @RequestParam int isPrint, @RequestParam(defaultValue="0") int isReprint
			, @RequestParam(defaultValue="0") int oneStep, @RequestParam(defaultValue = "") String tglCetakStr, 
			HttpServletResponse servletResponse){
		try {
			
			Response response = new Response();
			ElixirModel elModel = CommonUtils.getElixirModel(confProperties);
			String folder = CommonUtils.getMonthlyStatementDir(confProperties);
			String tempDir = CommonUtils.getTempDir(confProperties);
			String encryptedPass = accountListDao.getStatementPassword(accountNumber, 0);		
			String password = null;
			if( encryptedPass != null )
				password =rsaUtil.rsaDecrypt(encryptedPass);
			String uuid = UUID.randomUUID().toString();
			OutputStream fos = null;
			try {
				if( oneStep == 0 )
					fos = new FileOutputStream(tempDir+uuid);
				else
					fos = servletResponse.getOutputStream();
				if( category == 1 || category == 6 ){
					int mcyType = ddiDao.getMcyType(accountNumber);
					
					if (mcyType == 1) {
						//mcy
						ESMultiCurrencyHistory esMcy = ddiDao.getMcyHistoryByAccountNo(accountNumber, periode);
						if( esMcy == null ){
							servletResponse.getOutputStream().write(new byte[0]);
							return;
						}
						String filename = folder+"mcy"+File.separator+"e-Statement_" + esMcy.getAccountNumber() + "_" + esMcy.getProductTypeDesc() + "_" + periode + ".pdf";				
						double totalIdr = 0;
						List<ViewESMultiCurrencyKurs> listKurs = ddiDao.getListMcyKursHistoryReport(esMcy.getAccountNumber());
						for( ViewESMultiCurrencyKurs item : listKurs ){
							if( item.getSaldoIdrN() != null ){
								totalIdr+=item.getSaldoIdrN().doubleValue();
							}
						}
						ReportBulananStatement.generateReportMCYHistory(elModel, esMcy, filename, password, isPrint, isReprint, fos, totalIdr, tglCetakStr);
					} else if (mcyType == 2) {
						//mcb
						ESMCBHistory esMcb = ddiDao.getMcbHistoryByAccountNo(accountNumber, periode);
						if( esMcb == null ){
							servletResponse.getOutputStream().write(new byte[0]);
							return;
						}
						String filename = folder+"mcb"+File.separator+"e-Statement_" + esMcb.getNoRekening() + "_" + esMcb.getProductTypeDesc() + "_" + periode + ".pdf";				
						double totalIdr = 0;
						List<ViewESMultiCurrencyKurs> listKurs = ddiDao.getListMcbKursHistoryReport(esMcb.getNoRekening());
						for( ViewESMultiCurrencyKurs item : listKurs ){
							if( item.getSaldoIdrN() != null ){
								totalIdr+=item.getSaldoIdrN().doubleValue();
							}
						}
						ReportBulananStatement.generateReportMCBHistory(elModel, esMcb, filename, password, isPrint, isReprint, fos, totalIdr, tglCetakStr);
					} else {
						
					}				
				}else{
					ESGiroHistory esGiro = ddiDao.getGiroHistoryByAccountNo(accountNumber, periode);
					if( esGiro == null ){
						servletResponse.getOutputStream().write(new byte[0]);
						return;
					}
					String filename = folder+"giro"+File.separator+"e-Statement_" + esGiro.getNoRekening() + "_" + esGiro.getProductTypeDesc() + "_" + periode + ".pdf";
					ViewESAccountInfo row = ddiDao.getAccountNumberGiroHistory(accountNumber);
					ViewESGiroFooterHistoryReport footer = ddiDao.getGiroFooterHistory(row);
					ReportBulananStatement.generateReportGiroHistory(elModel, esGiro, footer, filename, password, isPrint, isReprint, fos, tglCetakStr);
				}
				
				response.setData(uuid);
				response.setResultCode(ResponseErrorCode.SUCCESS);
				response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			}finally{
				if( fos != null )
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			if( oneStep == 0 ){
				PrintWriter out = servletResponse.getWriter();
				ObjectMapper mapper = new ObjectMapper();
				out.print(mapper.writeValueAsString(response));
			}			
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
		}
		
	}
	
	@RequestMapping(value = "reverse-giro", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Serializable reverseGiro(@RequestBody ReverseRequestApi data, HttpServletRequest request) {
		try {
			Response res = new Response();
			
			String reverseDir = CommonUtils.getReverseWorkDir(confProperties);
			
			List<String> listResult = new ArrayList<String>();
			String periode = "";
			for (ReverseRequestDetailApi item : data.getPeriods()) {
				if (item.getMonth() < 10) {
					periode = item.getYear() + "0" + item.getMonth();
				} else {
					periode = item.getYear() + item.getMonth() + "";
				}
				
//				List<ESGiroHistory> listData = ddiDao.getRevESGiroHistory(data.getCifNo(), periode);
//				List<String> result = reverse.reverse(listData.toArray(), "");
//				listResult.addAll(result);
			}
			
			ReverseUtil.writeToFileES(reverseDir, listResult, 1, periode);
			
			res.setResultCode(1000);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "reverse-mcy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Serializable reverseMcy(@RequestBody ReverseRequestApi data, HttpServletRequest request) {
		try {
			Response res = new Response();
			
			String reverseDir = CommonUtils.getReverseWorkDir(confProperties);
			
			List<String> listResult = new ArrayList<String>();
			String periode = "";
			for (ReverseRequestDetailApi item : data.getPeriods()) {
				if (item.getMonth() < 10) {
					periode = item.getYear() + "0" + item.getMonth();
				} else {
					periode = item.getYear() + item.getMonth() + "";
				}
				
//				List<ESMultiCurrencyHistory> listData = ddiDao.getRevESMcyHistory(data.getCifNo(), periode);
//				List<String> result = reverse.reverse(listData.toArray(), "");
//				listResult.addAll(result);
			}
			
			ReverseUtil.writeToFileES(reverseDir, listResult, 2, periode);
			
			res.setResultCode(1000);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}				
	}
	
	@RequestMapping(value="getaddress", method=RequestMethod.POST)
	public Serializable getAddress(Model model, @RequestParam String accountNumber, @RequestParam String periode,
			@RequestParam Integer category, HttpServletResponse response) {
		try {
			Response res = new Response();
			
			RetrieveAddressResponse ret = new RetrieveAddressResponse();
			if (category == 1) {
				int mcyType = ddiDao.getMcyType(accountNumber);
				if( mcyType == 1 ){
					ESMultiCurrencyHistory esMcy = ddiDao.getMcyHistoryByAccountNo(accountNumber, periode);
					ret.setAddress1(StringUtility.checkStr(esMcy.getAlamat1()));
					ret.setAddress2(StringUtility.checkStr(esMcy.getAlamat2()));
					ret.setAddress3(StringUtility.checkStr(esMcy.getAlamat3()));
					ret.setAddress4(StringUtility.checkStr(esMcy.getAlamat4()));
				}else{
					ESMCBHistory esMcb = ddiDao.getMcbHistoryByAccountNo(accountNumber, periode);
					ret.setAddress1(StringUtility.checkStr(esMcb.getAlamat1()));
					ret.setAddress2(StringUtility.checkStr(esMcb.getAlamat2()));
					ret.setAddress3(StringUtility.checkStr(esMcb.getAlamat3()));
					ret.setAddress4(StringUtility.checkStr(esMcb.getAlamat4()));
				}
				
			} else {
				ESGiroHistory esGiro = ddiDao.getGiroHistoryByAccountNo(accountNumber, periode);
				ret.setAddress1(StringUtility.checkStr(esGiro.getAlamat1()));
				ret.setAddress2(StringUtility.checkStr(esGiro.getAlamat2()));
				ret.setAddress3(StringUtility.checkStr(esGiro.getAlamat3()));
				ret.setAddress4(StringUtility.checkStr(esGiro.getAlamat4()));
			}
			
			res.setData(ret);
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public Serializable submit(Model model, @RequestParam String accountNumber, @RequestParam String currency, 
			@RequestParam String periode, @RequestParam String address, HttpServletResponse response) {
		
		try {
			Response res = new Response();
			
			int rs = reqHardcopyDao.insertRequest(periode, accountNumber, currency, address);
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage("Request Hardcopy Success !");
			res.setData(rs);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "gethistory", method = RequestMethod.POST)
	public Serializable getHistory(Model model, @RequestParam int start, @RequestParam int length, @RequestParam String cif,
			@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate, 
			@RequestParam String periodeFrom, @RequestParam String periodeTo) {
		
		try {
			ResponseDataTable res = new ResponseDataTable();
			
			Date dateStart = DateUtility.parseDate3(startDate);
			Date dateEnd = DateUtility.parseDate3(endDate);
			
			Integer periodeStart = null;
			Integer periodeEnd = null;
			
			if (!periodeFrom.equals("")) {
				periodeStart = Integer.parseInt(periodeFrom);
			}
			
			if (!periodeTo.equals("")) {
				periodeEnd = Integer.parseInt(periodeTo);
			}
			
			List<ReqHardcopy> listData = new ArrayList<ReqHardcopy>();
			listData = reqHardcopyDao.getListDataByParam(cif, accountNumber, dateStart, dateEnd, periodeStart, periodeEnd, start, length);
			int total = reqHardcopyDao.getTotalRow(cif, accountNumber, dateStart, dateEnd, periodeStart, periodeEnd);
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			res.setData(listData);
			res.setRecordsFiltered(total);
			res.setRecordsTotal(total);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "re-req-hc", method = RequestMethod.POST)
	public Response HardcopyReRequest(Model model,  @RequestParam Long hcReqId) {
		
		try {
			Response res = new Response();
			
			reqHardcopyDao.updateReqHardcopy(hcReqId, HardcopyRequestStatus.PENDING);
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "cancel-req-hc", method = RequestMethod.POST)
	public Response cancelReqHardcopy(Model model, @RequestBody List<Long> ids) {
		try {
			Response res = new Response();
			
			Long batchId = reqHardcopyDao.cancelReqHardcopy(ids);
			String batchIdStr = this.paddingBatchId(batchId);
			
			//response
			ReqHardcopyProcessResponse processResponse = new ReqHardcopyProcessResponse();
			processResponse.setBatchId(batchIdStr);
			processResponse.setFilename(null);
			processResponse.setHcReqIds(ids);
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			res.setData(processResponse);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "process-req-hc", method = RequestMethod.POST)
	public Response reqHardcopy(@RequestBody ReqHardcopyApi data) {
		try {
			Response res = new Response();
			
			ESDdmast ddmast = null;
			
			//es
			List<String> listResultGiro = new ArrayList<String>();
			List<String> listResultMcy = new ArrayList<String>();
			
			//cc
			List<String> listResultCc = new ArrayList<String>();
			
			String alamat1 = "";
			String alamat2 = "";
			String alamat3 = "";
			String alamat4 = "";
			List<String> alamats = null;
			String[] alamatsAr = null;
			
			List<String> addresses = null;
			
			List<Long> hcIds = new ArrayList<Long>();
			
			for (ReqHardcopyDetailsApi item : data.getDetails()) {
				hcIds.add(item.getId());
				
				ddmast = ddiDao.getDdmast(item.getAccountNumber());
				
				addresses = new ArrayList<String>();
				
				if (ddmast != null) {
					alamatsAr = item.getAddress().split("[\\r\\n]+");
					for (int i = 0; i < alamatsAr.length; i++) {
						if (i == 0) {
							alamat1 = alamatsAr[i];
						} else if (i == 1) {
							alamat2 = alamatsAr[i];
						} else if (i == 2) {
							alamat3 = alamatsAr[i];
						} else if (i == 3) {
							alamat4 = alamatsAr[i];
						}
					}
					
					addresses.add(alamat1);
					addresses.add(alamat2);
					addresses.add(alamat3);
					addresses.add(alamat4);
					
					//giro
					List<ESGiroHistory> listDataGiro = ddiDao.getRevESGiroHistory(ddmast.getCifNo(), item.getAccountNumber(), item.getPeriode());
					List<String> resultGiro = reverse.reverseObj(listDataGiro.toArray(), addresses, "");
					listResultGiro.addAll(resultGiro);
					
					//mcy
					List<ESMultiCurrencyHistory> listDataMcy = ddiDao.getRevESMcyHistory(ddmast.getCifNo(), item.getAccountNumber(), item.getPeriode());
					List<String> resultMcy = reverse.reverseObj(listDataMcy.toArray(), addresses, "");
					listResultMcy.addAll(resultMcy);
					
					//cc
					Date start = null;
					Date end = null;
					
					int month = Integer.parseInt(item.getPeriode().substring(4));
					int year = Integer.parseInt(item.getPeriode().substring(0, 4));
					
					Calendar calStart = Calendar.getInstance();
					calStart.set(Calendar.MONTH, month - 1);
					calStart.set(Calendar.YEAR, year);
					calStart.set(Calendar.DATE, 1);
					
					Calendar calEnd = Calendar.getInstance();
					calEnd.set(Calendar.MONTH, month - 1);
					calEnd.set(Calendar.YEAR, year);
					calEnd.set(Calendar.DATE, calEnd.getActualMaximum(Calendar.DATE));
					
					start = calStart.getTime();
					end = calEnd.getTime();
					
					List<CCCustomerHeader> resultCh = ccDao.getListRevCC_02_CH(ddmast.getCifNo(), start, end);
					
					if (resultCh.size() > 0) {
						List<CCStatement> listFh = new ArrayList<CCStatement>();
						List<CCCustomerHeader> listCh = new ArrayList<CCCustomerHeader>();
						List<CCCustomerHeaderMessage> listCm = new ArrayList<CCCustomerHeaderMessage>();
						List<CCAccountLevelMessage> listAm = new ArrayList<CCAccountLevelMessage>();
						List<CCAccountHeader> listAh = new ArrayList<CCAccountHeader>();
						List<CCTransactionDetails> listTd = new ArrayList<CCTransactionDetails>();
						List<CCDualCurrency> listDc = new ArrayList<CCDualCurrency>();
						List<CCLuckyDraw> listLd = new ArrayList<CCLuckyDraw>();
						List<CCAccountBalance> listAb = new ArrayList<CCAccountBalance>();
						List<CCRewardSummary> listBs = new ArrayList<CCRewardSummary>();
						List<CCRateInfo> listRi = new ArrayList<CCRateInfo>();
						List<CCStatementMessage> listMs = new ArrayList<CCStatementMessage>();
						List<CCPromotionalMessage> listMt = new ArrayList<CCPromotionalMessage>();
						List<CCFooterMessage> listMf = new ArrayList<CCFooterMessage>();
						List<CCAccountTrailer> listAt = new ArrayList<CCAccountTrailer>();
						List<CCCustomerTrailer> listCt = new ArrayList<CCCustomerTrailer>();
						List<CCFileTrailer> listFt = new ArrayList<CCFileTrailer>();
						
						CCStatement fh = null;
						CCCustomerHeader ch = null;
						CCCustomerHeaderMessage cm = null;
						CCAccountLevelMessage am = null;
						CCAccountHeader ah = null;
						CCDualCurrency dc = null;
						CCLuckyDraw ld = null;
						CCAccountBalance ab = null;
						CCRewardSummary bs = null;
						CCRateInfo ri = null;
						CCStatementMessage ms = null;
						CCPromotionalMessage mt = null;
						CCFooterMessage mf = null;
						CCAccountTrailer at = null;
						CCCustomerTrailer ct = null;
						CCFileTrailer ft = null;
						
						for (CCCustomerHeader itemCh : resultCh) {
							Long chId = itemCh.getId();
							Long fhId = itemCh.getCcStatementId();
							
							fh = ccDao.getRevCC_01_FH(fhId);
							if (fh != null) {
								listFh.add(fh);
								List<String> results = ccReverse.reverseObj(listFh.toArray(), addresses,  "");
								listResultCc.addAll(results);
							}
							
							ch = itemCh;
							if (ch != null) {
								listCh.add(ch);
								List<String> results = ccReverse.reverseObj(listCh.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							cm = ccDao.getRevCC_03_CM(chId);
							if (cm != null) {
								listCm.add(cm);
								List<String> results = ccReverse.reverseObj(listCm.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							am = ccDao.getRevCC_04_AM(chId);
							if (am != null) {
								listAm.add(am);
								List<String> results = ccReverse.reverseObj(listAm.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ah = ccDao.getRevCC_05_AH(chId);
							if (ah != null) {
								listAh.add(ah);
								List<String> results = ccReverse.reverseObj(listAh.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							listTd = ccDao.getListRevCC_06_TD(ah.getId());
							List<String> resultsTd = ccReverse.reverseObj(listTd.toArray(), addresses, "");
							listResultCc.addAll(resultsTd);
							
							dc = ccDao.getRevCC_07_DC(chId);
							if (dc != null) {
								listDc.add(dc);
								List<String> results = ccReverse.reverseObj(listDc.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ld = ccDao.getRevCC_08_LD(chId);
							if (ld != null) {
								listLd.add(ld);
								List<String> results = ccReverse.reverseObj(listLd.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ab = ccDao.getRevCC_09_AB(chId);
							if (ab != null) {
								listAb.add(ab);
								List<String> results = ccReverse.reverseObj(listAb.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							bs = ccDao.getRevCC_10_BS(chId);
							if (bs != null) {
								listBs.add(bs);
								List<String> results = ccReverse.reverseObj(listBs.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ri = ccDao.getRevCC_11_RI(chId);
							if (ri != null) {
								listRi.add(ri);
								List<String> results = ccReverse.reverseObj(listRi.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ms = ccDao.getRevCC_12_MS(chId);
							if (ms != null) {
								listMs.add(ms);
								List<String> results = ccReverse.reverseObj(listMs.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							mt = ccDao.getRevCC_13_MT(chId);
							if (mt != null) {
								listMt.add(mt);
								List<String> results = ccReverse.reverseObj(listMt.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							mf = ccDao.getRevCC_14_MF(chId);
							if (mf != null) {
								listMf.add(mf);
								List<String> results = ccReverse.reverseObj(listMf.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							at = ccDao.getRevCC_15_AT(chId);
							if (at != null) {
								listAt.add(at);
								List<String> results = ccReverse.reverseObj(listAt.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ct = ccDao.getRevCC_16_CT(chId);
							if (ct != null) {
								listCt.add(ct);
								List<String> results = ccReverse.reverseObj(listCt.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
							
							ft = ccDao.getRevCC_17_FT(chId);
							if (ft != null) {
								listFt.add(ft);
								List<String> results = ccReverse.reverseObj(listFt.toArray(), addresses, "");
								listResultCc.addAll(results);
							}
						}
					}
				}
			}
			
			//process req hc
			Long batchId = reqHardcopyDao.processReqHardcopy(hcIds);
			String batchIdStr = this.paddingBatchId(batchId);
			Date today = new Date();
			String createdDate = DateUtility.formatDate5(today);
			
			String filename = "DDI_" + createdDate + "_" + batchIdStr + ".zip";
			String tempDir = CommonUtils.getTempDir(confProperties);
			
			FileOutputStream fos = new FileOutputStream(tempDir + filename);
			ZipOutputStream zs = new ZipOutputStream(fos);
			
			//write
			if (listResultGiro.size() > 0) {
				ReverseUtil.writeToFileESBatch(zs, data.getPassword(), listResultGiro, 1, batchIdStr, createdDate);
			}
			
			if (listResultMcy.size() > 0) {
				ReverseUtil.writeToFileESBatch(zs, data.getPassword(), listResultMcy, 2, batchIdStr, createdDate);
			}
			
//			ReverseUtil.writeToFileCCBatch(reverseDir, listResultCc, batchIdStr);
			
			zs.finish();
			zs.close();
			
			//response
			ReqHardcopyProcessResponse processResponse = new ReqHardcopyProcessResponse();
			processResponse.setBatchId(batchIdStr);
			processResponse.setFilename(filename);
			processResponse.setHcReqIds(hcIds);
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			res.setData(filename);
//			res.setData(processResponse);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	private String paddingBatchId(Long batchId) {
		String result = "";
		
		int length = String.valueOf(batchId).length();
		int padding = 6 - length;
		
		for (int i = 0; i < padding; i++) {
			result += "0";
		}
		
		result += batchId;
		
		return result;
	}
	
	@RequestMapping(value = "getaccstatement", method = RequestMethod.POST)
	public Response getAccountStatement(Model model, @RequestParam String cifKey) {
		try {
			Response res = new Response();
			
			List<String> accStatements = accountListDao.getAccountStatement(cifKey);
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			res.setData(accStatements);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
	}
}
