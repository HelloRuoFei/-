package com.snow.sys.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 工具类。用来得到requset， 得到session
 * */

public class WebUtils {
	
	/**
	 * 
	 * 得到requset
	 * */
	public static HttpServletRequest getRequest() {
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();//拿到request对象并返回
		return request;
		
	}
	
	/**
	 * 
	 * 得到session
	 * */
	public static HttpSession getSession() {
		
		return getRequest().getSession();
	}
		
}
