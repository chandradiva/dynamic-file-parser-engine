package com.optima.oraclejdbc.service;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.DailyStatement;

@Service
public class DailyStatementWatchDir extends WatchDir<DailyStatement>{
	public DailyStatementWatchDir() throws IOException{
		super();
	}
}
