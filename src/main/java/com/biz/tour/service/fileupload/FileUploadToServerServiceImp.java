package com.biz.tour.service.fileupload;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.tour.dao.TeraDao;
import com.biz.tour.domain.MusicChVO;
import com.biz.tour.domain.MusicChVideosVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadToServerServiceImp implements FileUploadToServerService {
	private final TeraDao teraDao;

	// servlet-context.xml에 설정된 파일 저장 경로 정보를 가져와서 사용하기
	private final String filePath;
	private final String picsPath;
	private final String videosPath;

	/*
	 * 원래 파일이름을 UUID 부착된 파일이름으로 변경하고 변경된 이름으로 서버의 filePath에 저장하고 변경된 파일이름을 return
	 */
	public String filesUp(MultipartHttpServletRequest uploaded_files, String whichTable, int fk) {
		// uploaded_files.getFiles("uploaded_files") 이부분은 jsp form input 에서 지정한 name과
		// 동일해야함
		for (MultipartFile file : uploaded_files.getFiles("uploaded_files")) {
			if (file.isEmpty())
				return null;
			fileUp(file, whichTable, fk);
		}
		return null;
	}

	public String fileUp(MultipartFile uploadedFile, String whichTable, int fk) {
		if (uploadedFile.isEmpty())
			return null;

		log.debug("## uploadedFile: " + uploadedFile.getOriginalFilename().toString());
		// upload할 filePath가 있는지 확인을 하고
		// 없으면 폴더를 생성
		File dir = new File(filePath);
		if (dir.exists()) {
			dir.mkdirs();
		}

		// 파일이름을 추출(그림.jpg)
		String originalFileName = uploadedFile.getOriginalFilename();
		Optional<String> fileExtension = Optional.ofNullable(originalFileName).filter(f -> f.contains("."))
				.map(f -> f.substring(originalFileName.lastIndexOf(".") + 1));
		;
		System.out.println("## splitOriginalFileNm.length: " + fileExtension.get());

		// UUID가 부착된 새로운 이름을 생성(UUID그림.jpg)
		String strUUID = UUID.randomUUID().toString();
		String UploadedFName = strUUID + "." + fileExtension.get();

		// filePath와 변경된 파일이름을 결합하여 empty 파일 객체를 생성
		File serverFile = new File(filePath, UploadedFName);

		// upFile을 serverFile 이름으로 복사 수행
		try {
			uploadedFile.transferTo(serverFile);

			// water 이면 wtarePics 테이블에 저장
			if (whichTable.equalsIgnoreCase("music_ch")) {
				MusicChVO musicChVO = new MusicChVO();
				musicChVO.setId(fk);
				musicChVO.setOriginal_pic(originalFileName);
				musicChVO.setUploaded_pic(UploadedFName);
				int ret = teraDao.updateMusicChPosterFile(musicChVO);
			} else if (whichTable.equalsIgnoreCase("videos")) {
				MusicChVideosVO videosVO = new MusicChVideosVO();
				MusicChVO musicChVO = new MusicChVO();
				musicChVO.setId(fk);
				musicChVO.setOriginal_filename(originalFileName);
				musicChVO.setUploaded_filename(UploadedFName);
				int ret = teraDao.updateMusicChVideoFile(musicChVO);
			}

			return UploadedFName;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} // 테이블에 이미지 저장

	public String picfileUp(MultipartFile uploadedFile, int fk) {
		if (uploadedFile.isEmpty())
			return null;

		// upload할 filePath가 있는지 확인을 하고
		// 없으면 폴더를 생성
		File dir = new File(picsPath);
		if (dir.exists()) {
			dir.mkdirs();
		}

		// 파일이름을 추출(그림.jpg)
		String originalFileName = uploadedFile.getOriginalFilename();

		// UUID가 부착된 새로운 이름을 생성(UUID그림.jpg)
		String strUUID = UUID.randomUUID().toString();
		String UploadedFName = strUUID + originalFileName;

		// filePath와 변경된 파일이름을 결합하여 empty 파일 객체를 생성
		File serverFile = new File(picsPath, UploadedFName);

		// upFile을 serverFile 이름으로 복사 수행
		try {
			uploadedFile.transferTo(serverFile);
			MusicChVO musicChVO = new MusicChVO();
			musicChVO.setId(fk);
			musicChVO.setOriginal_pic(originalFileName);
			musicChVO.setUploaded_pic(UploadedFName);
			int ret = teraDao.updateMusicChPosterFile(musicChVO);

			return UploadedFName;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String videofileUp(MultipartFile uploadedFile, int fk) {
		if (uploadedFile.isEmpty())
			return null;

		// upload할 filePath가 있는지 확인을 하고
		// 없으면 폴더를 생성
		File dir = new File(videosPath);
		if (dir.exists()) {
			dir.mkdirs();
		}

		// 파일이름을 추출(그림.jpg)
		String originalFileName = uploadedFile.getOriginalFilename();

		// UUID가 부착된 새로운 이름을 생성(UUID그림.jpg)
		String strUUID = UUID.randomUUID().toString();
		String UploadedFName = strUUID + originalFileName;

		// filePath와 변경된 파일이름을 결합하여 empty 파일 객체를 생성
		File serverFile = new File(videosPath, UploadedFName);

		// upFile을 serverFile 이름으로 복사 수행
		try {
			uploadedFile.transferTo(serverFile);
			MusicChVideosVO videosVO = new MusicChVideosVO();
			MusicChVO musicChVO = new MusicChVO();
			musicChVO.setId(fk);
			musicChVO.setOriginal_filename(originalFileName);
			musicChVO.setUploaded_filename(UploadedFName);
			int ret = teraDao.updateMusicChVideoFile(musicChVO);

			return UploadedFName;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteMusicChFiles(MusicChVO musicChVO) {

		File file = new File(picsPath+"/"+musicChVO.getUploaded_pic());
		if (file.exists()) {
			file.delete();
		} 
		file = new File(videosPath+"/"+musicChVO.getUploaded_filename());
		if (file.exists()) {
			file.delete();
		} 
		return true;
	}
}
