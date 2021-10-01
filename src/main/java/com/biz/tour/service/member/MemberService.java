package com.biz.tour.service.member;

import javax.validation.Valid;

import com.biz.tour.domain.member.MemberVO;

public interface MemberService {
	
	public MemberVO findById(long  id);
	public MemberVO findByUName(String u_name);
	public int insert(MemberVO MemberVO);
	public int delete(String u_name);
	public MemberVO checkLogin(MemberVO inputtedMemberVO);
	
	public boolean email_token_ok(String username, String secret_key, String secret_value);
	
	/**
	 *@since 2020-04-21
	 *회원정보를 받아서 DB에 저장하고
	 *회원정보를 활성화 할수 있도록 하기위해 인증정보를 생성한후 controller로 return
	 */
	public String insert_getToken(MemberVO memberVO);
	
	public int update(MemberVO memberVO);
	
	public MemberVO findByUsernameNemail(String username, String email);
	
	//비밀번호 1111 로 만듬과 동시에 유저한테 메일도 같이 보내줘야함
	public int resetPassword(MemberVO memberVO);
	
	public int changePassword(String inputtedPrevPass, String inputtedNewPass);
	
	//로그인시 포인트 적립. 서브테이블에 username 있으면 포인트 적립 안함
	public int raisePoint(@Valid MemberVO memberVO);
	
	public MemberVO findByIdresetpass(MemberVO memberVO);
	
	public String findId_getToken(MemberVO memberVO);
	public boolean findId_email_token_ok(String email, String secret_key, String secret_value);
	public int re_member_join(MemberVO memberVO);
}
