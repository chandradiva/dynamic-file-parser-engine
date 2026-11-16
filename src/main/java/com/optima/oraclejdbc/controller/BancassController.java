package com.optima.oraclejdbc.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.elixirtech.ers2.client.ERSClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailinsurance.REQConsumer_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailinsurance.RqDetail_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.NISPBancAssInfo_type0;
import com.ocbc.schemas.soa.emf.common.body.ErrorDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.ClientControl_type0;
import com.ocbc.schemas.soa.emf.common.header.ClientDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.CommonDetailChoice_type2;
import com.ocbc.schemas.soa.emf.common.header.CommonDetailChoice_type3;
import com.ocbc.schemas.soa.emf.common.header.CommonDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RecordControlChoice_type0;
import com.ocbc.schemas.soa.emf.common.header.RecordControl_type0;
import com.ocbc.schemas.soa.emf.common.header.RegionDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RemarkDetail_type0;
import com.ocbc.schemas.soa.emf.common.header.RqNature_type0;
import com.ocbc.schemas.soa.emf.common.header.UserDetail_type0;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinsurance.ServiceBody;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinsurance.ServiceEnvelope;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinsurance.ServiceEnvelopeE;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinsurance.ServiceHeader;
import com.ocbc.schemas.soa.emf.service.wbst_inquiry_detailinsurance.ServiceHeaderChoice_type1;
import com.ocbc.schemas.soa.wsdl.service.wbst_inquiry_detailinsurance.operationimpl.services.wbst__inquiry__detailinsurance.processes.WBST_Inquiry_DetailInsuranceStub;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.elixirprop.BancassProp;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.requestinput.ByDateHistoryInput;
import com.optima.oraclejdbc.requestinput.ByRecentTransactionsInput;
import com.optima.oraclejdbc.requestinput.bancass.PernyataanTransaksiInput;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.response.bancass.BancassResponse;
import com.optima.oraclejdbc.response.bancass.DetailBiayaResponse;
import com.optima.oraclejdbc.response.bancass.DetailPremiResponse;
import com.optima.oraclejdbc.response.bancass.DisclaimerResponse;
import com.optima.oraclejdbc.response.bancass.PernyataanTransaksiResponse;
import com.optima.oraclejdbc.response.bancass.TransactionSummaryResponse;
import com.optima.oraclejdbc.response.bancass.TransaksiLainResponse;
import com.optima.oraclejdbc.service.AccountListRegisterService;
import com.optima.oraclejdbc.service.BancassService;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value = "/bancass")
public class BancassController {

	private static final Logger logger = Logger.getLogger(BancassController.class);
			
	@Autowired
	BancassService service;

	@Autowired
	StubUtil stubUtil;

	@Resource(name = "confProp")
	private java.util.Properties confProperties;

	@Autowired
	SystemParameterDao sysParamDao;

	@Autowired
	AccountListRegisterService accountListService;

	@Autowired
	RsaUtil rsaUtil;

