package com.biz.tour.service.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.tour.dao.ddl.DDL_Dao;
import com.biz.tour.dao.member.AuthoritiesDao;
import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.dao.sql.CreateTableSql;
import com.biz.tour.domain.member.AuthorityVO;
import com.biz.tour.domain.member.MemberVO;
import com.biz.tour.service.util.PbeEncryptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImp implements MemberService {
	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberDao memDao;
	private final AuthoritiesDao authDao;
	private final DDL_Dao ddlDao;

	public MemberServiceImp(BCryptPasswordEncoder passwordEncoder, MemberDao memDao,
			AuthoritiesDao authDao, DDL_Dao ddlDao) {
		this.ddlDao = ddlDao;
		this.passwordEncoder = passwordEncoder;
		this.memDao = memDao;
		this.authDao = authDao;
		ddlDao.create_table(CreateTableSql.create_member_table);
		ddlDao.create_table(CreateTableSql.create_auth_table);
	}

	public MemberVO findByUName(String u_name) {
		return memDao.findByUName(u_name);
	}

	@Transactional
	public int insert(MemberVO memberVO) {
		log.debug("## memberservice imp insert activated");
		String encPass = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encPass);
		memberVO.setEnabled(true);
		int ret = memDao.insert(memberVO);
		List<AuthorityVO> authList = new ArrayList<AuthorityVO>();
		authList.add(AuthorityVO.builder().username(memberVO.getUsername()).authority("USER").build());
		authList.add(AuthorityVO.builder().username(memberVO.getUsername()).authority("ROLE_USER").build());
		ret = authDao.insert(authList);
		return ret;
	}

	public int delete(String u_name) {
		return memDao.delete(u_name);
	}

	public MemberVO checkLogin(MemberVO inputtedMemberVO) {
		// TODO Auto-generated method stub
		MemberVO existingMemberVO = memDao.findByUName(inputtedMemberVO.getUsername());
		if (existingMemberVO == null)
			return null;
		if (!passwordEncoder.matches(inputtedMemberVO.getPassword(), existingMemberVO.getPassword())) {
			return null;
		}

		return existingMemberVO;
	}

	@Transactional
	public boolean email_token_ok(String username, String secret_key, String secret_value) {
		boolean bKey = PbeEncryptor.getDecrypt(secret_key).equals(secret_value);
		log.debug("## bkey: " + bKey);
		if (bKey) {
			String strUsername = PbeEncryptor.getDecrypt(username);
			MemberVO memberVO = memDao.findByUName(strUsername);
			memberVO.setEnabled(true);
			memDao.update(memberVO);
		}
		return bKey;
	}

	/**
	 * @since 2020-04-21 회원정보를 받아서 DB에 저장하고 회원정보를 활성화 할수 있도록 하기위해 인증정보를 생성한후
	 *        controller로 return
	 */
	@Transactional
	public String insert_getToken(MemberVO memberVO) {
		// DB에 저장
		memberVO.setEnabled(false);
		String encPassword = passwordEncoder.encode(memberVO.getPassword());
		memberVO.setPassword(encPassword);
		int ret = memDao.insert(memberVO);

		List<AuthorityVO> authList = new ArrayList<AuthorityVO>();
		authList.add(AuthorityVO.builder().username(memberVO.getUsername()).authority("USER").build());
		authList.add(AuthorityVO.builder().username(memberVO.getUsername()).authority("ROLE_USER").build());
		ret = authDao.insert(authList);

		String email_token = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		String enc_email_token = PbeEncryptor.getEncrypt(email_token);
		// email보내기
		return enc_email_token;
	}

	@Override
	public int update(MemberVO memberVO) {
		// TODO Auto-generated method stub
		// 업뎃 전 olduserVO 를 수정후 VO 내용으로 세팅해주고
		Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
		MemberVO oldMemberVO = (MemberVO) oldAuth.getPrincipal();
		oldMemberVO.setEmail(memberVO.getEmail());
		oldMemberVO.setPhone(memberVO.getPhone());
		oldMemberVO.setAddress(memberVO.getAddress());
		oldMemberVO.setPoint(memberVO.getPoint());
		oldMemberVO.setU_date(memberVO.getU_date());
		oldMemberVO.setProfile_pic(memberVO.getProfile_pic());
		oldMemberVO.setEnabled(memberVO.isEnabled());
		oldMemberVO.setAuthorities(getAuthorities(memberVO.getUsername()));
		// --- oldMemberVO가 새로운 유저 정보로 업뎃 됬음

		int ret=memDao.update(memberVO);
		
		if (ret > 0) {

			// 회원정보 update후, olduservo를 인자로 줘서 Principal에 담겨있는 정보도 새로 갱신
			// --- oldMemberVO가 새로운 유저 정보로 업뎃 됬음
			Authentication newAuth = new UsernamePasswordAuthenticationToken(oldMemberVO, oldAuth.getCredentials(),
					oldAuth.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
		return ret;
	}

	@Override
	public MemberVO findByUsernameNemail(String username, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int resetPassword(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int changePassword(String inputtedPrevPass, String inputtedNewPass) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int raisePoint(@Valid MemberVO memberVO) {
		// 년 월 일 변수 생성
		SimpleDateFormat curDate = new SimpleDateFormat("yyyy-MM-dd");

		// 현재 날짜 데이터 생성 후 date 변수에 주입
		Date date = new Date();
		log.debug("date : " + date);

		// strcurDate 변수에 년월일 방식으로 현재 날짜 데이터를 주입
		String strcurDate = curDate.format(date);

		/*
		 * 현재 날짜와 u_date(마지막 로그인 날짜)가 틀리면 포인트 +1 같다면 오늘 로그인 했었단 뜻이니 포인트 중복 방지하기 위함
		 */
		if (!strcurDate.equals(memberVO.getU_date())) {
			log.debug("현재 포인트 : " + memberVO.getPoint());// 테스트용 로그
			int intPoint = memberVO.getPoint();
			log.debug("intPoint : " + intPoint);// 테스트용 로그
			// null이면 회원가입 후 최초 로그인
			if (memberVO.getU_date() == null) {
				intPoint = intPoint + 100;
				memberVO.setPoint(intPoint);
				log.debug("최초 로그인 포인트 + 100  : " + memberVO.getPoint());// 테스트용 로그

				// VO에 strcurDate 셋팅
				memberVO.setU_date(strcurDate);

				// u_date,point 칼럼만 업데이트(VO)
				// memDao.date_update(memberVO);
				update(memberVO);

				return 0;
			}
			// 포인트 + 1 해서 VO에 point 칼럼에 셋팅
			intPoint = intPoint + 1;
			memberVO.setPoint(intPoint);
			log.debug("일일 로그인 포인트 + 1 : " + memberVO.getPoint());// 테스트용 로그

			// VO에 strcurDate 셋팅
			memberVO.setU_date(strcurDate);

			// u_date,point 칼럼만 업데이트(VO)
			// memDao.date_update(memberVO);
			update(memberVO);
			return 0;
		}

		// VO에 strcurDate 셋팅
		memberVO.setU_date(strcurDate);

		// u_date,point 칼럼만 업데이트(VO)
		memDao.date_update(memberVO);

		return 0;
	}

	@Override
	public String findId_getToken(MemberVO memberVO) {

		// 인증키 값 생성
		String email_token = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		log.debug("EMAIL-TOKEN : " + email_token);

		// 인증키 값 암호화
		String enc_email_token = PbeEncryptor.getEncrypt(email_token);

		// 암호화되지 않은 UUID 인증키 값 메일 보내기

		// 암호화 된 인증키 값 리턴
		return enc_email_token;
	}

	// ID찾기,비번 재설정 전에 이메일 인증키 값 검증 메서드
	@Override
	public boolean findId_email_token_ok(String email, String secret_key, String secret_value) {

		boolean bKey = PbeEncryptor.getDecrypt(secret_key).equals(secret_value);

//		String decrypt_seckey = PbeEncryptor.getDecrypt(secret_key);
//		
//		log.debug("인증키  key : " + secret_key);
//		log.debug("인증키 디크립트 key : " + decrypt_seckey);
//		log.debug("인증키 value : " + secret_value);

		if (bKey) {
			log.debug("이메일  : " + email);

			MemberVO memberVO = memDao.findByUserEmail(email);

			memberVO.setEnabled(true);
//			update(memberVO);
			memDao.update(memberVO);
			return bKey;
		}

//		return bKey;
		return false;
	}

	/*
	 * ID찾기,비번 재설정에서 이메일 인증을 완료하고
	 * id와 비번재설정을 위한 메서드 email로 db를
	 * 검색해서 vo에 담은 다음 비번은 널로 재설정 후 리턴
	 */
	@Override
	public MemberVO findByIdresetpass(MemberVO memberVO) {

		MemberVO re_memberVO = memDao.findByUserEmail(memberVO.getEmail());

		re_memberVO.setPassword(null);

		return re_memberVO;
	}

	@Override
	public int re_member_join(MemberVO memberVO) {

		log.debug("암호화 하기 전 비번 : " + memberVO.getPassword());
		// 변경할 비밀번호를 암호화 해서 encPassword에 셋팅
		String encPassword = passwordEncoder.encode(memberVO.getPassword());
		// memberVO에 암호화한 encPassword를 셋팅
		memberVO.setPassword(encPassword);
		log.debug("암호화 후 비번 : " + memberVO.getPassword());
		// memberVO enabled를 true로 변경 후 셋팅(혹시 false이거나 할 경우를 위해 이부분은 확인 필요)
		memberVO.setEnabled(true);
		// memDao 에 업데이트 후 
		int ret = memDao.re_update(memberVO);
		// 컨트롤러로 리턴
		return ret;
	}

	@Override
	public MemberVO findById(long id) {
		// TODO Auto-generated method stub
		return memDao.findById(id);
	}
	
	private Collection<GrantedAuthority> getAuthorities(String username){
		List<AuthorityVO> authList=authDao.findByUserName(username);
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		for(AuthorityVO vo:authList) {// db에 저장된 권한 목록들을
			if(!vo.getAuthority().trim().isEmpty()) {
				//spring security용 auth list로 복사
				SimpleGrantedAuthority sAuth=new SimpleGrantedAuthority(vo.getAuthority());
				authorities.add(sAuth);
			}
		}
		return authorities;
	}
}