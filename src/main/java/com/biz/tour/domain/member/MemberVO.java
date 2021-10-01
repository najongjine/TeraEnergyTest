package com.biz.tour.domain.member;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberVO implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	@NotBlank(message = "* 아이디를 입력해 주세요")
	private String username;
	
	@NotBlank(message = "* 비번을 입력해 주세요")
	private String password;
	
	private boolean enabled;
	
	private String email;
	private String phone;
	private String address;
	private int point;
	
	private String profile_pic;
	
	private String u_date;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	private Collection<? extends GrantedAuthority> authorities;
}
