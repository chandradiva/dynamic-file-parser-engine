package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailtimeddeposit.TDDetailInfo_type0;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.response.dailystatement.TimedDepositResponse;

@Service
public class TimedDepositService {

	@Autowired
	MappingProductDao mappingProductDao;
	
	@Autowired
	AccountListDao accountListDao;
	
	public List<TimedDepositResponse> getSummary(TDDetailInfo_type0[] tdDetailInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		ArrayList<TimedDepositResponse> res = new ArrayList<TimedDepositResponse>();
		if( tdDetailInfo != null ){
			for (TDDetailInfo_type0 detail : tdDetailInfo) {
				TimedDepositResponse response = new TimedDepositResponse(detail);
				AccountList accountList = accountListDao.getAccountListByAccountNumber(response.getAccountNo(), response.getCurrency());
				if( accountList == null ){
					continue;
				}
				MappingProduct product = mappingProductDao.getProductById(accountList.getProductId());
				if( product == null || product.getId() == -1L){
					product = new MappingProduct();
					product.setId(-1L);
					product.setProductCategory(104);
					product.setProductCode(accountList.getProduct().getProductCode());
					product.setProductName("TBD - "+accountList.getProduct().getProductCode());
				}
				response.setProduct(product);
				res.add(response);
			}			
		}
		return res;
	}

}
