/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.63
Source Server Version : 50724
Source Host           : 192.168.1.63:3306
Source Database       : epp_sysbase

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-26 09:40:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role_of_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_of_menu`;
CREATE TABLE `role_of_menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENUID` int(11) DEFAULT NULL COMMENT 'MENU_ID',
  `ROLEID` int(11) DEFAULT NULL COMMENT '权限ID ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=121479 DEFAULT CHARSET=utf8;
