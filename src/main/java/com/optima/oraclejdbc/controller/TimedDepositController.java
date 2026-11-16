package com.optima.oraclejdbc.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elixirtech.ers2.client.ERSClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.RESConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.RsDetail_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.RsSubTDAccountInfo_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.RsSubTDDetailInfo_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.TDDetailInfo_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailtimeddeposit.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailtimeddeposit.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailtimeddeposit.ServiceEnvelopeE;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_detailtimeddeposit.operationimpl.services.wbst__inquiry__detailtimeddeposit.processes.WBST_Inquiry_DetailTimedDepositStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.dailystatement.TDAResponse;
import com.optima.oraclejdbc.response.dailystatement.TimedDepositResponse;
import com.optima.oraclejdbc.service.AccountListRegisterService;
import com.optima.oraclejdbc.service.TimedDepositService;
import com.optima.oraclejdbc.tibco.timeddeposit.TimedDepositServiceEnvelopeE;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value="/timeddeposit")
public class TimedDepositController {

	private static final Logger logger = Logger.getLogger(TimedDepositController.class);
			
	@Autowired
	TimedDepositService service;
	
	@Autowired
	StubUtil stubUtil;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Autowired
	RsaUtil rsaUtil;
	
	@Autowired
	AccountListRegisterService accountListService;
	
	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getTimeDepositSummary(@RequestParam String userId, @RequestParam String cifKey) {
		try {
			Response res = new Response();
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			WBST_Inquiry_DetailTimedDepositStub stub = new WBST_Inquiry_DetailTimedDepositStub(strContextPath+"Services/WBST_Inquiry_DetailTimedDeposit/Processes/MainRequestResponse_HTTP");
			ServiceEnvelopeE serviceEnvelopeE = TimedDepositServiceEnvelopeE.instance();
			TimedDepositServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			TimedDepositServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				return res;
			}
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			
			// dummy response
//			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//			ServiceBody serviceBody = new ServiceBody();
//			
//			//response
//			serviceEnvelope = new ServiceEnvelope();
//			
//			RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//			RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//			
//			RsSubTDDetailInfo_type0 rsSubTDDetailInfo_type0 = new RsSubTDDetailInfo_type0();
//			TDDetailInfo_type0 tdDetailInfo_type0[] = new TDDetailInfo_type0[1];
//			TDDetailInfo_type0 tdDetail = new TDDetailInfo_type0();
//			
//			tdDetail.setCurrencyType("IDR");
//			tdDetail.setAccountNo1("000010079049");
//			tdDetail.setEffectiveDate(new Date());
//			tdDetail.setMaturityDate(new Date());
//			tdDetail.setPrincipalAmount(new BigDecimal(2000000));
//			tdDetail.setInterestAvailAmount(new BigDecimal(3000000));
//			tdDetail.setInterestTerm(new BigInteger("2"));
//			tdDetail.setTDTerm(new BigInteger("6"));
//			tdDetail.setTDTermCode("M");
//			tdDetail.setInterestRate(new BigDecimal(0.3));
//			tdDetail.setNoBilyet("000012");
//			tdDetail.setIBFlag(1);
//			tdDetailInfo_type0[0] = tdDetail;
//			
//			rsSubTDDetailInfo_type0.setTDDetailInfo(tdDetailInfo_type0);
//			resConsumer_type0.setRsSubTDDetailInfo(rsSubTDDetailInfo_type0);
//			rsDetail_type0.setRESConsumer(resConsumer_type0);
//			
//			serviceBody.setRsDetail(rsDetail_type0);
//			serviceEnvelope.setServiceBody(serviceBody);
			
			//end of dummy response
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				List<TimedDepositResponse> data = new ArrayList<TimedDepositResponse>();
				if( serviceEnvelope.getServiceBody().getRsDetail()
				.getRESConsumer().isRsSubTDDetailInfoSpecified() ){
					data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getRsSubTDDetailInfo().getTDDetailInfo());
				}
				
				
				res.setData(data);
				
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}	
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "getaccdetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getTimeDepositAccountDetail(@RequestParam String userId, @RequestParam String cifKey
			, @RequestParam String accountNumber) {
		try {
			Response res = new Response();
			//stub
			String strContextPath = CommonUtils.getContextPath(confProperties);
			WBST_Inquiry_DetailTimedDepositStub stub = new WBST_Inquiry_DetailTimedDepositStub(strContextPath+"Services/WBST_Inquiry_DetailTimedDeposit/Processes/MainRequestResponse_HTTP");
			ServiceEnvelopeE serviceEnvelopeE = TimedDepositServiceEnvelopeE.instance();
			TimedDepositServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
			TimedDepositServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
			TimedDepositServiceEnvelopeE.setAccountNo(serviceEnvelopeE, accountNumber);
			TimedDepositServiceEnvelopeE.setAccountType(serviceEnvelopeE, "T");
			TimedDepositServiceEnvelopeE.setRequestDetailAccount(serviceEnvelopeE, accountNumber);
			
			ServiceEnvelopeE response = (ServiceEnvelopeE)stubUtil.request(stub, serviceEnvelopeE);
			if( response == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setData(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				return res;
			}
			ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
			
			// dummy response
//			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//			ServiceBody serviceBody = new ServiceBody();
//			
//			//response
//			serviceEnvelope = new ServiceEnvelope();
//			
//			RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//			RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//			
//			RsSubTDDetailInfo_type0 rsSubTDDetailInfo_type0 = new RsSubTDDetailInfo_type0();
//			TDDetailInfo_type0 tdDetailInfo_type0[] = new TDDetailInfo_type0[1];
//			TDDetailInfo_type0 tdDetail = new TDDetailInfo_type0();
//			
//			tdDetail.setCurrencyType("IDR");
//			tdDetail.setAccountNo1("000010079049");
//			tdDetail.setEffectiveDate(new Date());
//			tdDetail.setMaturityDate(new Date());
//			tdDetail.setPrincipalAmount(new BigDecimal(2000000));
//			tdDetail.setInterestAvailAmount(new BigDecimal(3000000));
//			tdDetail.setInterestTerm(new BigInteger("2"));
//			tdDetail.setTDTerm(new BigInteger("6"));
//			tdDetail.setTDTermCode("M");
//			tdDetail.setInterestRate(new BigDecimal(0.3));
//			tdDetail.setNoBilyet("000012");
//			tdDetail.setIBFlag(1);
//			tdDetailInfo_type0[0] = tdDetail;
//			
//			rsSubTDDetailInfo_type0.setTDDetailInfo(tdDetailInfo_type0);
//			resConsumer_type0.setRsSubTDDetailInfo(rsSubTDDetailInfo_type0);
//			rsDetail_type0.setRESConsumer(resConsumer_type0);
//			
//			serviceBody.setRsDetail(rsDetail_type0);
//			serviceEnvelope.setServiceBody(serviceBody);
			
			//end of dummy response
			
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				List<TimedDepositResponse> data = new ArrayList<TimedDepositResponse>();
				if( serviceEnvelope.getServiceBody().getRsDetail()
				.getRESConsumer().isRsSubTDDetailInfoSpecified() ){
					data = service.getSummary(serviceEnvelope.getServiceBody().getRsDetail()
							.getRESConsumer().getRsSubTDDetailInfo().getTDDetailInfo());
				}
				
				
				res.setData(data);
				
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
		
	}
	
	@RequestMapping(value = "gettda", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getTimeDepositAdvice(@RequestParam String userId, @RequestParam String cifKey, 
			@RequestParam String accountNo, @RequestParam String currency) {
		try {
			Response res = new Response();
			ServiceEnvelope serviceEnvelope = getServiceEnvelope(userId, cifKey, accountNo, currency);
			if( serviceEnvelope == null ){
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);
				return res;
			}
			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				res.setData(new TDAResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getRsSubTDAccountInfo()));
			}else{
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}		
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
	
	@RequestMapping(value = "print-tda", method = RequestMethod.POST)
	public void printTDA(@RequestParam String accountNo, @RequestParam String currency,
			@RequestParam String userId, @RequestParam String cifKey, @RequestParam int isPrint, @RequestParam(defaultValue="0") int oneStep,
			HttpServletResponse servletResponse) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {		
			Response response = new Response();
			ServiceEnvelope serviceEnvelope = getServiceEnvelope(userId, cifKey, accountNo, currency);
			if( serviceEnvelope != null ){				
				if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
					TDAResponse tda = new TDAResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getRsSubTDAccountInfo());
					//print
					String elHostname = CommonUtils.getElixirHostname(confProperties);
					int elPort = CommonUtils.getElixirPort(confProperties);
					String elUsername = CommonUtils.getElixirUsername(confProperties);
					String elPassword = CommonUtils.getElixirPassword(confProperties);
					String elResources = CommonUtils.getElixirResources(confProperties);
					ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
					
					//put parameter
					java.util.Properties prop = new java.util.Properties();
					prop.put("ACCOUNT_NO", tda.getAccountNo());
					prop.put("TDA_DATE", tda.getTdaDate());
					prop.put("TDA_NAME", tda.getTdaName());
					prop.put("TDA_LOCATION", tda.getTdaLocation());
					prop.put("TDA_ADDRESS", tda.getTdaAddress());
					prop.put("TDA_TENOR", tda.getTenor());
					prop.put("VALUTA_DATE", tda.getValutaDate());
					prop.put("MATURITY_DATE", tda.getMaturityDate());
					prop.put("CURRENCY", tda.getCurrency());
					prop.put("PRINCIPAL_AMOUNT", tda.getPrincipalAmount());
					prop.put("INTEREST_RATE", tda.getInterestAmount());
					prop.put("WORDS", tda.getWords());
					prop.put("MATURITY", tda.getMaturity());
					prop.put("INTEREST_AMOUNT", tda.getInterestAmount());
					prop.put("TAXES", tda.getTaxes());
					prop.put("NET_INTEREST", tda.getNetInterest());
					prop.put("LOGO", elResources + "/img/" + tda.getLogo());
					
					String elWorkspace = CommonUtils.getElixirWorkspace(confProperties);
					String reportName = elWorkspace + "/TimeDepositAdvice.rml";
					String contentType = "application/pdf";
					
					String tempDir = CommonUtils.getTempDir(confProperties);
					String uuid = UUID.randomUUID().toString();
					
					OutputStream fos;
					if( oneStep == 0 )
						fos = new FileOutputStream(tempDir+uuid);
					else
						fos = servletResponse.getOutputStream();
					if (isPrint == 1) {
						//print
						ersClient.renderReport(reportName, contentType, fos, prop);
					} else {
						//export
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ersClient.renderReport(reportName, contentType, baos, prop);
						
						ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
						PDDocument doc = PDDocument.load(bais);
						int keyLength = 128;
						
						AccessPermission permission = new AccessPermission();
						permission.setCanPrint(true);
						
						String encryptedPass = accountListService.getStatementPasswordByCifKey(cifKey);
						if( encryptedPass != null ){
							String password = rsaUtil.rsaDecrypt(encryptedPass);
							StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
							policy.setEncryptionKeyLength(keyLength);
							policy.setPermissions(permission);
							
							doc.protect(policy);
						}
						doc.save(fos);
						doc.close();
					}
					fos.close();
					response.setData(uuid);
					response.setResultCode(ResponseErrorCode.SUCCESS);
					response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				}
			}
			
			if( oneStep == 0 ){
				PrintWriter out;
				out = servletResponse.getWriter();
				out.print(mapper.writeValueAsString(response));				
			}						
		} catch (Exception e) {
			logger.error("Error", e);
			try{
				PrintWriter out = servletResponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
		}
			
	}
	
	@RequestMapping(value = "img-tda", method = RequestMethod.POST)
	public @ResponseBody Response imageTDA(@RequestParam String accountNo, @RequestParam String currency,
			@RequestParam String userId, @RequestParam String cifKey) {
		
		try {
			Response response = new Response();
			ServiceEnvelope serviceEnvelope = getServiceEnvelope(userId, cifKey, accountNo, currency);
			if( serviceEnvelope != null ){				
				if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
					TDAResponse tda = new TDAResponse(serviceEnvelope.getServiceBody().getRsDetail().getRESConsumer().getRsSubTDAccountInfo());
					//print
					String elHostname = CommonUtils.getElixirHostname(confProperties);
					int elPort = CommonUtils.getElixirPort(confProperties);
					String elUsername = CommonUtils.getElixirUsername(confProperties);
					String elPassword = CommonUtils.getElixirPassword(confProperties);
					String elResources = CommonUtils.getElixirResources(confProperties);
					ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
					
					//put parameter
					java.util.Properties prop = new java.util.Properties();
					prop.put("ACCOUNT_NO", tda.getAccountNo());
					prop.put("TDA_DATE", tda.getTdaDate());
					prop.put("TDA_NAME", tda.getTdaName());
					prop.put("TDA_LOCATION", tda.getTdaLocation());
					prop.put("TDA_ADDRESS", tda.getTdaAddress());
					prop.put("TDA_TENOR", tda.getTenor());
					prop.put("VALUTA_DATE", tda.getValutaDate());
					prop.put("MATURITY_DATE", tda.getMaturityDate());
					prop.put("CURRENCY", tda.getCurrency());
					prop.put("PRINCIPAL_AMOUNT", tda.getPrincipalAmount());
					prop.put("INTEREST_RATE", tda.getInterestAmount());
					prop.put("WORDS", tda.getWords());
					prop.put("MATURITY", tda.getMaturity());
					prop.put("INTEREST_AMOUNT", tda.getInterestAmount());
					prop.put("TAXES", tda.getTaxes());
					prop.put("NET_INTEREST", tda.getNetInterest());
					prop.put("LOGO", elResources + "/img/" + tda.getLogo());
					
					String elWorkspace = CommonUtils.getElixirWorkspace(confProperties);
					String reportName = elWorkspace + "/TimeDepositAdvice.rml";
					String tempDir = CommonUtils.getTempDir(confProperties);
					String uuid = UUID.randomUUID().toString();
					FileOutputStream fos = new FileOutputStream(tempDir+uuid);
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ersClient.renderReport(reportName, "application/pdf", baos, prop);
					
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					PDDocument doc = PDDocument.load(bais);
					PDFRenderer render = new PDFRenderer(doc);
					
					BufferedImage bim = null;
					BufferedImage result = null;
					int i = 0;
					int y = 0;
					for(i = 0; i < doc.getNumberOfPages(); i++) {
						bim = render.renderImageWithDPI(i, 200, ImageType.RGB);
						if (i == 0) {
							result = new BufferedImage(bim.getWidth(), bim.getHeight() * doc.getNumberOfPages(), BufferedImage.TYPE_INT_RGB);
						}
//						ImageIOUtil.writeImage(bim, file2 + "-" + (i) + ".png", 100);
						
						result.getGraphics().drawImage(bim, 0, y, null);
						
						y += bim.getHeight();		        
					}
					
					ImageIO.write(result, "png", fos);
					
					doc.save(fos);
					doc.close();
					fos.close();
					response.setResultCode(ResponseErrorCode.SUCCESS);
					response.setMessage(ResponseErrorCode.MSG_SUCCESS);
					response.setData(uuid);
				}else{
					response.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
					response.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
					ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope.getServiceBody().getError().getErrorDetail();
					ArrayList<String> errorCodes = new ArrayList<String>();
					for (ErrorDetail_type0 err : errorDetail_type0) {
						errorCodes.add(err.getErrorCode()+" : "+err.getErrorDescription());
					}
					response.setErrMsg(errorCodes);
				}
			}else{
				response.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				response.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
			}
			return response;	
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	private ServiceEnvelope getServiceEnvelope(String userId, String cifKey, String accountNo, String currency) throws AxisFault, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//stub
		String strContextPath = CommonUtils.getContextPath(confProperties);
		WBST_Inquiry_DetailTimedDepositStub stub = new WBST_Inquiry_DetailTimedDepositStub(strContextPath+"Services/WBST_Inquiry_DetailTimedDeposit/Processes/MainRequestResponse_HTTP");
		
		ServiceEnvelopeE serviceEnvelopeE = TimedDepositServiceEnvelopeE.instance();
		TimedDepositServiceEnvelopeE.setUserId(serviceEnvelopeE, userId);
		TimedDepositServiceEnvelopeE.setCifKey(serviceEnvelopeE, cifKey);
		TimedDepositServiceEnvelopeE.setAccountNo(serviceEnvelopeE, accountNo);
		TimedDepositServiceEnvelopeE.setAccountType(serviceEnvelopeE, "T");
		TimedDepositServiceEnvelopeE.setCurrencyType(serviceEnvelopeE, currency);
		TimedDepositServiceEnvelopeE.setOperationCode(serviceEnvelopeE, "100");
		
		ServiceEnvelopeE response = (ServiceEnvelopeE) stubUtil.request(stub, serviceEnvelopeE);
		if( response == null ){
			return null;
		}
		ServiceEnvelope serviceEnvelope = response.getServiceEnvelope();
		
		// dummy response
//		ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
//		ServiceBody serviceBody = new ServiceBody();
//		
//		//response
//		serviceEnvelope = new ServiceEnvelope();
//		
//		RsDetail_type0 rsDetail_type0 = new RsDetail_type0();
//		RESConsumer_type0 resConsumer_type0 = new RESConsumer_type0();
//		
//		RsSubTDAccountInfo_type0 subTDAccountInfo_type0 = new RsSubTDAccountInfo_type0();
//		subTDAccountInfo_type0.setResponseCode("00");
//		subTDAccountInfo_type0.setTDAdviceXML("&lt;dataaccount>&lt;waktu>1 Bulan / Month&lt;/waktu>&lt;awal>14-Apr-2014&lt;/awal>&lt;akhir>14-Aug-2016&lt;/akhir>&lt;tanggal>19-Jul-2016&lt;/tanggal>&lt;uang>IDR&lt;/uang>&lt;nominal>11,184,712.00&lt;/nominal>&lt;bunga_persen>6.00%&lt;/bunga_persen>&lt;rekening>000-820-00024-8&lt;/rekening>&lt;tempo>Diperpanjang pokok + bunga / Rollover principal + interest&lt;/tempo>&lt;jumlah>56,840.00&lt;/jumlah>&lt;pajak>11,368.00&lt;/pajak>&lt;bunga>45,472.00&lt;/bunga>&lt;bilang>SEBELAS JUTA SERATUS DELAPAN PULUH EMPAT RIBU TUJUH RATUS DUA BELAS RUPIAH/ELEVEN MILLION ONE HUNDRED EIGHTY FOUR THOUSANDS SEVEN HUNDREDS TWELVE INDONESIAN RUPIAH&lt;/bilang>&lt;nama>NAME1 810161 NAME2 810161 NAME3 810161&lt;/nama>&lt;lokasi>- NIL -                                 &lt;/lokasi>&lt;alamat>- NIL -                                 &lt;/alamat>&lt;logo>logoOCBC.jpg&lt;/logo>&lt;/dataaccount>");
//		
//		resConsumer_type0.setRsSubTDAccountInfo(subTDAccountInfo_type0);
//		rsDetail_type0.setRESConsumer(resConsumer_type0);
//		
//		serviceBody.setRsDetail(rsDetail_type0);
//		serviceEnvelope.setServiceBody(serviceBody);
		
		//end of dummy response
		
		return serviceEnvelope;
	}
}