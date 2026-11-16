package com.optima.oraclejdbc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.service.ActivityLogService;
import com.optima.oraclejdbc.service.ApplicationParameterService;
import com.optima.oraclejdbc.utils.StubUtil;

@RestController
@RequestMapping(value = "/api")
public class ActivityLogController {

	@Autowired
	ActivityLogService activityLogService;
	
		
	@Autowired
	ApplicationParameterService applicationParameterService;
	
	@Autowired
	StubUtil stubUtil;
	
	
	
	@RequestMapping(value = "activityLog", method = RequestMethod.POST)
	public @ResponseBody Response activityUserLog(@RequestParam String urlPath,@RequestParam String reqMethod,@RequestParam String userID,@RequestParam String startTime,@RequestParam String finishTime,@RequestParam String sessionId,@RequestParam String requestMap,@RequestParam String source,@RequestParam String ipAddress,@RequestParam String browser, @RequestParam String cifKey) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Response response = new Response();
		try {
			Date startD = sdf.parse(startTime);
			Date endD = sdf.parse(finishTime);			
			activityLogService.insertUserActivity(urlPath, reqMethod, userID, startD, endD, sessionId,requestMap,source,ipAddress,browser, cifKey);
			response.setResultCode(ResponseErrorCode.SUCCESS);
			response.setMessage(ResponseErrorCode.MSG_SUCCESS);
		} catch (Exception e) {
			response.setResultCode(ResponseErrorCode.INTERNAL_SERVER_ERROR);
			response.setMessage(ResponseErrorCode.MSG_INTERNAL_SERVER_ERROR);
		}
		
		return response;
		
	}
	
	
	
}
