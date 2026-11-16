package com.optima.oraclejdbc.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.daoimpl.HouseKeepingDao;
import com.optima.oraclejdbc.daoimpl.HousekeepingLogDao;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;

@Component
public class HousekeepingUtils {

	@Autowired
	HouseKeepingDao hkDao;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	@Autowired
	HousekeepingLogDao hklDao;
	
	private static HousekeepingUtils instance = new HousekeepingUtils();
	
	private HousekeepingUtils(){}
	
	public static HousekeepingUtils getInstance(){
		return instance;
	}
	
	public boolean houseKeeping(){
		Calendar calToday = Calendar.getInstance();
		calToday.set(Calendar.HOUR_OF_DAY, 00);
		calToday.set(Calendar.MINUTE, 00);
		calToday.set(Calendar.SECOND, 00);
		calToday.set(Calendar.MILLISECOND, 000);
		Date today = calToday.getTime();
		String strHkVal = sysParamDao.getParameterValue("MRHPDS");
		int hkVal;
		if( strHkVal == null ){
			hkVal = 24;
		}else{
			hkVal = Integer.parseInt(strHkVal);
		}
		String hostname = confProperties.getProperty("HOST_NAME");
		hkDao.houseKeeping(hostname);			
		
		String ftp_work_dir=confProperties.getProperty("ftp_work_dir");
		Date start = new Date();
		int status = cleanFile(ftp_work_dir, today, hkVal)?1:0;						
		Date end = new Date();
		hklDao.insertLog(start, end, status, "Clean Up Directory "+ftp_work_dir+" on "+hostname);
		
		String reverse_work_dir=confProperties.getProperty("reverse_work_dir");
		start = new Date();			
		status = cleanFile(reverse_work_dir, today, hkVal)?1:0;
		end = new Date();
		hklDao.insertLog(start, end, status, "Clean Up Directory "+reverse_work_dir+" on "+hostname);
		
		String temp_dir=confProperties.getProperty("temp_dir");
		start = new Date();			
		status = cleanFile(temp_dir, today, hkVal)?1:0;
		end = new Date();
		hklDao.insertLog(start, end, status, "Clean Up Directory "+temp_dir+" on "+hostname);
		
		String ms_dir=confProperties.getProperty("ms_dir");
		start = new Date();			
		status =cleanFile(ms_dir, today, hkVal)?1:0;
		end = new Date();
		hklDao.insertLog(start, end, status, "Clean Up Directory "+ms_dir+" on "+hostname);
		return true;
	}
	
	private boolean cleanFile(String dir, Date today, int hkVal) {
		boolean result = false;
		
		try {
			File hkDir = new File(dir);
			if (hkDir.isDirectory()) {
				File[] files = hkDir.listFiles();							
				
				for (File item : files) {
					if( item.isDirectory() ){
						cleanFile(item.getAbsolutePath(), today, hkVal);
					}else{
						BasicFileAttributes attr = Files.readAttributes(item.toPath(), BasicFileAttributes.class);
						
//						Date creationDate = new Date(attr.creationTime().toMillis());
						Date creationDate = new Date(attr.lastModifiedTime().toMillis());
						Calendar cal = Calendar.getInstance();
						cal.setTime(creationDate);
						cal.set(Calendar.HOUR_OF_DAY, 00);
						cal.set(Calendar.MINUTE, 00);
						cal.set(Calendar.SECOND, 00);
						cal.set(Calendar.MILLISECOND, 000);
						creationDate = cal.getTime();
						
						int diff = getDiffInMonths(today, creationDate);
						if (diff >= hkVal) {
							item.delete();
						}
					}
					
				}
				
				result = true;
			} else {
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			
			result = false;
		}
		
		return result;
	}
	
	private int getDiffInMonths(Date today, Date creation) {
		int diff = 0;
		
		Calendar cToday = Calendar.getInstance();
		cToday.setTime(today);
		
		Calendar cCreate = Calendar.getInstance();
		cCreate.setTime(creation);
		
		if (cToday.after(cCreate)) {
			while (cToday.after(cCreate)) {
				cCreate.add(Calendar.MONTH, 1);
				if (cToday.after(cCreate) || cToday.equals(cCreate)) {
					diff++;
				}
			}
		}
		
		return diff;
	}
}
