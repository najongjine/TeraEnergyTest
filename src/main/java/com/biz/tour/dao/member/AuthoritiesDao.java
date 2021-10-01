package com.biz.tour.dao.member;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.tour.domain.member.AuthorityVO;

public interface AuthoritiesDao {
	@Select("select * from tbl_authorities where username=#{username}")
	List<AuthorityVO> findByUserName(String username);

	int insert(List<AuthorityVO> authList);

	@Delete("delete from tbl_authorities where username=#{username}")
	int delete(String username);
}
