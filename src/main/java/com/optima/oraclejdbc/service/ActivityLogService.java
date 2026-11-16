package com.optima.oraclejdbc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.ActivityLogDao;
import com.optima.oraclejdbc.daoimpl.UrlActivityMappingDao;

@Service
public class ActivityLogService {

	@Autowired
	ActivityLogDao activityLogDao;
	
	@Autowired
	UrlActivityMappingDao urlActivityMappingDao;
	
	public void insertUrlMapping(Map<String, String> map){
		activityLogDao.insertUrlMapping(map);
	}

	public void insertUrlMapping(String[] urlMapping, String[] actionName) {
		activityLogDao.insertUrlMapping(urlMapping,actionName);
		
	}
	
	private String getInformation(String requestMap){
		String info = "";
		Map<String, String> map = getMapFromReqParam(requestMap);
		
		if(map.containsKey("hcReqId")){
			String hcReqId = map.get("hcReqId");
			
			info += "<strong>Hardcopy Request Id : " + (hcReqId!=null ? hcReqId : "") + "</strong>"; 
		}
		
		/*if(map!=null && !map.isEmpty()){
			for (Object key : map.keySet()) {
				String keyString = key.toString();
				
				if(keyString!=null && !keyString.isEmpty()){
					info += "<Strong>";
					
					info += keyString + " " ;
				}
			}
		}*/
				
		return info;
	}
	
	private Map<String, String> getMapFromReqParam(String strParam){
		Map<String, String> map = new HashMap<String,String>();
		
		String[] pairs = strParam.split(",\\|");
		
		for(String pair : pairs){
			if(pair.contains("|") && pair.contains("=")){
				String key = pair.substring(pair.indexOf('|')+1, pair.indexOf('='));
				String value = pair.substring(pair.indexOf('=')+1);
				
				map.put(key, value);
			}
		}		
		
		return map;
	}
	
	public void insertUserActivity(String urlPath,String reqMethod,String userID,Date startTime,Date finishTime,String sessionId, String requestMap,String source, String ipAddress, String browser, String cifKey) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		if(urlActivityMappingDao.getByUrl(urlPath)!=null){
			String reReqHardcopyUrl = "statement/re-req-hc";
			String info = "-";
			
			if(urlPath.equals(reReqHardcopyUrl)){
				info = getInformation(requestMap);
			}
			
			if(requestMap.equalsIgnoreCase("null")){
				requestMap = "-";
			} else if(requestMap.startsWith("null") && requestMap.length()>4){
				requestMap = requestMap.substring(4);
			}
			
			if(urlPath.equalsIgnoreCase("personalization")){
				if(!requestMap.equals("-")){
					activityLogDao.insertUserActivity(urlPath, reqMethod, userID, startTime, finishTime,sessionId,requestMap,source,ipAddress,browser, info, cifKey);
				}
			} else {
				if(urlPath.equalsIgnoreCase("settings/changepassword/save")){
					requestMap = "-";
				} 
				
				activityLogDao.insertUserActivity(urlPath, reqMethod, userID, startTime, finishTime,sessionId,requestMap,source,ipAddress,browser, info, cifKey);
			}
		}
	}
}
