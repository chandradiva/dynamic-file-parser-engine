package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailloan.LoanDetailInfo_type0;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.response.loan.LoanResponse;

@Component
public class LoanService {
	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	MappingProductDao mappingProductDao;

	public List<LoanResponse> getSummary(LoanDetailInfo_type0[] loanDetailInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		ArrayList<LoanResponse> res = new ArrayList<LoanResponse>();
		for (LoanDetailInfo_type0 info : loanDetailInfo) {
			LoanResponse response = new LoanResponse(info);
			AccountList accountList = accountListDao.getAccountListByAccountNumber(info.getAccountNo1(), info.getCurrencyType());
			if( accountList != null ){
				MappingProduct product = mappingProductDao.getProductById(accountList.getProductId());
				response.setProduct(product);
				res.add(response);
			}			
		}
		return res;
	}
	
	
}
