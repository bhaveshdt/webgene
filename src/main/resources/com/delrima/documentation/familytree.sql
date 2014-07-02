

DROP SCHEMA IF EXISTS webgenedb;
CREATE SCHEMA webgenedb;
USE webgenedb;

DROP TABLE IF EXISTS webgenedb.school;
DROP TABLE IF EXISTS webgenedb.member;
DROP TABLE IF EXISTS webgenedb.contact;
DROP TABLE IF EXISTS webgenedb.appuserid_ctl;
DROP TABLE IF EXISTS webgenedb.appuser;
DROP TABLE IF EXISTS webgenedb.role;

CREATE TABLE webgenedb.contact (
  id int(11) NOT NULL AUTO_INCREMENT,
  NAME varchar(50) default NULL,
  STREET1 varchar(100) default NULL,
  STREET2 varchar(100) default NULL,
  CITY varchar(50) default NULL,
  STATE varchar(50) default NULL,
  ZIP varchar(20) default NULL,
  COUNTRY varchar(50) default NULL,
  HOMEPHONE varchar(20) default NULL,
  MOBILEPHONE varchar(20) default NULL,
  WORKPHONE varchar(20) default NULL,
  FAX varchar(20) default NULL,
  EMAIL varchar(100) default NULL,
  IM varchar(100) default NULL,
  WEBSITE varchar(255) default NULL,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE webgenedb.member (
  ID int(11) NOT NULL AUTO_INCREMENT,
  MOTHERID int(11) default NULL,
  FATHERID int(11) default NULL,
  SPOUSEID int(11) default NULL,
  CONTACTID int(11) default NULL,
  FIRSTNAME varchar(50) NOT NULL,
  MIDDLENAME varchar(50) default NULL,
  LASTNAME varchar(50) NOT NULL,
  MAIDENNAME varchar(50) default NULL,
  DOB datetime NULL,
  DOD datetime NULL,
  GENDER enum('M','F','U') NOT NULL,
  MARRIED enum('Y','N','U') NOT NULL,
  OCCUPATION varchar(100) NULL,
  MEMBERSINCE datetime NULL,
  PRIMARY KEY  (ID),
  KEY MOTHERID (MOTHERID),
  KEY FATHERID (FATHERID),
  KEY SPOUSEID (SPOUSEID),
  KEY CONTACTID (CONTACTID),
  CONSTRAINT member_ibfk_2 FOREIGN KEY (MOTHERID) REFERENCES webgenedb.member (ID),
  CONSTRAINT member_ibfk_3 FOREIGN KEY (FATHERID) REFERENCES webgenedb.member (ID),
  CONSTRAINT member_ibfk_4 FOREIGN KEY (SPOUSEID) REFERENCES webgenedb.member (ID),
  CONSTRAINT member_ibfk_5 FOREIGN KEY (CONTACTID) REFERENCES webgenedb.contact (ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into webgenedb.contact values (1, 'No Contact Info', '', '', '', '', '', '', '',  '', '', '', '', '', '');

insert into webgenedb.member (
	ID, MOTHERID, FATHERID, SPOUSEID, CONTACTID, FIRSTNAME, MIDDLENAME, LASTNAME, MAIDENNAME, DOB , DOD , GENDER, MARRIED, OCCUPATION, MEMBERSINCE
) values (1, 1, 1, 1, 1, 'UNKNOWN', 'UNKNOWN', 'UNKNOWN', 'UNKNOWN', null, null, 'U', 'U', '', NOW());

select * from member;
select * from contact;
