create database if not exists clicker;
use clicker;
drop table if exists responses;
create table responses(username varchar(30), phone int, questionNo int, choice varchar(1), comment varchar(50));
create table qbank(questionNo int, question varchar(100), optA varchar(50), optB varchar(50), optC varchar(50), optD varchar(50), isOpen varchar(1));
