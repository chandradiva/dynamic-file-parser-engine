package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.UserDao;
import com.optima.oraclejdbc.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao dao;
	
	public User getUserByUsername(String username) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		return dao.getUserByUsername(username);
	}
	
	public void save(User user) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		dao.insertOrUpdate(user);
	}
}
