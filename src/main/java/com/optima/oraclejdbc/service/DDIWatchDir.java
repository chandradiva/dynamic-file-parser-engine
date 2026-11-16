package com.optima.oraclejdbc.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.model.estatement.DDIModel;

@Component
public class DDIWatchDir extends WatchDir<DDIModel> {

	public DDIWatchDir() throws IOException {
		super();
	}

}
