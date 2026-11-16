package com.optima.oraclejdbc.cons;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TimerCons {
	private static ScheduledExecutorService scheduler = null;
	
	public static ScheduledExecutorService getInstance(){
		if( scheduler == null ){
			scheduler = Executors.newScheduledThreadPool(2);
		}
		return scheduler;
	}
}
