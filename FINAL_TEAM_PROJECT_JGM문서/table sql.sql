
use emsDB;

CREATE TABLE tbl_marineanimal(

id bigint PRIMARY KEY auto_increment,
sciKr VARCHAR(50),
phylgntClas VARCHAR(100),
chrtr VARCHAR(2000),
latD int,
latM int,
latS int,
lonD int,
lonM int,
lonS int
);



CREATE TABLE tbl_naverapi(

id bigint PRIMARY KEY auto_increment,
title VARCHAR(500),
link VARCHAR(1000),
description VARCHAR(2000),
thumbnail VARCHAR(1000)

)







