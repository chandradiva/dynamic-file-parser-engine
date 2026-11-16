package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.AccountsInfo_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.CardsInfo_type0;
import com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RESConsumer_type0;
import com.ocbc.schemas.soa.emf.account.aggregates.AlternateAccountKeyDetail_type0;
import com.ocbc.schemas.soa.emf.creditcard.aggregates.CreditCardDetail_type0;
import com.ocbc.schemas.soa.emf.creditcard.aggregates.CreditCardInfo_type0;
import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.CreditCardInfoDao;
import com.optima.oraclejdbc.daoimpl.CustomerInfoDao;
import com.optima.oraclejdbc.daoimpl.DDIDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.CreditCardInfo;
import com.optima.oraclejdbc.model.CustomerInfo;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.model.UserInfo;
import com.optima.oraclejdbc.model.estatement.ESAccountInfo;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.tibco.customerinfo.CustomerInfoRequest;
import com.optima.oraclejdbc.utils.CommonUtils;
import com.optima.oraclejdbc.utils.RsaUtil;

@Service
public class AccountListRegisterService {

	@Autowired
	CustomerInfoDao customerInfoDao;
	
	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	MappingProductDao mappingProductDao;
	
	@Autowired
	CreditCardInfoDao creditCardInfoDao;
	
	@Autowired
	DDIDao ddiDao;
	
	@Autowired
	CustomerInfoRequest customerInfoRequest;
	
	@Autowired
	RsaUtil rsaUtil;
	
	private Long registerCustomerInfo(String userId, String cifKey) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		boolean isUpdate = true;
		CustomerInfo customerInfo = customerInfoDao.getUserByCIFKey(cifKey);
		if( customerInfo == null ){
			customerInfo = new CustomerInfo();
			customerInfo.setCifKey(cifKey);
			customerInfo.setUserId(userId);
			isUpdate=false;
		}
		
		ESAccountInfo esai = ddiDao.getEsAccountInfoByCif(cifKey);
		if( esai != null && esai.getDob() != null ){
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
			customerInfo.setStatementPassword(rsaUtil.rsaEncrypt(sdf.format(esai.getDob())));
		}
		
		if( isUpdate ){
			String eUserId = customerInfo.getUserId();
			if( eUserId == null || !eUserId.equalsIgnoreCase(userId) ){
				customerInfo.setUserId(userId);				
			}
			customerInfoDao.update(customerInfo);
		}else{
			customerInfoDao.create(customerInfo);
		}
		
