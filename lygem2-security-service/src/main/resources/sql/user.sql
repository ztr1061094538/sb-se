/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.63
Source Server Version : 50724
Source Host           : 192.168.1.63:3306
Source Database       : epp_sysbase

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-26 09:38:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `NAME` varchar(32) NOT NULL COMMENT '姓名',
  `PHONE` char(11) NOT NULL COMMENT '手机号码',
  `TELEPHONE` varchar(16) DEFAULT NULL COMMENT '住宅电话',
  `ADDRESS` varchar(64) DEFAULT NULL COMMENT '联系地址',
  `ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `USERNAME` varchar(255) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(255) NOT NULL COMMENT '密码',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `SEX` int(1) DEFAULT NULL COMMENT '性别',
  `PROVINCE` varchar(10) DEFAULT NULL COMMENT '省',
  `CITY` varchar(10) DEFAULT NULL COMMENT '城市',
  `DISTRICT` varchar(10) DEFAULT NULL COMMENT '区',
  `ENTERPRISE_ID` int(11) NOT NULL COMMENT '企业id对应company表；机构id对应organizational表；服务商id对应service_provider',
  `user_type` int(1) NOT NULL COMMENT '0：系统管理员；1：政府账号；2：企业账号；3：服务商账号；',
  `area_id` int(20) DEFAULT NULL,
  `audit_state` tinyint(1) DEFAULT '0' COMMENT '审核状态；0：待审核；1：审核通过；2：审核不通过；',
  `audit_desc` varchar(50) DEFAULT '' COMMENT '审核意见',
  `register_time` bigint(20) DEFAULT NULL COMMENT '注册时间yyyyMMddHHmmss',
  `profile_photo` varchar(100) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_phone` (`PHONE`) USING BTREE,
  UNIQUE KEY `unique_username` (`USERNAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=476 DEFAULT CHARSET=utf8 COMMENT='机构表';
