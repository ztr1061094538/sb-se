/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.63
Source Server Version : 50724
Source Host           : 192.168.1.63:3306
Source Database       : epp_sysbase

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-26 09:39:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_of_role
-- ----------------------------
DROP TABLE IF EXISTS `user_of_role`;
CREATE TABLE `user_of_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERID` int(11) DEFAULT NULL COMMENT '用户ID',
  `ROLEID` int(11) DEFAULT NULL COMMENT '权限ID ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=728 DEFAULT CHARSET=utf8;
