package com.biz.tour.dao.sql;

public class CreateTableSql {
	public static final String create_member_table =" create table if not exists tbl_members( " + 
			" id bigint AUTO_INCREMENT	PRIMARY KEY, " + 
			" username varchar(50) unique, " + 
			" password VARCHAR(150), " + 
			" enabled boolean default false, " +
			" email VARCHAR(50), " + 
			" phone varchar(20), " + 
			" address varchar(125), " + 
			" point INT default 0, "+
			" profile_pic VARCHAR(125), "+
			" u_date VARCHAR(10) "+
			" ) ";
	public static final String create_auth_table=" create table if not exists tbl_authorities( " + 
			" id bigint  primary key auto_increment, " + 
			" username varchar(50), " + 
			" authority varchar(50) " + 
			" ) ";
}
