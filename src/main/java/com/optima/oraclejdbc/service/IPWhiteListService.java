package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.IPWhiteListDao;
import com.optima.oraclejdbc.model.IPWhiteList;

@Service
public class IPWhiteListService {

	@Autowired
	IPWhiteListDao dao;
	
	public List<IPWhiteList> getAlloweds() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getAlloweds();
	}
}
