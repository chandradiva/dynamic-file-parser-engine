package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.CustomerInfoDao;
import com.optima.oraclejdbc.model.CustomerInfo;

@Service
public class CustomerInfoService {
	
	@Autowired
	CustomerInfoDao dao;
	
	public CustomerInfo getUserByUserId(String userId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getUserByUserId(userId);
	}
	
	public CustomerInfo getUserByCIFKey(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getUserByCIFKey(cifKey);
	}
	
	public void save(CustomerInfo user) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		dao.insertOrUpdate(user);
	}
}
