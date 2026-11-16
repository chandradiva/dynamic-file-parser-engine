package com.optima.oraclejdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.utils.HousekeepingUtils;

@Service
public class HouseKeepingService {
	@Autowired
	HousekeepingUtils hkUtils;
	
	public void houseKeeping(){
		hkUtils.houseKeeping();
	}
}
