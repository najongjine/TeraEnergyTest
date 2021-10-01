package com.biz.tour.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.tour.dao.TeraDashboardDao;
import com.biz.tour.domain.VisitedVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeraDashBoardService {
	private final TeraDashboardDao teraDashboardDao;
	
	public List<VisitedVO> getDailyCon(int intdays){
		List<VisitedVO> visitedList=new ArrayList<>();
		for (int i=0;i<intdays;i++) {
			VisitedVO visitedVO=teraDashboardDao.getDailyCon(i);
			visitedList.add(visitedVO);
		}
		return visitedList;
	}
	
	
}
