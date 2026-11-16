package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.DDIDao;
import com.optima.oraclejdbc.model.estatement.ESAccountInfo;

@Service
public class DDIService {
	@Autowired
	DDIDao dao;
	
	public ESAccountInfo getESAccountInfoByCifkey(String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getEsAccountInfoByCif(cifKey);
	}
}
