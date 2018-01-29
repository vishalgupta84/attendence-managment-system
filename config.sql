	create database if not exists studentInfo;
	use studentInfo;
	drop table if exists attendenceInfo;
	create table  attendenceInfo(	
		id int not null ,
		name varchar(255) not null,
		roll varchar(255) not null,
		seat varchar(255) not null,
		courseID varchar(255) not null,
		CONSTRAINT info primary key(roll,courseID,seat)
	);
