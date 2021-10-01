package com.biz.tour.service.member.auth;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.domain.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthProviderImpl implements AuthenticationProvider {
	/*
	 * 서비스 중에서 @service (" " ) 안에 userDetailsService 라고 적혀져있는 서비스를 가져와라
	 */
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDService;

	@Autowired
	private MemberDao memDao;

	/*
	 * spring sec에 bean으로 등록되어있음
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * spring sec에서 customize 수행하여 login을 세세히 제어하고자 할때 코드를 작성해야하는 중요한 method
	 * 
	 * login을 하고나면 제일먼저 여기로 옴
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/*
		 * authentication으로부터 로그인 폼에서 보낸 username과 password를 추출
		 */
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		// service->dao를 통해서 DB로부터 사용자 정보 가져오기
		MemberVO memberVO = (MemberVO) userDService.loadUserByUsername(username);
		if (!passwordEncoder.matches(password.trim(), memberVO.getPassword().trim())) {
			throw new BadCredentialsException("비밀번호 오류");
		}
		if (!memberVO.isEnabled()) {
			throw new BadCredentialsException(username + ": 접근권한 없음");
		}

		return new UsernamePasswordAuthenticationToken(memberVO, null, memberVO.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}
