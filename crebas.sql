/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/5/23 11:03:08                           */
/*==============================================================*/


drop table if exists User;

drop table if exists contract;

drop table if exists contract_attachment;

drop table if exists contract_process;

drop table if exists customer;

drop table if exists log;

drop table if exists rights;

drop table if exists role_right;

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   userId               int not null AUTO_INCREMENT,
   userName             varchar(20),
   userDescription      varchar(20),
   password             varchar(40),
   email                varchar(20),
   primary key (userId)
);

/*==============================================================*/
/* Table: contract                                              */
/*==============================================================*/
create table contract
(
   contractId           int not null AUTO_INCREMENT,
   contractName         varchar(50),
   customerId           int,
   beginTime            date,
   endTime              date,
   content              text,
   drafterId            int,
   type                 int,
   failureTimes         int,
	 primary key (contractId)
);

/*==============================================================*/
/* Table: contract_attachment                                   */
/*==============================================================*/
create table contract_attachment
(
   contractId           int,
   fileName             varchar(100),
   path                 varchar(100),
   type                 varchar(20),
   uploadTime           datetime
);

/*==============================================================*/
/* Table: contract_process                                      */
/*==============================================================*/
create table contract_process
(
   contractId           int not null,
   userId               int not null,
   type                 int,
   state                int,
   content              text,
   time                 datetime,
   primary key (contractId,userId)
);

/*==============================================================*/
/* Table: customer                                              */
/*==============================================================*/
create table customer
(
   customerId           int not null AUTO_INCREMENT,
   customerName         varchar(40),
   address              varchar(100),
   phone                varchar(20),
   code                 varchar(10),
   bank                 varchar(50),
   account              varchar(50),
	 primary key(customerId)
);

/*==============================================================*/
/* Table: log                                                   */
/*==============================================================*/
create table log
(
   userId               int,
   content              text,
   time                 datetime
);

/*==============================================================*/
/* Table: rights                                                */
/*==============================================================*/
create table rights
(
   rightId              int not null,
   rightName            varchar(40),
   description          varchar(100),
   functions            varchar(500),
   primary key (rightId)
);

/*==============================================================*/
/* Table: role_right                                            */
/*==============================================================*/
create table role_right
(
   userId               int not null,
   rightId              int not null,
   description          varchar(100),
   primary key (userId,rightId)
);