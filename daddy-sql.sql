-----修改mysql的root密码：
use mysql;
UPDATE user SET Password = PASSWORD('123') WHERE user = 'root';
FLUSH PRIVILEGES;

-----允许root账户从任何主机连接到本mysql
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123' WITH GRANT OPTION;
FLUSH   PRIVILEGES;

----------涵涵
CREATE database if not exists app_hanhan CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use app_hanhan;

----------合法美女
CREATE database if not exists app_beauty CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use app_beauty;

----------违法美女
CREATE database if not exists app_sexy CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use app_sexy;

----------爬虫开放接口
CREATE database if not exists spider CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
use spider;

show variables like "%char%";
set names utf8;
SET character_set_client='utf8';
SET character_set_connection='utf8';
SET character_set_results='utf8';
SET character_set_server='utf8';


create table if not exists users(
	id int primary key auto_increment,
	username varchar(200) default '',
	password varchar(200) default '0',
	sid varchar(200) default '',
	company varchar(200) default '',
	realname varchar(200) default '',
	extra1 varchar(200) default '',
	extra2 varchar(200) default '',
	extra3 varchar(200) default '',
	extra4 varchar(200) default ''
)engine=innodb default charset=utf8 auto_increment=1;


create table if not exists admins(
	id int primary key auto_increment,
	username varchar(200) default '',
	password varchar(200) default '0',
	sid varchar(200) default '',
	company varchar(200) default '',
	realname varchar(200) default '',
	extra1 varchar(200) default '',
	extra2 varchar(200) default '',
	extra3 varchar(200) default '',
	extra4 varchar(200) default ''
)engine=innodb default charset=utf8 auto_increment=1;

insert into admins (username, password, sid, company, realname) values('jack-daddy','99990529138','112233445544332211','最高管理员','Jack');

insert into admins (username, password, sid, company, realname) values('admin1','99990529138','112233445544332211','普通管理员','Jack');
insert into admins (username, password, sid, company, realname) values('admin2','99990529138','112233445544332211','普通管理员','Jack');

create table if not exists top(
	id int primary key auto_increment,
	title varchar(200) default '',
	coverImages varchar(2000) default '',
	shortIntro varchar(2000) default '',
	readCount int default 0,
	createAt varchar(200) default '',
	topUrl varchar(500) default '',
	authorName varchar(200) default '',
	authorUrl varchar(200) default '',
	authorHeadImg varchar(200) default '',
	tags varchar(200) default ''
)engine=innodb default charset=utf8 auto_increment=1;


rds72u8iow6ff1cng0b7.mysql.rds.aliyuncs.com
