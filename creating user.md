-- mysql에서는 사용자 등록할때 접속경로를 설정`
-- iouser는 localhost에서만 접속할수 있다

create user 'iouser'@'localhost' identified by '1234';
grant all privileges on *.* TO 'iouser'@'localhost';


-- iouser는 모든 곳에서 원격, 로컬로 접속할수 있다.
create user 'iouser'@'%' identified by '1234';
grant all privileges on *.* TO 'iouser'@'%';


-- 2020-01-21. 내 컴터에서 접속 가능하게.
create user 'ems'@'localhost' identified by 'ems';
grant all privileges on *.* TO 'ems'@'localhost';

-- % 는 모든 곳에서 원격, 로컬로 접속할수 있다.
create user 'ems'@'%' identified by 'ems';
grant all privileges on *.* TO 'ems'@'%';

-- schema DB 생성
create database emsDB;

use emsDB;
show tables;