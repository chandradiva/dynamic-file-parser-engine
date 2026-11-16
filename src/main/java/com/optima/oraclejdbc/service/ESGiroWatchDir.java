package com.optima.oraclejdbc.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.model.estatement.ESGiro;

@Component
public class ESGiroWatchDir extends WatchDir<ESGiro> {

	public ESGiroWatchDir() throws IOException {
		super();
	}

}
