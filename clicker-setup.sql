create database if not exists clicker;
use clicker;
drop table if exists responses;
create table responses(username varchar(30), phone int, questionNo int, choice varchar(1), comment varchar(50));
