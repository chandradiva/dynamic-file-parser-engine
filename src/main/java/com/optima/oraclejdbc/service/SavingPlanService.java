package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocbc.schemas.soa.accounts.service.wbst_inquiry_detailsavingsplan.SPDetailInfo_type0;
import com.optima.oraclejdbc.daoimpl.AccountListDao;
import com.optima.oraclejdbc.daoimpl.MappingProductDao;
import com.optima.oraclejdbc.model.AccountList;
import com.optima.oraclejdbc.model.MappingProduct;
import com.optima.oraclejdbc.response.dailystatement.SavingPlanResponse;

@Service
public class SavingPlanService {

	@Autowired
	AccountListDao accountListDao;
	
	@Autowired
	MappingProductDao mappingProductDao;
	
	public List<SavingPlanResponse> getSummary(SPDetailInfo_type0[] spDetailInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		ArrayList<SavingPlanResponse> res = new ArrayList<SavingPlanResponse>();
		for (SPDetailInfo_type0 info : spDetailInfo) {
			SavingPlanResponse response = new SavingPlanResponse(info);
			AccountList accountList = accountListDao.getAccountListByAccountNumber(response.getAccountNo(), response.getCurrency());
			if( accountList != null ){
				MappingProduct product = mappingProductDao.getProductById(accountList.getProductId());
				response.setProduct(product);
				res.add(response);
			}			
		}
		return res;
	}

}
