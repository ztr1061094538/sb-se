/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.63
Source Server Version : 50724
Source Host           : 192.168.1.63:3306
Source Database       : epp_sysbase

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-26 09:40:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(64) NOT NULL,
  `NAME` varchar(64) NOT NULL,
  `ICONCLS` varchar(64) DEFAULT NULL,
  `PARENTID` int(11) NOT NULL,
  `ENABLED` tinyint(1) NOT NULL DEFAULT '1',
  `SORTID` int(64) NOT NULL DEFAULT '0',
  `TYPE` int(1) unsigned zerofill NOT NULL COMMENT '0 is menu  1 is button',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `URL` (`URL`)
) ENGINE=InnoDB AUTO_INCREMENT=631 DEFAULT CHARSET=utf8;
