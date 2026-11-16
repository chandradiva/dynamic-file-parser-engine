package com.optima.oraclejdbc.service;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.BAParentModel;
import com.optima.oraclejdbc.model.Bancass;

@Service
public class BancassWatchDir extends WatchDir<BAParentModel> {

	public BancassWatchDir() throws IOException {
		super();
	}

}
