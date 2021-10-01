package com.biz.tour;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.biz.tour.dao.TeraDao;
import com.biz.tour.domain.VisitedVO;
import com.biz.tour.domain.member.MemberVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Intercepter extends HandlerInterceptorAdapter{
	private final TeraDao teraDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

    	VisitedVO visitedVO=new VisitedVO();
    	visitedVO.setIp(request.getRemoteAddr());
    	teraDao.insertVisitedPpl(visitedVO);
        return super.preHandle(request, response, handler);
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }



}
