package com.biz.tour.service.fileupload;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.domain.MusicChVO;

public interface FileUploadToServerService {

	/*
	 * 원래 파일이름을 UUID 부착된 파일이름으로 변경하고 변경된 이름으로 서버의 filePath에 저장하고 변경된 파일이름을 return
	 */
	public String filesUp(MultipartHttpServletRequest uploaded_files, String whichTable,int fk);

	public String picfileUp(MultipartFile uploadedFile,int fk);

	public String videofileUp(MultipartFile uploadedFile,int fk) ;
	
	public boolean deleteMusicChFiles(MusicChVO musicChVO);
}
