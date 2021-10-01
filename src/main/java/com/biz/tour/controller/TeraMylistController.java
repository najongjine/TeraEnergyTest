package com.biz.tour.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.tour.dao.TeraDao;
import com.biz.tour.domain.MusicChMylistVO;
import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.domain.util.PageDTO;
import com.biz.tour.service.member.MemberService;
import com.biz.tour.service.util.PagiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({ "musicChMylistVO" })
public class TeraMylistController {
	private final TeraDao teraDao;
	private final PagiService pagiService;
	private final MemberService memService;

	@ModelAttribute("MusicChMylistVO")
	public MusicChMylistVO makemylistVO() {
		MusicChMylistVO dummyVO = new MusicChMylistVO();
		return dummyVO;
	}

	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public String mylist(@RequestParam(value="pageIndex", defaultValue="1") int pageIndex,Model model) {
		Object securityContext=SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		MemberVO memberVO=null;
		if(securityContext!=null && !"anonymousUser".equals(securityContext)) {
			memberVO=(MemberVO) securityContext;
		}
		if(memberVO==null) {
			return null;
		}
		int itemsPerpage=50;
		MusicChMylistVO musicChMylistVO=new MusicChMylistVO();
		musicChMylistVO.setMember_id(memberVO.getId());
		musicChMylistVO.setPageIndex((pageIndex-1)*itemsPerpage);
		musicChMylistVO.setItemsPerpage(itemsPerpage);
		int myListCnt=teraDao.cntMyList(musicChMylistVO);
		List<MusicChMylistVO> myList=teraDao.getMyList(musicChMylistVO);
		PageDTO pageDTO=pagiService.makePageNation(myListCnt, pageIndex, itemsPerpage);
		model.addAttribute("myListCnt", myListCnt);
		model.addAttribute("myList", myList);
		model.addAttribute("PAGE", pageDTO);
		return "tera/mylist/mychList";
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/mych/{id}", method = RequestMethod.GET)
	public String mych(@PathVariable int id,Model model) {
		Object securityContext=SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		MemberVO memberVO=null;
		if(securityContext!=null && !"anonymousUser".equals(securityContext)) {
			memberVO=(MemberVO) securityContext;
		}
		if(memberVO==null) {
			return null;
		}
		MusicChMylistVO musicChMylistVO=new MusicChMylistVO();
		musicChMylistVO.setMember_id(memberVO.getId());
		musicChMylistVO.setId(id);
		MusicChMylistVO myCh=teraDao.getMusicFromMylist(musicChMylistVO);
		model.addAttribute("myCh", myCh);
		return "tera/mylist/view_video";
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/mynextch/{iorder}", method = RequestMethod.GET)
	public String mynextch(@PathVariable int iorder,Model model) {
		Object securityContext=SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		MemberVO memberVO=null;
		if(securityContext!=null && !"anonymousUser".equals(securityContext)) {
			memberVO=(MemberVO) securityContext;
		}
		if(memberVO==null) {
			return null;
		}
		MusicChMylistVO musicChMylistVO=new MusicChMylistVO();
		musicChMylistVO.setMember_id(memberVO.getId());
		musicChMylistVO.setIorder(iorder);
		MusicChMylistVO myCh=teraDao.getNextMusicFromMylist(musicChMylistVO);
		if(myCh==null) {
			myCh=teraDao.getMinOrderMusicFromMylist(musicChMylistVO);
		}
		model.addAttribute("myCh", myCh);
		return "tera/mylist/view_video";
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/myprevch/{iorder}", method = RequestMethod.GET)
	public String myprevch(@PathVariable int iorder,Model model) {
		Object securityContext=SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		MemberVO memberVO=null;
		if(securityContext!=null && !"anonymousUser".equals(securityContext)) {
			memberVO=(MemberVO) securityContext;
		}
		if(memberVO==null) {
			return null;
		}
		MusicChMylistVO musicChMylistVO=new MusicChMylistVO();
		musicChMylistVO.setMember_id(memberVO.getId());
		musicChMylistVO.setIorder(iorder);
		MusicChMylistVO myCh=teraDao.getPrevMusicFromMylist(musicChMylistVO);
		if(myCh==null) {
			myCh=teraDao.getMaxOrderMusicFromMylist(musicChMylistVO);
		}
		model.addAttribute("myCh", myCh);
		return "tera/mylist/view_video";
	}
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/add_sub_mylist_json", method = RequestMethod.GET,produces="application/json;charset=utf-8")
	public Map<String,Object> myprevch(@RequestParam(value="type", defaultValue="add") String type,@RequestParam(value="ch_id", defaultValue="0") int ch_id
			,Model model) {
		Map<String,Object> data=new HashMap<>();
		Object securityContext=SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		MemberVO memberVO=(MemberVO) securityContext;
		
		MusicChMylistVO musicChMylistVO=new MusicChMylistVO();
		musicChMylistVO.setMusic_ch_id(ch_id);
		musicChMylistVO.setMember_id(memberVO.getId());
		
		int ret=0;
		if("add".equals(type)) {
			ret=teraDao.insertMusicChMylist(musicChMylistVO);
		}else {
			ret=teraDao.deleteMyListChBychid(musicChMylistVO);
		}
		
		if(ret>0) {
			data.put("success", true);
			data.put("type", type);
		}else {
			data.put("success", false);
			data.put("type", type);
		}
		
		return data;
	}
	@ResponseBody
	@RequestMapping(value = "/check_is_added_to_mylist_json/{ch_id}", method = RequestMethod.GET,produces="application/json;charset=utf-8")
	public Map<String,Object> check_is_added_to_mylist_json(@PathVariable int ch_id,Model model) {
		Map<String,Object> data=new HashMap<>();
		Object securityContext=SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(securityContext == null || "anonymousUser".equals(securityContext)) {
			data.put("success", false);
			data.put("bAdded", false);
			return data;
		}
		
		MemberVO memberVO=(MemberVO) securityContext;
		
		
		MusicChMylistVO musicChMylistVO=new MusicChMylistVO();
		musicChMylistVO.setMusic_ch_id(ch_id);
		musicChMylistVO.setMember_id(memberVO.getId());
		musicChMylistVO=teraDao.getChInMyList(musicChMylistVO);
		if(musicChMylistVO==null) {
			data.put("success", true);
			data.put("bAdded", false);
		}else {
			data.put("success", true);
			data.put("bAdded", true);
		}
			
		
		return data;
	}


}
