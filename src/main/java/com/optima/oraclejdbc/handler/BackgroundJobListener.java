package com.optima.oraclejdbc.handler;

import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.optima.oraclejdbc.cons.TimerCons;
import com.optima.oraclejdbc.service.BancassWatchDir;
import com.optima.oraclejdbc.service.CCWatchDir;
import com.optima.oraclejdbc.service.DDIWatchDir;
import com.optima.oraclejdbc.service.DailyStatementWatchDir;
import com.optima.oraclejdbc.service.UnitTrustWatchDir;

public class BackgroundJobListener implements ServletContextListener {
	private static BackgroundJobListener instance;
	
	private DailyStatementWatchDir dsWatch;
	
	private CCWatchDir ccWatch;
	
	private BancassWatchDir bsWatch;
	
	private UnitTrustWatchDir utWatch;
	
	private DDIWatchDir ddiWatch;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try{
			if( instance != null ){
				dsWatch.stop();
				ccWatch.stop();
				utWatch.stop();
				ddiWatch.stop();
				bsWatch.stop();
				ScheduledExecutorService scheduler = TimerCons.getInstance();
				scheduler.shutdownNow();
				System.out.println("Shutdown all services");
			}			
		}catch( Exception e){
			e.printStackTrace();
			System.out.print("Exception on shutdown service");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {		
		instance = this;
	}
	
	public static BackgroundJobListener getInstance(){
		return instance;
	}

	public void setDsWatch(DailyStatementWatchDir dsWatch) {
		this.dsWatch = dsWatch;
	}

	public void setCcWatch(CCWatchDir ccWatch) {
		this.ccWatch = ccWatch;
	}

	public void setBsWatch(BancassWatchDir bsWatch) {
		this.bsWatch = bsWatch;
	}

	public void setUtWatch(UnitTrustWatchDir utWatch) {
		this.utWatch = utWatch;
	}

	public void setDdiWatch(DDIWatchDir ddiWatch) {
		this.ddiWatch = ddiWatch;
	}
	
	
}
