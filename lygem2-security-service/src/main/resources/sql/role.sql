/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.63
Source Server Version : 50724
Source Host           : 192.168.1.63:3306
Source Database       : epp_sysbase

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-26 09:40:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(64) DEFAULT NULL COMMENT '权限名称',
  `NAMEZH` varchar(64) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
