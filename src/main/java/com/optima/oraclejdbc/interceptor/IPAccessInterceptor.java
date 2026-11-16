package com.optima.oraclejdbc.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.optima.oraclejdbc.model.IPWhiteList;
import com.optima.oraclejdbc.service.IPWhiteListService;

public class IPAccessInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = Logger.getLogger(IPAccessInterceptor.class);
	@Autowired
	IPWhiteListService service;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String remoteIp = request.getRemoteAddr();
			List<IPWhiteList> ips = service.getAlloweds();
			
			for (IPWhiteList ipWhiteList : ips) {
				if( ipWhiteList.getIp().equals(remoteIp) ){
					return true;
				}
					
			}
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			httpResponse.getOutputStream().write((request.getRemoteAddr()+" is not allowed to access this service.").getBytes("UTF-8"));
			return false;
		} catch (Exception e) {
			logger.error("Error", e);
		}
		return false;
	}
}
