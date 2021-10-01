package com.biz.tour.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 요 3줄은 디버그용
		String urlPath=request.getRequestURL().toString();
		String uriPath=request.getRequestURI().toString();
		String msg=String.format("!!! \n URL:%s, \n URI: %s", urlPath,uriPath);
		
		//session 정보가 없으면
		HttpSession session=request.getSession();
		Object sessionObj=session.getAttribute("U_NAME");
		if(sessionObj==null) {
			//로그인 페이지로
			response.sendRedirect(request.getContextPath()+"/member/login");
			return false;
		}
		log.debug(msg);
		return super.preHandle(request, response, handler);
	}
}
