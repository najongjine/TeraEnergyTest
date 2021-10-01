package com.biz.tour.dao;

import java.util.List;

import com.biz.tour.domain.MusicChMylistVO;
import com.biz.tour.domain.MusicChVO;
import com.biz.tour.domain.MusicChVideosVO;
import com.biz.tour.domain.VisitedVO;

public interface TeraDao {
	public int insertMusicCh(MusicChVO musicChVO);
	public int updateMusicCh(MusicChVO musicChVO);
	public int updateMusicChPosterFile(MusicChVO musicChVO);
	public int updateMusicChVideoFile(MusicChVO musicChVO);
	public int deleteMusicCh(int id);
	
	public int insertMusicChVideos(MusicChVideosVO musicChVidoesVO);
	
	public MusicChVO getMusicChById(int id);
	public List<MusicChVO> getMusicChList(MusicChVO musicChVO);
	public int cntMusicChList();
	public MusicChMylistVO getMusicFromMylist(MusicChMylistVO musicChMylistVO);
	public MusicChMylistVO getNextMusicFromMylist(MusicChMylistVO musicChMylistVO);
	public MusicChMylistVO getPrevMusicFromMylist(MusicChMylistVO musicChMylistVO);
	public MusicChMylistVO getMinOrderMusicFromMylist(MusicChMylistVO musicChMylistVO);
	public MusicChMylistVO getMaxOrderMusicFromMylist(MusicChMylistVO musicChMylistVO);
	public MusicChMylistVO getChInMyList(MusicChMylistVO musicChMylistVO);
	public int insertMusicChMylist(MusicChMylistVO musicChMylistVO);
	public int deleteMyListChByiorder(MusicChMylistVO musicChMylistVO);
	public int deleteMyListChBychid(MusicChMylistVO musicChMylistVO);
	
	public List<MusicChMylistVO> getMyList(MusicChMylistVO musicChMylistVO);
	public int cntMyList(MusicChMylistVO musicChMylistVO);
	
	public int insertVisitedPpl(VisitedVO visitedVO);
}
