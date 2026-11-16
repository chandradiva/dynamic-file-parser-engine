package com.optima.oraclejdbc.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.cons.TimerCons;
import com.optima.oraclejdbc.daoimpl.ApplicationParameterDao;
import com.optima.oraclejdbc.service.BOSendEmailService;
import com.optima.oraclejdbc.service.BancassWatchDir;
import com.optima.oraclejdbc.service.CCWatchDir;
import com.optima.oraclejdbc.service.DDIWatchDir;
import com.optima.oraclejdbc.service.DailyStatementWatchDir;
import com.optima.oraclejdbc.service.ExchangeRateService;
import com.optima.oraclejdbc.service.HouseKeepingService;
import com.optima.oraclejdbc.service.UnitTrustWatchDir;
import com.optima.oraclejdbc.utils.CommonUtils;

@Service
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = Logger.getLogger(StartUpListener.class);
	
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
	
	@Autowired
	ExchangeRateService exchangeRateService;
	
	@Autowired
	ApplicationParameterDao applicationParameterDao;
	
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	@Autowired
	HouseKeepingService hkService;
	
	@Autowired
	BOSendEmailService emailService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initScheduler();
		try {
			String ftpWorkDir = CommonUtils.getFtpWorkDir(confProperties);
			//daily statement
			String dsWatchDir = ftpWorkDir+CommonCons.DAILY_STATEMENT_DIR;
			Path dsDir = Paths.get(dsWatchDir);
			dsWatch.setRootDir(dsDir, true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						dsWatch.processEvents();
					}catch( Exception e){
						logger.error("Error", e);						
					}					
				}
			}).start();
			
			//credit card
			String ccWatchDir = ftpWorkDir+CommonCons.CREDIT_CARD_DIR;
			Path ccDir = Paths.get(ccWatchDir);
			ccWatch.setRootDir(ccDir, true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						ccWatch.processEvents();
					}catch( Exception e){
						logger.error("Error", e);						
					}					
				}
			}).start();
			
			//bancass
			String bsWatchDir = ftpWorkDir+CommonCons.BANCASS_DIR;
			Path bsDir = Paths.get(bsWatchDir);
			bsWatch.setRootDir(bsDir, true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						bsWatch.processEvents();
					}catch( Exception e){
						logger.error("Error", e);						
					}					
				}
			}).start();
			
			//unit trust
			String utWatchDir = ftpWorkDir+CommonCons.UNIT_TRUST_DIR;
			Path utDir = Paths.get(utWatchDir);
			utWatch.setRootDir(utDir, true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						utWatch.processEvents();
					}catch( Exception e){
						logger.error("Error", e);						
					}					
				}
			}).start();
			
			//ddi
			String ddiWatchDir = ftpWorkDir + CommonCons.DDI_DIR;
			Path ddiDir = Paths.get(ddiWatchDir);
			ddiWatch.setRootDir(ddiDir, true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						ddiWatch.processEvents();
					}catch( Exception e){
						logger.error("Error", e);						
					}					
				}
			}).start();
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}

	private void initScheduler(){
		BackgroundJobListener bgJob = BackgroundJobListener.getInstance();
		bgJob.setBsWatch(bsWatch);
		bgJob.setCcWatch(ccWatch);
		bgJob.setDdiWatch(ddiWatch);
		bgJob.setDsWatch(dsWatch);
		bgJob.setUtWatch(utWatch);
		houseKeeping();
		List<Map<String, Object>> params = applicationParameterDao.getParameterValues();
		intervalErrt(CommonUtils.getErrt(params));
		intervalEmailCheck(CommonUtils.getErcp(params));
	}
	
	private void intervalErrt(int seconds){
		ScheduledExecutorService scheduler = TimerCons.getInstance();		
		scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
					exchangeRateService.getExchangeRate();
				}catch( Exception e){
					logger.error("Error", e);					
				}				
			}
		}, 0L, seconds, TimeUnit.SECONDS);		
	}
	
	private void intervalEmailCheck(int seconds){
		ScheduledExecutorService scheduler = TimerCons.getInstance();		
		scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try {
					emailService.processNewEmail();
				} catch (MessagingException | IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
					logger.error("Error", e);
				}
			}
		}, 0L, seconds, TimeUnit.SECONDS);
	}
	
	private void houseKeeping(){
		Calendar cal = Calendar.getInstance();
		long delay = (23-cal.get(Calendar.HOUR_OF_DAY))*3600 + (59-cal.get(Calendar.MINUTE))*60 + 59 - cal.get(Calendar.SECOND); 
		ScheduledExecutorService scheduler = TimerCons.getInstance();
		scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
					hkService.houseKeeping();
					emailService.startEmailRetention();
				}catch( Exception e){
					logger.error("Error", e);					
				}				 
			}
		}, delay, 24*60*60, TimeUnit.SECONDS);		
	}
}
