package com.optima.oraclejdbc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optima.oraclejdbc.api.ReverseRequestApi;
import com.optima.oraclejdbc.api.ReverseRequestDetailApi;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.CCDao;
import com.optima.oraclejdbc.daoimpl.CustomerInfoDao;
import com.optima.oraclejdbc.daoimpl.DDIDao;
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
import com.optima.oraclejdbc.model.CustomerInfo;
import com.optima.oraclejdbc.model.estatement.DDIModel;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrencyHistory;
import com.optima.oraclejdbc.parser.NISPReverse;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.service.CustomerAddressService;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.ReverseUtil;
import com.optima.oraclejdbc.utils.StringUtility;

@RestController
@RequestMapping(value="/settings")
public class SettingController {
	private static final Logger logger = Logger.getLogger(SettingController.class);
	
	@Autowired
	DDIDao ddiDao;
	
	@Autowired
	CCDao ccDao;
	
	@Autowired
	NISPReverse<DDIModel> reverse;
	
	@Autowired
	NISPReverse<CCParentModel> ccReverse;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	CustomerInfoDao custDao;

	@Autowired
	CustomerAddressService customerAddressService;
	
	@RequestMapping(value = "requesthardcopy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Serializable reverseGiro(@RequestBody ReverseRequestApi data, HttpServletRequest request) {
		try {
			Response res = new Response();
			
			String reverseDir = CommonUtils.getReverseWorkDir(confProperties);
			
			//save cust info
			CustomerInfo custInfo = custDao.getUserByCIFKey(data.getCifNo());
			if (custInfo != null) {
				custInfo.setIsMonthly(data.getPerMonth());
				custDao.update(custInfo);
			}
			
			String alamat1 = "";
			String alamat2 = "";
			String alamat3 = "";
			String alamat4 = "";
			List<String> alamats = StringUtility.getAlamats(data.getAlamat());
			for (int i = 0; i < alamats.size(); i++) {
				if (i == 0) {
					alamat1 = alamats.get(i);
				} else if (i == 1) {
					alamat2 = alamats.get(i);
				} else if (i == 2) {
					alamat3 = alamats.get(i);
				} else if (i == 3) {
					alamat4 = alamats.get(i);
				}
			}
			
			alamats.removeAll(alamats);
			alamats.add(alamat1);
			alamats.add(alamat2);
			alamats.add(alamat3);
			alamats.add(alamat4);
			
			//es
			List<String> listResultGiro = new ArrayList<String>();
			List<String> listResultMcy = new ArrayList<String>();
			
			//cc
			List<String> listResultCc = new ArrayList<String>();
			
			String periode = "";
			for (ReverseRequestDetailApi item : data.getPeriods()) {
				if (item.getMonth() < 10) {
					periode = item.getYear() + "0" + item.getMonth();
				} else {
					periode = item.getYear() + item.getMonth() + "";
				}
				
				//giro
//				List<ESGiroHistory> listDataGiro = ddiDao.getRevESGiroHistory(data.getCifNo(), periode);
//				List<String> resultGiro = reverse.reverse(listDataGiro.toArray(), alamats, "");
//				listResultGiro.addAll(resultGiro);
				
				//mcy
//				List<ESMultiCurrencyHistory> listDataMcy = ddiDao.getRevESMcyHistory(data.getCifNo(), periode);
//				List<String> resultMcy = reverse.reverse(listDataMcy.toArray(), alamats, "");
//				listResultMcy.addAll(resultMcy);
				
				//cc
				Date start = null;
				Date end = null;
				
				Calendar calStart = Calendar.getInstance();
				calStart.set(Calendar.MONTH, item.getMonth() - 1);
				calStart.set(Calendar.YEAR, item.getYear());
				calStart.set(Calendar.DATE, 1);
				
				Calendar calEnd = Calendar.getInstance();
				calEnd.set(Calendar.MONTH, item.getMonth() - 1);
				calEnd.set(Calendar.YEAR, item.getYear());
				calEnd.set(Calendar.DATE, calEnd.getActualMaximum(Calendar.DATE));
				
				start = calStart.getTime();
				end = calEnd.getTime();
				
				List<CCCustomerHeader> resultCh = ccDao.getListRevCC_02_CH(data.getCifNo(), start, end);
				
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
							List<String> results = ccReverse.reverseObj(listFh.toArray(), alamats,  "");
							listResultCc.addAll(results);
						}
						
						ch = itemCh;
						if (ch != null) {
							listCh.add(ch);
							List<String> results = ccReverse.reverseObj(listCh.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						cm = ccDao.getRevCC_03_CM(chId);
						if (cm != null) {
							listCm.add(cm);
							List<String> results = ccReverse.reverseObj(listCm.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						am = ccDao.getRevCC_04_AM(chId);
						if (am != null) {
							listAm.add(am);
							List<String> results = ccReverse.reverseObj(listAm.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ah = ccDao.getRevCC_05_AH(chId);
						if (ah != null) {
							listAh.add(ah);
							List<String> results = ccReverse.reverseObj(listAh.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						listTd = ccDao.getListRevCC_06_TD(ah.getId());
						List<String> resultsTd = ccReverse.reverseObj(listTd.toArray(), alamats, "");
						listResultCc.addAll(resultsTd);
						
						dc = ccDao.getRevCC_07_DC(chId);
						if (dc != null) {
							listDc.add(dc);
							List<String> results = ccReverse.reverseObj(listDc.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ld = ccDao.getRevCC_08_LD(chId);
						if (ld != null) {
							listLd.add(ld);
							List<String> results = ccReverse.reverseObj(listLd.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ab = ccDao.getRevCC_09_AB(chId);
						if (ab != null) {
							listAb.add(ab);
							List<String> results = ccReverse.reverseObj(listAb.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						bs = ccDao.getRevCC_10_BS(chId);
						if (bs != null) {
							listBs.add(bs);
							List<String> results = ccReverse.reverseObj(listBs.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ri = ccDao.getRevCC_11_RI(chId);
						if (ri != null) {
							listRi.add(ri);
							List<String> results = ccReverse.reverseObj(listRi.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ms = ccDao.getRevCC_12_MS(chId);
						if (ms != null) {
							listMs.add(ms);
							List<String> results = ccReverse.reverseObj(listMs.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						mt = ccDao.getRevCC_13_MT(chId);
						if (mt != null) {
							listMt.add(mt);
							List<String> results = ccReverse.reverseObj(listMt.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						mf = ccDao.getRevCC_14_MF(chId);
						if (mf != null) {
							listMf.add(mf);
							List<String> results = ccReverse.reverseObj(listMf.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						at = ccDao.getRevCC_15_AT(chId);
						if (at != null) {
							listAt.add(at);
							List<String> results = ccReverse.reverseObj(listAt.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ct = ccDao.getRevCC_16_CT(chId);
						if (ct != null) {
							listCt.add(ct);
							List<String> results = ccReverse.reverseObj(listCt.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
						
						ft = ccDao.getRevCC_17_FT(chId);
						if (ft != null) {
							listFt.add(ft);
							List<String> results = ccReverse.reverseObj(listFt.toArray(), alamats, "");
							listResultCc.addAll(results);
						}
					}
				}
			}
			
			//write
			ReverseUtil.writeToFileES(reverseDir, listResultGiro, 1, periode);
			ReverseUtil.writeToFileES(reverseDir, listResultMcy, 2, periode);
			ReverseUtil.writeToFileCC(reverseDir, listResultCc, periode);
			
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		} catch (Exception e) {
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
	}
	
	@RequestMapping(value = "savedefaultaddr", method = RequestMethod.POST)
	public Response saveDefaultAddress(@RequestParam String cifKey, @RequestParam String type
			, @RequestParam(defaultValue="0") String seqNum, @RequestParam(defaultValue="") String address){
		
		try{
			Response res = new Response();
			
			if( type.equals("1") ){
				customerAddressService.setCustomerDefaultAddress(cifKey, seqNum);
			}else{
				customerAddressService.setCustomerCustomAddress(cifKey, address);
			}
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch(Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);	
		}
	}
}
