package com.biz.tour.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.DummyVO;
import com.biz.tour.service.fileupload.FileUploadToServerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes({ "dummyVO" })
public class TestController {
	private final FileUploadToServerService fService;
	

	@ModelAttribute("dummyVO")
	public DummyVO makeSearchVO() {
		DummyVO dummyVO = new DummyVO();
		return dummyVO;
	}

	@RequestMapping(value = "/testchart", method = RequestMethod.GET)
	public String testchart(Model model) {
		List<DummyVO> dummyList=new ArrayList<>();
		DummyVO dummyVO=new DummyVO();
		dummyVO.setNum(1);
		dummyVO.setStrDummy("1day");
		dummyList.add(dummyVO);
		dummyVO=new DummyVO();
		dummyVO.setNum(3);
		dummyVO.setStrDummy("2day");
		dummyList.add(dummyVO);
		dummyVO=new DummyVO();
		dummyVO.setNum(2);
		dummyVO.setStrDummy("3day");
		dummyList.add(dummyVO);
		log.debug("## dummylist: "+dummyList.toString());
		model.addAttribute("dummyList",dummyList);
		return "test/testchart";
	}
	
	// ,produces = "text/json;charset=UTF-8"
	

	/*
	 * 09:14:17.595 [http-nio-8080-exec-7] DEBUG
	 * com.biz.tour.controller.TestController - ## uploadedFile: 1.jpg 09:14:17.595
	 * [http-nio-8080-exec-7] DEBUG com.biz.tour.controller.TestController - ##
	 * uploadedFile: 2.jpg 09:14:17.595 [http-nio-8080-exec-7] DEBUG
	 * com.biz.tour.controller.TestController - ## uploadedFile2: Image Links.txt
	 */
	@ResponseBody
	@RequestMapping(value = "/testfileup", method = RequestMethod.POST, produces = "text/json;charset=utf-8")
	public String fileUp(DummyVO dummyVO, MultipartHttpServletRequest uploaded_files) {

		/* form 에서 입력한 <input type="file" multiple="multiple" name="uploaded_files"> */
		for (MultipartFile uploadedFile : uploaded_files.getFiles("uploaded_files")) {
			log.debug("## uploadedFile: " + uploadedFile.getOriginalFilename().toString());
		}
		/* form 에서 입력한 <input type="file" multiple="multiple" name="uploaded_files2"> */
		for (MultipartFile uploadedFile : uploaded_files.getFiles("uploaded_files2")) {
			log.debug("## uploadedFile2: " + uploadedFile.getOriginalFilename().toString());
		}

		return "test/testform";
	}

	/** @reference : http://aodis.egloos.com/5962812 
	 *  @modified  : whiteduck 
	 *  https://whiteduck.tistory.com/118
	 */
	@RequestMapping(value="/teststream/{content_id}", method= RequestMethod.GET) 
	public String getContentMediaVideo(@PathVariable("content_id")String content_id, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {


	  //progressbar 에서 특정 위치를 클릭하거나 해서 임의 위치의 내용을 요청할 수 있으므로
	  //파일의 임의의 위치에서 읽어오기 위해 RandomAccessFile 클래스를 사용한다.
	  //해당 파일이 없을 경우 예외 발생
	  
	  File file = new File("H:/crawled/[ORIGINAL] REFLECT - Gawr Gura/20210621121025ORIGINAL_REFLECT_-_Gawr_Gura.webm");
	  if( ! file.exists() ) throw new FileNotFoundException();
	  
	  RandomAccessFile randomFile = new RandomAccessFile(file, "r");

	  long rangeStart = 0; //요청 범위의 시작 위치
	  long rangeEnd = 0;  //요청 범위의 끝 위치
	  boolean isPart=false; //부분 요청일 경우 true, 전체 요청의 경우 false

	  //randomFile 을 클로즈 하기 위하여 try~finally 사용
	  try{
	      //동영상 파일 크기
	      long movieSize = randomFile.length(); 
	      //스트림 요청 범위, request의 헤더에서 range를 읽는다.
	      String range = request.getHeader("range");


	      //브라우저에 따라 range 형식이 다른데, 기본 형식은 "bytes={start}-{end}" 형식이다.
	      //range가 null이거나, reqStart가  0이고 end가 없을 경우 전체 요청이다.
	      //요청 범위를 구한다.
	      if(range!=null) {
	          //처리의 편의를 위해 요청 range에 end 값이 없을 경우 넣어줌
	          if(range.endsWith("-")){
	              range = range+(movieSize - 1); 
	          }
	          int idxm = range.trim().indexOf("-");                           //"-" 위치
	          rangeStart = Long.parseLong(range.substring(6,idxm));
	          rangeEnd = Long.parseLong(range.substring(idxm+1));
	          if(rangeStart > 0){
	              isPart = true;
	          }
	     }else{   //range가 null인 경우 동영상 전체 크기로 초기값을 넣어줌. 0부터 시작하므로 -1
	          rangeStart = 0;
	          rangeEnd = movieSize - 1;
	     }

	     //전송 파일 크기
	     long partSize = rangeEnd - rangeStart + 1;


	     //전송시작
	     response.reset();

	     //전체 요청일 경우 200, 부분 요청일 경우 206을 반환상태 코드로 지정
	     response.setStatus(isPart ? 206 : 200);

	     //mime type 지정
	     response.setContentType("video/mp4");

	     //전송 내용을 헤드에 넣어준다. 마지막에 파일 전체 크기를 넣는다.
	     response.setHeader("Content-Range", "bytes "+rangeStart+"-"+rangeEnd+"/"+movieSize);
	     response.setHeader("Accept-Ranges", "bytes");
	     response.setHeader("Content-Length", ""+partSize);

	     OutputStream out = response.getOutputStream();
	     //동영상 파일의 전송시작 위치 지정
	     randomFile.seek(rangeStart);

	     //파일 전송...  java io는 1회 전송 byte수가 int로 지정됨
	     //동영상 파일의 경우 int형으로는 처리 안되는 크기의 파일이 있으므로
	     //8kb로 잘라서 파일의 크기가 크더라도 문제가 되지 않도록 구현
	     int bufferSize = 8*1024;
	     byte[] buf = new byte[bufferSize];
	     do{
	         int block = partSize > bufferSize ? bufferSize : (int)partSize;
	         int len = randomFile.read(buf, 0, block);
	         out.write(buf, 0, len);
	         partSize -= block;
	     }while(partSize > 0);

	 }catch(IOException e){
	     //전송 중에 브라우저를 닫거나, 화면을 전환한 경우 종료해야 하므로 전송취소.
	     // progressBar를 클릭한 경우에는 클릭한 위치값으로 재요청이 들어오므로 전송 취소.

	 }finally{
	     randomFile.close();
	 }
	  return null;
	}

}
