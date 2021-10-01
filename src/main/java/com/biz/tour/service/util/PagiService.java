package com.biz.tour.service.util;

import com.biz.tour.domain.util.PageDTO;

public interface PagiService {
	/*
	 * 최소한의 조건으로 페이지를 계산하기 위한 method
	 * 전체 데이터개수와 현재 선택된 페이지번호만 매개변수로만 받아서
	 * 페이지 정보를 만들어내기
	 */
	public PageDTO makePageNation(int totalCount,int currentPageNo,int listPerPage);
}
