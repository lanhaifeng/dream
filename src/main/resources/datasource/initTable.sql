--create database
create database baseframework default character set utf8 collate utf8_general_ci;
--create user
create user 'admin'@'%' identified by 'admin';
--grant privileges
grant select,insert,update,delete,create on baseframework.* to admin;
FLUSH PRIVILEGES;

--create table users;
CREATE TABLE users(
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--create table userRoles;
CREATE TABLE userRoles(
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) NOT NULL,
  `roleId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
);

--create table roles;
CREATE TABLE roles(
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roleName` varchar(100) DEFAULT NULL,
  `mark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--create table resources;
CREATE TABLE resources(
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `resourcePath` varchar(500) NOT NULL,
  `methodName` varchar(500) DEFAULT NULL,
  `className` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

--create table roleResources;
CREATE TABLE roleResources(
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roleId` int(10) NOT NULL,
  `resourceId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
);