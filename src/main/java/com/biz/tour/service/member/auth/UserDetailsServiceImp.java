package com.biz.tour.service.member.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biz.tour.dao.member.AuthoritiesDao;
import com.biz.tour.dao.member.MemberDao;
import com.biz.tour.domain.member.AuthorityVO;
import com.biz.tour.domain.member.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImp implements UserDetailsService{
	@Autowired
	private AuthoritiesDao authDao;
	@Autowired
	private MemberDao memberDao;
	
	/*
	 * DB로부터 데이터를 불러와서 UserdeailsVO에 데이터를 복사하여 연동하는 코드가 작성될곳 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//spring security가 사용할 detailVO 선언
		MemberVO memberVO=memberDao.findByUName(username);
		if(memberVO==null) {
			throw new UsernameNotFoundException("User Name이 없습니다");
		}
		memberVO.setAccountNonExpired(true);
		memberVO.setAccountNonLocked(true);
		memberVO.setCredentialsNonExpired(true);
		
		//userDetails 안의 authority property는 이상한 객체로 되있어서
		//getAuthorities()라는 보일러 코드로 한번 전처리 해주고 값 셋팅
		memberVO.setAuthorities(this.getAuthorities(username));
		return memberVO;
	}
	
	/**
	 * authorities 테이블에서 권한 리스트를 가져오기
	 * 요건 보일러코드
	 */
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
