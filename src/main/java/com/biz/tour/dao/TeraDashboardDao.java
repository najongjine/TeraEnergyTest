package com.biz.tour.dao;

import com.biz.tour.domain.VisitedVO;

public interface TeraDashboardDao {
	public VisitedVO getDailyCon(int intday);
	
	public int getTotalUserCnt();
	public int getTotalMusicCh();
}
