package com.optima.oraclejdbc.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optima.oraclejdbc.cons.ResponseErrorCode;
import com.optima.oraclejdbc.response.Response;
import com.optima.oraclejdbc.service.ApplicationParameterService;
import com.optima.oraclejdbc.utils.CommonUtils;

@RestController
@RequestMapping(value="/appparam")
public class ApplicationParameterController {
	private static final Logger logger = Logger.getLogger(ApplicationParameterController.class);
	
	@Autowired
	ApplicationParameterService service;
	
	@RequestMapping(value="/getparam", method=RequestMethod.POST)
	public Response getParam(@RequestParam String key){
		try{
			String param = service.getParameterValue(key);
			Response res = new Response();
			res.setData(param);
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value="/getparams", method=RequestMethod.POST)
	public Response getParams(@RequestParam List<String> keys){
		try{
			Map<String, String> params = service.getParameterValues(keys);
			Response res = new Response();
			res.setData(params);
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}
		
	}
	
	@RequestMapping(value="/getallparams", method=RequestMethod.GET)
	public Response getAllParams(){
		try{
			Map<String, String> params = service.getAllParameters();
			Response res = new Response();
			res.setData(params);
			res.setResultCode(ResponseErrorCode.SUCCESS);
			res.setMessage(ResponseErrorCode.MSG_SUCCESS);
			return res;
		}catch( Exception e){
			logger.error("Error", e);
			return CommonUtils.responseException(e);
		}		
	}
}