	@RequestMapping(value = "/print-pernyataantransaksi", method = RequestMethod.POST)
	public void printPernyataanTransaksi(
			@Valid @ModelAttribute PernyataanTransaksiInput input,
			BindingResult bindingResult, @RequestParam int isPrint, @RequestParam(defaultValue="0") int oneStep,
			HttpServletResponse servletResponse) {
		try{
			Response response = new Response();
			Hashtable<String, String> errMsg = new Hashtable<String, String>();

			ElixirModel model = CommonUtils.getElixirModel(confProperties);

			String strAccountNo = null;
			String strCustNo = null;
			String strPolicyNo = input.getPolicyNo();

			if (strPolicyNo == null) {
				strAccountNo = input.getAccountNumber();
			}

			if (strAccountNo == null && strCustNo == null && strPolicyNo == null) {
				errMsg.put("customerNumber/accountNumber/policyNumber",
						"one of customer number or account number must be filled");
			}

			PernyataanTransaksiResponse pernyataan = new PernyataanTransaksiResponse();

			TransactionSummaryResponse sh = service
					.getTransactionSummaryByPolicyNo(strPolicyNo);
			pernyataan.setSh(sh);

			List<DetailPremiResponse> listDp = service
					.getListDetailPremiByPolicyNo(sh.getId());
			pernyataan.setListDp(listDp);

			List<DetailBiayaResponse> listDb = service
					.getListDetailBiayaByPolicyNo(sh.getId());
			pernyataan.setListDb(listDb);

			List<TransaksiLainResponse> listTl = service
					.getListTransaksiLainByPolicyNo(sh.getId());
			pernyataan.setListTl(listTl);

			List<DisclaimerResponse> listDm = service
					.getListDisclaimerByPolicyNo(sh.getId());
			pernyataan.setListDm(listDm);

			// print
			String elHostname = model.getHostname();
			int elPort = model.getPort();
			String elUsername = model.getUsername();
			String elPassword = model.getPassword();
			ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);

			Date tglCetak = new Date();

			// put parameter
			java.util.Properties prop = BancassProp.pernyataanTransaksi(sh, tglCetak, model.getProviderUrl());

			String elWorkspace = model.getWorkspace();
			String reportName = elWorkspace + "/PernyataanTransaksi.rml";
			String contentType = "application/pdf";

			String tempDir = CommonUtils.getTempDir(confProperties);
			String uuid = UUID.randomUUID().toString();

			OutputStream fos = null;
			// outServlet = response.getOutputStream();

			if( oneStep == 0 )
				fos = new FileOutputStream(tempDir + uuid);
			else
				fos = servletResponse.getOutputStream();
			if (isPrint == 1) {
				// print
				ersClient.renderReport(reportName, contentType, fos, prop);
			} else {
				// export
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ersClient.renderReport(reportName, contentType, baos, prop);

				ByteArrayInputStream bais = new ByteArrayInputStream(
						baos.toByteArray());
				PDDocument doc = PDDocument.load(bais);
				int keyLength = 128;

				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);

				String encryptedPass = accountListService
						.getStatementPasswordByCifKey(input.getCifKey());
				if (encryptedPass != null) {
					String password = rsaUtil.rsaDecrypt(encryptedPass);
					StandardProtectionPolicy policy = new StandardProtectionPolicy(
							password, password, permission);
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
			if( oneStep == 0 ){
				try {
					PrintWriter out = servletResponse.getWriter();
					ObjectMapper mapper = new ObjectMapper();
					out.print(mapper.writeValueAsString(response));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch( Exception e){
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

	@RequestMapping(value = "/pernyataantransaksi", method = RequestMethod.POST)
	public Response pernyataanTransaksi(
			@Valid @ModelAttribute PernyataanTransaksiInput input,
			BindingResult bindingResult) {
		try{
			Response response = new Response();

			List<String> errMsg = new ArrayList<String>();

			if (!CommonUtils.validateInput(bindingResult, errMsg)) {
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
			} else {
				String strAccountNo = null;
				String strCustNo = null;
				String strPolicyNo = input.getPolicyNo();

				if (strPolicyNo == null) {
					strAccountNo = input.getAccountNumber();
				}

				if (strAccountNo == null && strCustNo == null
						&& strPolicyNo == null) {
					errMsg.add("customerNumber/accountNumber/policyNumber: one of customer number or account number must be filled");
				}

				PernyataanTransaksiResponse pernyataan = new PernyataanTransaksiResponse();

				TransactionSummaryResponse sh = service
						.getTransactionSummaryByPolicyNo(strPolicyNo);
				pernyataan.setSh(sh);

				List<DetailPremiResponse> listDp = service
						.getListDetailPremiByPolicyNo(sh.getId());
				pernyataan.setListDp(listDp);

				List<DetailBiayaResponse> listDb = service
						.getListDetailBiayaByPolicyNo(sh.getId());
				pernyataan.setListDb(listDb);

				List<TransaksiLainResponse> listTl = service
						.getListTransaksiLainByPolicyNo(sh.getId());
				pernyataan.setListTl(listTl);

				List<DisclaimerResponse> listDm = service
						.getListDisclaimerByPolicyNo(sh.getId());
				pernyataan.setListDm(listDm);

				response.setData(pernyataan);
				response.setResultCode(ResponseErrorCode.SUCCESS);
				response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			}

			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}

	@RequestMapping(value = "/ministatement", method = RequestMethod.GET)
	public Response getMiniStatement(@RequestParam String spajNo) {
		try{
			Response response = new Response();
			response.setData(service.getMiniStatement(spajNo));
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}

	@RequestMapping(value = "getsummary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSummary(@RequestParam String userId,
			@RequestParam String cifKey) {
		
		try {
			Response res = new Response();
			// stub
			String strContextPath = CommonUtils.getContextPath(confProperties);

			WBST_Inquiry_DetailInsuranceStub stub = new WBST_Inquiry_DetailInsuranceStub(
					strContextPath
							+ "Services/WBST_Inquiry_DetailInsurance/Processes/MainRequestResponse_HTTP");
			// WBST_Inquiry_DetailInsuranceStub stub = new
			// WBST_Inquiry_DetailInsuranceStub(strContextPath+"services/DetailInsurance_MainRequestResponse__HTTP");
			ServiceEnvelopeE serviceEnvelopeE = new ServiceEnvelopeE();
			ServiceEnvelope serviceEnvelope = new ServiceEnvelope();
			ServiceBody serviceBody = new ServiceBody();

			// request
			ServiceHeader serviceHeader = new ServiceHeader();
			CommonDetail_type0 commonDetail_type0 = new CommonDetail_type0();

			commonDetail_type0.setServiceName("WBST_Inquiry_DetailInsurance");
			commonDetail_type0.setOperationCode("000");
			commonDetail_type0.setEMFVersion("000");
			commonDetail_type0.setServiceVersion("1.0");
			commonDetail_type0.setOperationVersion("000");
			commonDetail_type0.setRqUuid(UUID.randomUUID().toString());
			commonDetail_type0.setRqExpiryTime(new BigInteger("99"));
			CommonDetailChoice_type2 commonDetailChoice_type2 = new CommonDetailChoice_type2();
			commonDetailChoice_type2.setRqDateTime(Calendar.getInstance());
			commonDetail_type0
					.setCommonDetailChoice_type2(commonDetailChoice_type2);

			RecordControl_type0 recordControl_type0 = new RecordControl_type0();
			RecordControlChoice_type0 recordControlChoice_type0 = new RecordControlChoice_type0();
			ClientControl_type0 clientControl_type0 = new ClientControl_type0();
			clientControl_type0.setRqNoOfRecords(new BigInteger("0"));
			clientControl_type0.setCursor("1");

			recordControlChoice_type0.setClientControl(clientControl_type0);
			recordControl_type0
					.setRecordControlChoice_type0(recordControlChoice_type0);
			commonDetail_type0.setRecordControl(recordControl_type0);

			CommonDetailChoice_type3 commonDetailChoice_type3 = new CommonDetailChoice_type3();
			RqNature_type0 rqNature_type0 = new RqNature_type0();
			rqNature_type0.setTransactionCode("1");
			rqNature_type0.setTransactionRefNo(new BigInteger("1"));
			rqNature_type0.setTransactionMode("000");
			rqNature_type0.setRqStatus("000");
			commonDetailChoice_type3.setRqNature(rqNature_type0);

			commonDetail_type0
					.setCommonDetailChoice_type3(commonDetailChoice_type3);

			RemarkDetail_type0 remarkDetail_type0 = new RemarkDetail_type0();
			remarkDetail_type0.setRemarkType("1");
			remarkDetail_type0.setRemarkCode("2");
			remarkDetail_type0.setRemarkStatus("1");
			remarkDetail_type0.setRemarkDescription("Description");
			remarkDetail_type0.setRemarkActor("herman");
			commonDetail_type0.setRemarkDetail(remarkDetail_type0);

			serviceHeader.setCommonDetail(commonDetail_type0);

			ServiceHeaderChoice_type1 serviceHeaderChoice_type1 = new ServiceHeaderChoice_type1();
			ClientDetail_type0 clientDetail_type0 = new ClientDetail_type0();
			clientDetail_type0.setID("NISP");
			clientDetail_type0.setOrganization("NISP");
			clientDetail_type0.setSubsystem("NISP");
			RegionDetail_type0 regionDetail_type0 = new RegionDetail_type0();
			regionDetail_type0.setCountryCode("ID");
			regionDetail_type0.setRegionCode("BDG");
			clientDetail_type0.setRegionDetail(regionDetail_type0);
			clientDetail_type0.setChannel("CONS"); // tergantung consumer atau
													// coorporate
			UserDetail_type0 userDetail_type0 = new UserDetail_type0();
			userDetail_type0.setUserId("77777");
			userDetail_type0.setUserRole("");
			userDetail_type0.setUserPassword("");
			clientDetail_type0.setUserDetail(userDetail_type0);
			serviceHeaderChoice_type1.setClientDetail(clientDetail_type0);
			serviceHeader
					.setServiceHeaderChoice_type1(serviceHeaderChoice_type1);
			serviceHeader.setCommonDetail(commonDetail_type0);

			RqDetail_type0 rqDetail_type0 = new RqDetail_type0();
			REQConsumer_type0 reqConsumer_type0 = new REQConsumer_type0();
			;

			reqConsumer_type0.setLoginID(userId);
			reqConsumer_type0.setFUID(userId);
			reqConsumer_type0.setCIFKey(cifKey);

			rqDetail_type0.setREQConsumer(reqConsumer_type0);
			serviceBody.setRqDetail(rqDetail_type0);
			serviceEnvelope.setServiceHeader(serviceHeader);
			serviceEnvelope.setServiceBody(serviceBody);

			serviceEnvelopeE.setServiceEnvelope(serviceEnvelope);
			ServiceEnvelopeE response = (ServiceEnvelopeE) stubUtil.request(
					stub, serviceEnvelopeE);
			if (response == null) {
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				List<String> errMsg = new ArrayList<String>();
				errMsg.add(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				res.setErrMsg(errMsg);
				return res;
			}

			// response
			serviceEnvelope = response.getServiceEnvelope();

			if (serviceEnvelope.getServiceBody().getRsDetail() != null) {
				List<BancassResponse> listData = new ArrayList<BancassResponse>();
				if (serviceEnvelope.getServiceBody().getRsDetail()
						.getRESConsumer().isNISPBancAssInfoSpecified())
					for (NISPBancAssInfo_type0 item : serviceEnvelope
							.getServiceBody().getRsDetail().getRESConsumer()
							.getNISPBancAssInfo()) {
						BancassResponse bancassResponse = new BancassResponse(
								item);
						bancassResponse.setProduct(service.getProductName(item
								.getProductCode()));
						listData.add(bancassResponse);
					}
				res.setResultCode(ResponseErrorCode.SUCCESS);
				res.setMessage(ResponseErrorCode.MSG_SUCCESS);
				res.setData(listData);
			} else {
				res.setResultCode(ResponseErrorCode.TIBCO_CONNECTION_FAILED);
				res.setMessage(ResponseErrorCode.MSG_TIBCO_CONNECTION_FAILED);
				ErrorDetail_type0 errorDetail_type0[] = serviceEnvelope
						.getServiceBody().getError().getErrorDetail();
				ArrayList<String> errorCodes = new ArrayList<String>();
				for (ErrorDetail_type0 err : errorDetail_type0) {
					errorCodes.add(err.getErrorCode() + " : "
							+ err.getErrorDescription());
				}
				res.setErrMsg(errorCodes);
			}
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}

	@RequestMapping(value = "/history", method = RequestMethod.POST)
	public Response getTransactionHistoryStatement(
			@Valid @ModelAttribute ByDateHistoryInput input,
			BindingResult bindingResult) {
		try{
			Response response = new Response();

			List<String> errMsg = new ArrayList<String>();
			if (!CommonUtils.validateInput(bindingResult, errMsg)) {
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				return response;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = null;
				Date endDate = null;
				try {
					startDate = sdf.parse(input.getStart());
				} catch (ParseException e) {
					errMsg.add("start: invalid date format, should be in dd/MM/yyyy format");
				}

				try {
					endDate = sdf.parse(input.getEnd());
				} catch (ParseException e) {
					errMsg.add("end: invalid date format, should be in dd/MM/yyyy format");
				}

				String strPage = input.getPage();
				if (strPage != null)
					CommonUtils.checkPositiveInteger("page", strPage, errMsg);

				String strCountPerPage = input.getCountPerPage();
				if (strCountPerPage != null)
					CommonUtils.checkPositiveInteger("countPerPage",
							strCountPerPage, errMsg);

				String sortBy = input.getSortBy();
				if (sortBy != null) {
					sortBy = sortBy.toLowerCase();
					if (!sortBy.equals("transaction date")
							&& !sortBy.equals("description")
							&& !sortBy.equals("amount") && !sortBy.equals("status")) {
						errMsg.add("sortBy: invalid value, valid value are \"transaction date\", "
								+ "\"description\", \"amount\" or \"status\"");
					}
				} else
					sortBy = "default";

				String sortType = input.getSortType();
				if (sortType != null) {
					sortType = sortType.toUpperCase();
					if (!sortType.equals("ASC") && !sortType.equals("DESC")) {
						errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
					}
				} else
					sortType = "ASC";

				if (errMsg.size() > 0) {
					response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
					response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
					response.setErrMsg(errMsg);
					return response;
				}

				Integer page = null;
				if (strPage != null)
					page = Integer.parseInt(input.getPage());

				Integer countPerPage = null;
				if (strCountPerPage != null)
					countPerPage = Integer.parseInt(strCountPerPage);

				String batchId = input.getAccountNumber();
				response.setData(service.getByDateStatement(batchId, startDate,
						endDate, page, countPerPage, sortBy, sortType));
				response.setTotalAllData(service.getTotalCountByDate(batchId,
						startDate, endDate));
				response.setResultCode(ResponseErrorCode.SUCCESS);
				response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				return response;
			}
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
	}

	@RequestMapping(value = "/recent", method = RequestMethod.POST)
	public Response getTransactionHistoryStatement(
			@Valid @ModelAttribute ByRecentTransactionsInput input,
			BindingResult bindingResult) {
		try{
			Response response = new Response();

			List<String> errMsg = new ArrayList<String>();
			if (!CommonUtils.validateInput(bindingResult, errMsg)) {
				response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
				response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
				response.setErrMsg(errMsg);
				return response;
			} else {
				String strCount = input.getCountLastTransactions();
				CommonUtils.checkPositiveInteger("countLastTransactions", strCount,
						errMsg);

				String strPage = input.getPage();
				if (strPage != null)
					CommonUtils.checkPositiveInteger("page", strPage, errMsg);

				String strCountPerPage = input.getCountPerPage();
				if (strCountPerPage != null)
					CommonUtils.checkPositiveInteger("countPerPage",
							strCountPerPage, errMsg);

				String sortBy = input.getSortBy();
				if (sortBy != null) {
					sortBy = sortBy.toLowerCase();
					if (!sortBy.equals("transaction date")
							&& !sortBy.equals("description")
							&& !sortBy.equals("amount") && !sortBy.equals("status")) {
						errMsg.add("sortBy: invalid value, valid value are \"transaction date\", "
								+ "\"description\", \"amount\" or \"status\"");
					}
				} else
					sortBy = "default";

				String sortType = input.getSortType();
				if (sortType != null) {
					sortType = sortType.toUpperCase();
					if (!sortType.equals("ASC") && !sortType.equals("DESC")) {
						errMsg.add("sortType: invalid value, valid value are \"ASC\" or \"DESC\"");
					}
				} else
					sortType = "ASC";

				if (errMsg.size() > 0) {
					response.setResultCode(ResponseErrorCode.INVALID_REQUEST_INPUT);
					response.setMessage(ResponseErrorCode.MSG_INVALID_REQUEST_INPUT);
					response.setErrMsg(errMsg);
					return response;
				}

				Integer page = null;
				if (strPage != null)
					page = Integer.parseInt(input.getPage());

				Integer countPerPage = null;
				if (strCountPerPage != null)
					countPerPage = Integer.parseInt(strCountPerPage);

				String batchId = input.getAccountNumber();
				int count = Integer.parseInt(strCount);
				response.setData(service.getByRecentTransactionsStatement(batchId,
						count, page, countPerPage, sortBy, sortType));
				response.setTotalAllData(service.getTotalCountByRecent(batchId,
						count));
				response.setResultCode(ResponseErrorCode.SUCCESS);
				response.setMessage(ResponseErrorCode.MSG_SUCCESS);
				return response;
			}
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}

	@RequestMapping(value = "/img-pernyataantransaksi", method = RequestMethod.POST)
	public @ResponseBody void imagePernyataanTransaksi(
			@Valid @ModelAttribute PernyataanTransaksiInput input,
			BindingResult bindingResult, HttpServletResponse httpresponse) {
		try{
			Hashtable<String, String> errMsg = new Hashtable<String, String>();

			ElixirModel model = CommonUtils.getElixirModel(confProperties);

			String strAccountNo = null;
			String strCustNo = null;
			String strPolicyNo = input.getPolicyNo();

			if (strPolicyNo == null) {
				strAccountNo = input.getAccountNumber();
			}

			if (strAccountNo == null && strCustNo == null && strPolicyNo == null) {
				errMsg.put("customerNumber/accountNumber/policyNumber",
						"one of customer number or account number must be filled");
			}

			PernyataanTransaksiResponse pernyataan = new PernyataanTransaksiResponse();

			TransactionSummaryResponse sh = service
					.getTransactionSummaryByPolicyNo(strPolicyNo);
			pernyataan.setSh(sh);

			List<DetailPremiResponse> listDp = service
					.getListDetailPremiByPolicyNo(sh.getId());
			pernyataan.setListDp(listDp);

			List<DetailBiayaResponse> listDb = service
					.getListDetailBiayaByPolicyNo(sh.getId());
			pernyataan.setListDb(listDb);

			List<TransaksiLainResponse> listTl = service
					.getListTransaksiLainByPolicyNo(sh.getId());
			pernyataan.setListTl(listTl);

			List<DisclaimerResponse> listDm = service
					.getListDisclaimerByPolicyNo(sh.getId());
			pernyataan.setListDm(listDm);

			// print
			String elHostname = model.getHostname();
			int elPort = model.getPort();
			String elUsername = model.getUsername();
			String elPassword = model.getPassword();
			ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);

			Date tglCetak = new Date();

			// put parameter
			java.util.Properties prop = BancassProp.pernyataanTransaksi(sh, tglCetak, model.getProviderUrl());

			String elWorkspace = model.getWorkspace();
			String reportName = elWorkspace + "/PernyataanTransaksi.rml";
			String contentType = "image/png";

			ServletOutputStream outServlet = null;

			outServlet = httpresponse.getOutputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ersClient.renderReport(reportName, "application/pdf", baos, prop);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());

			PDDocument doc = PDDocument.load(bais);
			PDFRenderer render = new PDFRenderer(doc);

			BufferedImage bim = null;
			BufferedImage result = null;
			int i = 0;
			int y = 0;
			for (i = 0; i < doc.getNumberOfPages(); i++) {
				bim = render.renderImageWithDPI(i, 200, ImageType.RGB);
				if (i == 0) {
					result = new BufferedImage(bim.getWidth(), bim.getHeight()
							* doc.getNumberOfPages(),
							BufferedImage.TYPE_INT_RGB);
				}
				// ImageIOUtil.writeImage(bim, file2 + "-" + (i) + ".png", 100);

				result.getGraphics().drawImage(bim, 0, y, null);

				y += bim.getHeight();
			}

			ImageIO.write(result, "png", outServlet);

			doc.save(outServlet);
			doc.close();

			httpresponse.setContentType(contentType);
		}catch( Exception e){
			logger.error("Error", e);
			try{
				ObjectMapper mapper = new ObjectMapper();
				PrintWriter out = httpresponse.getWriter();				
				out.print(mapper.writeValueAsString(CommonUtils.responseException(e)));
			}catch( Exception e1){
				logger.error("Error", e1);
			}
		}		
	}

	@RequestMapping(value = "/exists", method = RequestMethod.POST)
	public Response isExists(@RequestParam String policyNo) {
		try{
			Response response = new Response();
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
			response.setData(service.isExists(policyNo));
			return response;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
	}
}
