package com.biz.tour.service.util;

import org.springframework.stereotype.Service;

import com.biz.tour.domain.util.PageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PagiServiceImp implements PagiService{
	int pageCount=5;

	/*
	 * 최소한의 조건으로 페이지를 계산하기 위한 method
	 * 전체 데이터개수와 현재 선택된 페이지번호만 매개변수로만 받아서
	 * 페이지 정보를 만들어내기
	 */
	public PageDTO makePageNation(int totalCount,int currentPageNo,int listPerPage) {
		//데이터가 없으면 중단
		if(totalCount<1) return null;
		
		//전체데이터+보여질리스트-1  / 보여질리스트
		int finalPageNo=(totalCount+(listPerPage-1))/listPerPage;
		
		//naver는 페이지를 검색할때 1000페이지가 넘어가면 오류를 낸다.
		//finalPageNo=finalPageNo >1000? 1000:finalPageNo;
		
		//currentPageNo 가 마지막 페이지보다 크면
		if(currentPageNo>finalPageNo) currentPageNo=finalPageNo;
		
		if(currentPageNo<1) currentPageNo=1;
		
		//이전,이후를 계산하기 위해서 현재 페이지번호가 첫페이지인가, 마지막페이지인가를 검사해서
		//flag를 셋팅
		boolean isNowFirst=currentPageNo==1;
		boolean isNowFinal=currentPageNo==finalPageNo;
		
		//하단에 리스트로 보여줄 페이지 계산
		//cur:3 이면 1~5, cur 10이면 8~12
		int startPageNo=((currentPageNo-1)/pageCount)*pageCount+1;
		
		int endPageNo=startPageNo+pageCount-1;
		
		if(endPageNo>finalPageNo) endPageNo=finalPageNo;
		PageDTO pageDTO=PageDTO.builder().totalCount(totalCount).pageCount(pageCount)
				.listPerPage(listPerPage).firstPageNo(1)
				.finalPageNo(finalPageNo)
				.endPageNo(endPageNo).startPageNo(startPageNo)
				.currentPageNo(currentPageNo).build();
		if(isNowFirst) pageDTO.setPrePageNo(1);
		else pageDTO.setPrePageNo((currentPageNo-1) <1? 1:currentPageNo-1);
		if(isNowFinal) pageDTO.setNextPageNo(finalPageNo);
		else pageDTO.setNextPageNo((currentPageNo+1) > finalPageNo ? finalPageNo :currentPageNo+1);
		
		return pageDTO;
	}
}
