package com.biz.tour.dao.member;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.member.MemberVO;

public interface MemberDao {
	@Select("select * from tbl_members where id=#{id}")
	public MemberVO findById(long id);
	
	@Select("select * from tbl_members where username=#{username}")
	public MemberVO findByUName(String username);
	
	public int insert(MemberVO memberVO);
	
	@Delete("delete from tbl where username=#{username}")
	public int delete(String username);

	public int update(MemberVO userVO);

	public int pwupdate(MemberVO memberVO);

	public MemberVO findByUserEmail(String email);

	public int re_update(MemberVO memberVO);

	public int date_update(@Valid MemberVO memberVO);

}
