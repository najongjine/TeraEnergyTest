package com.biz.tour.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetCurrentDateServiceImp implements GetCurrentDateService{
	public String getCurTime() {
		SimpleDateFormat curTime = new SimpleDateFormat ( "HH:mm:ss");
				
		Date time = new Date();
		
		String strcurTime = curTime.format(time);
		return strcurTime;
	}
	public String getCurDate() {
		SimpleDateFormat curDate = new SimpleDateFormat ( "yyyy-MM-dd");
				
		Date date = new Date();
		
		String strcurDate = curDate.format(date);
		return strcurDate;
	}
}
