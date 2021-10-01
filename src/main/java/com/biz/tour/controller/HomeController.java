package com.biz.tour.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.tour.dao.TeraDashboardDao;
import com.biz.tour.domain.VisitedVO;
import com.biz.tour.service.TeraDashBoardService;
import com.biz.tour.service.util.GetFolderSize;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	private final String videosPath;
	private final TeraDashBoardService teraDashBoardService;
	private final TeraDashboardDao teraDashboardDao;
	//,produces = "text/json;charset=UTF-8"
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws URISyntaxException {
		List<VisitedVO> visitedList=teraDashBoardService.getDailyCon(10);
		int totalUserCnt=teraDashboardDao.getTotalUserCnt();
		int totalMusicChCnt=teraDashboardDao.getTotalMusicCh();
		
		File dir = new File(videosPath);
		long videoFolderSize=GetFolderSize.getDirectorySizeCommonIO(dir);
		model.addAttribute("videoFolderSize", videoFolderSize);
		model.addAttribute("totalUserCnt", totalUserCnt);
		model.addAttribute("totalMusicChCnt", totalMusicChCnt);
		model.addAttribute("visitedList", visitedList);
		return "tera/main";
	}
	
	
}
