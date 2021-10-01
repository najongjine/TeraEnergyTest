use emsdb;

drop table tbl_userfish_water;
drop table tbl_userfish_sea;
drop table tbl_userfish_water_comment;
drop table tbl_userfish_sea_comment;
drop table tbl_userfish_water_pics;
drop table tbl_userfish_sea_pics;
drop table tbl_members;

create table tbl_userfish_water(
	uf_id bigint AUTO_INCREMENT	PRIMARY KEY,
    uf_username nVARCHAR(50),
    uf_title nVARCHAR(150),
    uf_date nVARCHAR(50),
    uf_addr1 nVARCHAR(150),
    uf_addr2 nVARCHAR(150),
    uf_text nVARCHAR(2000)
);
create table tbl_userfish_sea(
	uf_id bigint AUTO_INCREMENT	PRIMARY KEY,
    uf_username nVARCHAR(50),
    uf_title nVARCHAR(150),
    uf_date nVARCHAR(50),
    uf_addr1 nVARCHAR(150),
    uf_addr2 nVARCHAR(150),
    uf_text nVARCHAR(2000)
);

create table tbl_userfish_water_comment(
	ufc_id bigint AUTO_INCREMENT	PRIMARY KEY,
    ufc_pid bigint,
    ufc_fk bigint,
    ufc_username nVARCHAR(50),
    ufc_title nVARCHAR(150),
    ufc_date nVARCHAR(50),
    ufc_text nVARCHAR(2000)
);

create table tbl_userfish_sea_comment(
	ufc_id bigint AUTO_INCREMENT	PRIMARY KEY,
    ufc_pid bigint,
    ufc_fk bigint,
    ufc_username nVARCHAR(50),
    ufc_title nVARCHAR(150),
    ufc_date nVARCHAR(50),
    ufc_text nVARCHAR(2000)
);

create table tbl_userfish_water_pics(
	ufp_id bigint AUTO_INCREMENT	PRIMARY KEY,
    ufp_fk bigint(50),
    ufp_originalFName nVARCHAR(50),
    ufp_uploadedFName nVARCHAR(150)
);
create table tbl_userfish_sea_pics(
	ufp_id bigint AUTO_INCREMENT	PRIMARY KEY,
    ufp_fk bigint(50),
    ufp_originalFName nVARCHAR(50),
    ufp_uploadedFName nVARCHAR(150)
);

create table tbl_members(
	id bigint AUTO_INCREMENT	PRIMARY KEY,
    username varchar(50) unique,
    password VARCHAR(150),
    enabled boolean default false,
    email VARCHAR(50),
    phone VARCHAR(15),
    address VARCHAR(125),
    point INT default 0,
    profile_pic VARCHAR(125),
    u_date VARCHAR(10) //마지막 로그인 날짜
);


ALTER TABLE tbl_userfish_water_comment
ADD CONSTRAINT fk_comment
    FOREIGN KEY (ufc_fk)
    REFERENCES tbl_userfish_water (uf_id)
    ON DELETE CASCADE;
ALTER TABLE tbl_userfish_sea_comment
ADD CONSTRAINT fk_comment_sea
    FOREIGN KEY (ufc_fk)
    REFERENCES tbl_userfish_water (uf_id)
    ON DELETE CASCADE;

ALTER TABLE tbl_userfish_water_pics
ADD CONSTRAINT fk_pic_water
    FOREIGN KEY (ufp_fk)
    REFERENCES tbl_userfish_water (uf_id)
    ON DELETE CASCADE;
ALTER TABLE tbl_userfish_sea_pics
ADD CONSTRAINT fk_pic_sea
    FOREIGN KEY (ufp_fk)
    REFERENCES tbl_userfish_water (uf_id)
    ON DELETE CASCADE;