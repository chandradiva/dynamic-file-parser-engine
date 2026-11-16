package com.optima.oraclejdbc.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.model.CCParentModel;

@Component
public class CCWatchDir extends WatchDir<CCParentModel>{

	public CCWatchDir() throws IOException {
		super();
	}

}
