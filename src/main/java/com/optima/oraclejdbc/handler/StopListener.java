package com.optima.oraclejdbc.handler;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.cons.TimerCons;
import com.optima.oraclejdbc.service.BancassWatchDir;
import com.optima.oraclejdbc.service.CCWatchDir;
import com.optima.oraclejdbc.service.DDIWatchDir;
import com.optima.oraclejdbc.service.DailyStatementWatchDir;
import com.optima.oraclejdbc.service.ESGiroWatchDir;
import com.optima.oraclejdbc.service.ESMCBilingualWatchDir;
import com.optima.oraclejdbc.service.ESMultiCurrencyWatchDir;
import com.optima.oraclejdbc.service.UnitTrustWatchDir;

@Service
public class StopListener implements ApplicationListener<ContextStoppedEvent>{
	@Autowired
	DailyStatementWatchDir dsWatch;
	
	@Autowired
	CCWatchDir ccWatch;
	
	@Autowired
	BancassWatchDir bsWatch;
	
	@Autowired
	UnitTrustWatchDir utWatch;
	
	@Autowired
	DDIWatchDir ddiWatch;
	
	@Override
	public void onApplicationEvent(ContextStoppedEvent event) {
		dsWatch.stop();
		ccWatch.stop();
		bsWatch.stop();
		utWatch.stop();
		
		ddiWatch.stop();
		
		ScheduledExecutorService scheduler = TimerCons.getInstance();
		scheduler.shutdownNow();
	}
}
