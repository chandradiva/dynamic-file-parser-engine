package com.optima.oraclejdbc.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.optima.oraclejdbc.cons.CommonCons;

public class LogFilter implements Filter {
	private static final Logger logger = Logger.getLogger("Logging Filter");
	
	@Override
	public void destroy() {
		// NOP
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		if ( request instanceof HttpServletRequest) {
		    try {			    
			    Date startTime = new Date(System.currentTimeMillis());
			    CustomResponseWrapper responseWrapper = new CustomResponseWrapper((HttpServletResponse)response);
			    filterChain.doFilter(request, responseWrapper);
			    String url = ((HttpServletRequest) request).getRequestURI();
			    String queryString = ((HttpServletRequest) request).getQueryString();
			    if( queryString != null )
				    url += "?"+queryString;
			    String contentTypeStr = request.getContentType();			    
			    String strReqParam = "N/A";
			    if ("POST".equalsIgnoreCase(((HttpServletRequest)request).getMethod()) && contentTypeStr != null && contentTypeStr.toLowerCase().startsWith(CommonCons.X_WWW_FORM_URLECODED)) {
				    strReqParam = getStrReqMap((HttpServletRequest)request);
			    }
			    byte[] byteResponse = responseWrapper.getDataStream();
			    if( byteResponse.length > 0 )
			    	response.getOutputStream().write(byteResponse);
			    String respContentType = response.getContentType();
			    String method = ((HttpServletRequest) request).getMethod();
			    String requesterIp = request.getRemoteAddr();
			    Date endTime = new Date(System.currentTimeMillis());
			    long executeTime = endTime.getTime() - startTime.getTime();
			    String strLog = "url: "+url
					    +"\nmethod: "+method
					    +"\nrequester ip: "+requesterIp
					    +"\nrequest param: "+strReqParam;
		    	if( respContentType != null && !respContentType.startsWith("image") && byteResponse.length > 0){
		    		strLog+="\nresponse content type: "+respContentType
		    				+"\nresponse: "+new String(byteResponse);			    		
		    	}
		    	strLog += "\nexecution time: "+executeTime+" ms";		    	
		    	logger.debug(strLog);	   
		    } catch (ServletException e) {
		        e.printStackTrace();
		        throw e;
		    }
		} else {
		    filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// NOP
	}

	public String getStrReqMap(HttpServletRequest request){
		String requestMap = "";
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		while( paramNames.hasMoreElements() ){
			String paramName = (String)paramNames.nextElement();
			if( requestMap.length() > 0 )
				requestMap += "&";
			requestMap += paramName + "=";
			String[] values = request.getParameterValues(paramName);
			String value = "";
			for (String val : values) {
				if( value.length() > 0 )
					value += ",";
				value += val;
			}
			requestMap+=value;
		}
		return requestMap;
	}
}
