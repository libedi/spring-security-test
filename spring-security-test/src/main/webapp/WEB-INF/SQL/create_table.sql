CREATE TABLE MEMBERINFO(
	ID VARCHAR(50)	NOT NULL,
	PASSWORD VARCHAR(300) NOT NULL,
	NAME VARCHAR(30),
	PRIMARY KEY (ID)
);


CREATE TABLE AUTHORITY(
	AUTHORITY	VARCHAR(50) NOT NULL,
	AUTHORITY_NAME	VARCHAR(50)	NOT NULL,
	PRIMARY KEY (AUTHORITY)
);


CREATE TABLE MEMBER_AUTHORITY(
	ID VARCHAR(50)	NOT NULL,
	AUTHORITY	VARCHAR(50),
	PRIMARY KEY (ID)
);


CREATE TABLE GROUP_MEMBER(
	GROUP_ID VARCHAR(50)	NOT NULL,
	MEMBER_ID	VARCHAR(50),
	PRIMARY KEY (GROUP_ID)
);


CREATE TABLE GROUP_AUTHORITY(
	GROUP_ID VARCHAR(50)	NOT NULL,
	AUTHORITY	VARCHAR(50),
	PRIMARY KEY (GROUP_ID)
);


CREATE TABLE SECURED_RESOURCE(
	RESOURCE_ID	VARCHAR(10) NOT NULL,
	RESOURCE_NAME	VARCHAR(50),
	RESOURCE_PATTERN	VARCHAR(100),
	RESOURCE_TYPE	VARCHAR(10),
	SORT_ORDER	NUMERIC,
	PRIMARY KEY (RESOURCE_ID)
);


CREATE TABLE SECURED_RESOURCE_AUTHORITY(
	RESOURCE_ID VARCHAR(10)	NOT NULL,
	AUTHORITY	VARCHAR(50),
	PRIMARY KEY (RESOURCE_ID)
);

