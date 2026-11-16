package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.LoginHistoryDao;
import com.optima.oraclejdbc.model.LoginHistory;

@Service
public class LoginService {
	
	@Autowired
	LoginHistoryDao dao;
	
	public void save(LoginHistory data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		data.setLoginTime(new Date());
		dao.create(data);
	}
}
