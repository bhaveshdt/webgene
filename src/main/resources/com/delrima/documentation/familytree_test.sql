

DROP SCHEMA IF EXISTS webgene_test;
CREATE SCHEMA webgene_test;
USE webgene_test;

DROP TABLE IF EXISTS webgene_test.school;
DROP TABLE IF EXISTS webgene_test.member;
DROP TABLE IF EXISTS webgene_test.contact;
DROP TABLE IF EXISTS webgene_test.appuserid_ctl;
DROP TABLE IF EXISTS webgene_test.appuser;
DROP TABLE IF EXISTS webgene_test.role;

CREATE TABLE webgene_test.contact (
  id int(11) NOT NULL AUTO_INCREMENT,
  NAME char(50) default NULL,
  STREET1 char(100) default NULL,
  STREET2 char(100) default NULL,
  CITY char(50) default NULL,
  STATE char(50) default NULL,
  ZIP char(20) default NULL,
  COUNTRY char(50) default NULL,
  HOMEPHONE char(20) default NULL,
  MOBILEPHONE char(20) default NULL,
  WORKPHONE char(20) default NULL,
  FAX char(20) default NULL,
  EMAIL char(100) default NULL,
  IM char(100) default NULL,
  WEBSITE char(255) default NULL,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE webgene_test.member (
  ID int(11) NOT NULL AUTO_INCREMENT,
  IGNOREROW BOOL default FALSE,
  MLEFTID int(11) NULL,
  MRIGHTID int(11) NULL,
  FLEFTID int(11) NULL,
  FRIGHTID int(11) NULL,
  MOTHERID int(11) NOT NULL,
  FATHERID int(11) NOT NULL,
  SPOUSEID int(11) NOT NULL,
  CONTACTID int(11) NOT NULL,
  FIRSTNAME char(50) NOT NULL,
  MIDDLENAME char(50) default NULL,
  LASTNAME char(50) NOT NULL,
  MAIDENNAME char(50) default NULL,
  DOB datetime NULL,
  DOD datetime NULL,
  GENDER enum('M','F','U') NOT NULL,
  MARRIED enum('Y','N','U') NOT NULL,
  OCCUPATION char(100) NULL,
  MEMBERSINCE datetime NULL,
  PRIMARY KEY  (ID),
  KEY MOTHERID (MOTHERID),
  KEY FATHERID (FATHERID),
  KEY SPOUSEID (SPOUSEID),
  KEY CONTACTID (CONTACTID),
  CONSTRAINT member_ibfk_2 FOREIGN KEY (MOTHERID) REFERENCES webgene_test.member (ID),
  CONSTRAINT member_ibfk_3 FOREIGN KEY (FATHERID) REFERENCES webgene_test.member (ID),
  CONSTRAINT member_ibfk_4 FOREIGN KEY (SPOUSEID) REFERENCES webgene_test.member (ID),
  CONSTRAINT member_ibfk_5 FOREIGN KEY (CONTACTID) REFERENCES webgene_test.contact (ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into webgene_test.contact values (1, 'No Contact Info', '', '', '', '', '', '', '',  '', '', '', '', '', '');

insert into webgene_test.member values (1, 0, 1, 2, 1, 2, 1, 1, 1, 1, 'UNKNOWN', 'UNKNOWN', 'UNKNOWN', 'UNK', null, null, 'U', 'U', '', NOW());

select * from member;
select * from contact;
