/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.63
Source Server Version : 50724
Source Host           : 192.168.1.63:3306
Source Database       : epp_sysbase

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-26 09:51:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_login_logs
-- ----------------------------
DROP TABLE IF EXISTS `user_login_logs`;
CREATE TABLE `user_login_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) NOT NULL,
  `org_id` int(11) NOT NULL COMMENT '机构id',
  `company_id` int(11) DEFAULT NULL COMMENT '企业id',
  `login_time` bigint(20) DEFAULT NULL COMMENT '登录时间，yyyyMMddHHmmss',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9719 DEFAULT CHARSET=utf8 COMMENT='用户登录日志';
