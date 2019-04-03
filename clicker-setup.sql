create database if not exists clicker;
use clicker;
drop table if exists responses;
create table responses(questionNo int, value varchar(1));
insert into  responses (questionNo, choice) values (8, '?');