		return customerInfo.getId();
	}

	public void registerESAccountInfo(com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RESConsumer_type0 resConsumer) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		String cifKey = resConsumer.getCIFKey();
		
		boolean isUpdate = true;
		ESAccountInfo esai = ddiDao.getEsAccountInfoByCif(CommonUtils.normalizeCifKey(cifKey));
		if( esai == null ){
			esai = new ESAccountInfo();				
			esai.setCifNo(CommonUtils.normalizeCifKey(cifKey));
			isUpdate = false;
		}
		if( esai.getDob() == null ){
			Response res = customerInfoRequest.getUserInfo(cifKey);			
			if( res.getResultCode() == ResponseErrorCode.SUCCESS ){
				UserInfo userInfo = (UserInfo)res.getData();
				esai.setDob(userInfo.getDob());
			}
			if( isUpdate )
				ddiDao.update(esai);
			else
				ddiDao.create(esai);
		}
	}
	
	public List<AccountList> accountListRegister(String userId,
			com.ocbc.schemas.soa.accounts.service.wbst_admin_loginchannelbanking.RESConsumer_type0 resConsumer) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Long customerInfoId = registerCustomerInfo(userId, CommonUtils.normalizeCifKey(resConsumer.getCIFKey()));
		if( customerInfoId != null )
			accountListDao.removeFromAccountList(customerInfoId);
		ArrayList<AccountList> res = new ArrayList<AccountList>();
		if( resConsumer.isAccountsListSpecified() ){
			AccountsInfo_type0 accountsList[] = resConsumer.getAccountsList().getAccountsInfo();			
			for(int i=0; i<accountsList.length;i++){
				if( !accountsList[i].getStatus().equals("02") ){
					AlternateAccountKeyDetail_type0 accountKeyDetailList[] = accountsList[i].getCustomerAccountsInfo().getAlternateAccountKeyDetail();
					for(int j=0;j<accountKeyDetailList.length;j++){
						AccountList accountListItem = new AccountList();
						if(accountKeyDetailList[j].getAccountNo1() == null )
							continue;
						String accNo = accountKeyDetailList[j].getAccountNo1();
						accountListItem.setAccountNumber(CommonUtils.normalizeAccNo(accNo));
						accountListItem.setAccountType(accountKeyDetailList[j].getAccountType().charAt(0));
						accountListItem.setCurrency(accountKeyDetailList[j].getCurrencyType());
						accountListItem.setCustomerInfoId(customerInfoId);
						accountListItem.setBranchCode(accountKeyDetailList[j].getBranchCode());
						accountListItem.setMcBit(accountKeyDetailList[j].getMCBit()?1:0);
						MappingProduct product = mappingProductDao.getProductByCode(accountsList[i].getProductCode());
						if( product == null ){
							product = new MappingProduct();
							product.setId(-1L);
							product.setProductType(101);
							product.setProductCategory(1);
							product.setProductGroup(1);
							product.setProductCode(accountsList[i].getProductCode());
							product.setProductName("TBD - "+product.getProductCode());							
						}
						accountListItem.setProduct(product);
						accountListItem.setProductId(product.getId());
						accountListDao.insertOrUpdate(accountListItem);		
						accountListItem.setAccountNumber(accNo);
						res.add(accountListItem);
					}
				}else{
					String accountNumber = CommonUtils.normalizeAccNo(accountsList[i].getCustomerAccountsInfo().getAlternateAccountKeyDetail()[0]
							.getAccountNo1());
					String currency = accountsList[i].getCustomerAccountsInfo().getAlternateAccountKeyDetail()[0]
							.getCurrencyType();
					accountListDao.removeFromAccountList(accountNumber, currency);
					System.out.println("Account number "+accountNumber+" is closed");
				}
			}			
		}
		return res;
	}

	public List<CreditCardInfo> creditCardInfoRegister(String userId,
			RESConsumer_type0 resConsumer) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		String cifKey = resConsumer.getCIFKey();
		Long customerInfoId = registerCustomerInfo(userId, cifKey);
		ArrayList<CreditCardInfo> res = new ArrayList<CreditCardInfo>();
		if( resConsumer.isAccountsListSpecified() ){
			CardsInfo_type0 cardInfoList[] = resConsumer.getAccountsList().getCardsInfo();
			if( cardInfoList != null)
				for(int i=0; i<cardInfoList.length;i++){
					CreditCardInfo_type0 creditCardInfo = cardInfoList[i].getCreditCardInfo();
					CreditCardDetail_type0 aCreditCardDetail_type0[] = creditCardInfo.getCreditCardDetail();
					if( aCreditCardDetail_type0 != null ){
						for (CreditCardDetail_type0 creditCardDetail : creditCardInfo.getCreditCardDetail()) {
							CreditCardInfo item = new CreditCardInfo(customerInfoId, creditCardInfo.getCreditCardAccountNumber(), creditCardDetail);
							MappingProduct product = mappingProductDao.getProductByCode(creditCardDetail.getProductCode());
							if( product == null ){
								product = new MappingProduct();
								product.setProductName(creditCardDetail.getProductCode()+" (TBD)");					
							}else
								item.setProductId(product.getId());
							item.setProduct(product);
							creditCardInfoDao.insertOrUpdate(item);
							res.add(item);
						}
					}
													
				}
		}
		return res;
	}
	
	public String getStatementPasswordByCifKey(String cifKey) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String password = accountListDao.getStatementPasswordByCifKey(cifKey);
		if( password == null ){
			Response res = customerInfoRequest.getUserInfo(cifKey);			
			if( res.getResultCode() == ResponseErrorCode.SUCCESS ){
				UserInfo userInfo = (UserInfo)res.getData();
				ESAccountInfo esai = new ESAccountInfo();				
				esai.setCifNo(cifKey);
				esai.setDob(userInfo.getDob());
				ddiDao.create(esai);
				
				CustomerInfo ci = new CustomerInfo();
				ci.setCifKey(cifKey);
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
				password = rsaUtil.rsaEncrypt(sdf.format(userInfo.getDob()));
				ci.setStatementPassword(password);				
			}
		}
		
		return password;
	}

	public AccountList getAccountListByAccountNumber(String accountNumber, String currency) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return accountListDao.getAccountListByAccountNumber(accountNumber, currency);
	}

	public AccountList getAccountListByAccountNumber(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return accountListDao.getAccountListByAccountNumber(accountNumber);
	}

	public List<AccountList> getAllAccountListByAccountNumber(String accountNumber) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return accountListDao.getAllAccountListByAccountNumber(accountNumber);
	}

	public String getStatementPassword(String accountNumber, int type) {
		return accountListDao.getStatementPassword(accountNumber, type);		
	}
	
	public CustomerInfo getCustInfoByCif(String cif) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		return customerInfoDao.getUserByCIFKey(cif);
	}
}
