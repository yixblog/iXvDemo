/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ixvsampledata

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2013-12-09 17:28:53
*/
create database ixvsampledata if not exists default charset=utf8;
use ixvsampledata;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `plate` varchar(10) NOT NULL COMMENT '车牌',
  `brand` varchar(30) NOT NULL COMMENT '品牌型号',
  `color` varchar(30) NOT NULL COMMENT '颜色',
  `enginecode` varchar(50) DEFAULT NULL COMMENT '发动机编号',
  `driver` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`pkid`),
  KEY `FK_DRIVER` (`driver`),
  CONSTRAINT `FK_DRIVER` FOREIGN KEY (`driver`) REFERENCES `person` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of car
-- ----------------------------
INSERT INTO `car` VALUES ('1', '苏ADG122', '大众桑塔纳3000', '白色', '233333', '1');

-- ----------------------------
-- Table structure for `hotel`
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `hotelname` varchar(50) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES ('1', '新河宾馆', '淮海路');
INSERT INTO `hotel` VALUES ('2', '中兴宾馆', '北京西路');

-- ----------------------------
-- Table structure for `internetcafe`
-- ----------------------------
DROP TABLE IF EXISTS `internetcafe`;
CREATE TABLE `internetcafe` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of internetcafe
-- ----------------------------
INSERT INTO `internetcafe` VALUES ('1', '速腾网吧', '沿江大道');
INSERT INTO `internetcafe` VALUES ('2', '战神网吧', '中央路');
INSERT INTO `internetcafe` VALUES ('3', '新网网吧', '湖南路');
INSERT INTO `internetcafe` VALUES ('4', '南都网吧', '湖南路');
INSERT INTO `internetcafe` VALUES ('5', '腾飞网吧', '大桥南路');
INSERT INTO `internetcafe` VALUES ('6', '飞翔网吧', '虎踞路');

-- ----------------------------
-- Table structure for `internetcafe_record`
-- ----------------------------
DROP TABLE IF EXISTS `internetcafe_record`;
CREATE TABLE `internetcafe_record` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `personid` int(10) unsigned NOT NULL,
  `cafeid` int(10) unsigned NOT NULL,
  `starttime` varchar(19) DEFAULT NULL,
  PRIMARY KEY (`pkid`),
  KEY `FK_CAFE_PERSON` (`personid`),
  KEY `FK_CAFE_CAFE` (`cafeid`),
  CONSTRAINT `FK_CAFE_CAFE` FOREIGN KEY (`cafeid`) REFERENCES `internetcafe` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_CAFE_PERSON` FOREIGN KEY (`personid`) REFERENCES `person` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of internetcafe_record
-- ----------------------------
INSERT INTO `internetcafe_record` VALUES ('1', '1', '1', '2013-10-11 10:14:00');
INSERT INTO `internetcafe_record` VALUES ('2', '1', '1', '2013-10-13 17:34:00');
INSERT INTO `internetcafe_record` VALUES ('3', '1', '3', '2013-10-14 18:00:00');
INSERT INTO `internetcafe_record` VALUES ('4', '1', '2', '2013-10-30 15:32:00');
INSERT INTO `internetcafe_record` VALUES ('5', '1', '1', '2013-10-31 18:14:00');
INSERT INTO `internetcafe_record` VALUES ('6', '1', '4', '2013-11-02 10:39:00');
INSERT INTO `internetcafe_record` VALUES ('7', '1', '2', '2013-11-05 14:33:00');
INSERT INTO `internetcafe_record` VALUES ('8', '1', '5', '2013-11-09 15:00:00');
INSERT INTO `internetcafe_record` VALUES ('9', '1', '5', '2013-11-10 11:30:00');
INSERT INTO `internetcafe_record` VALUES ('10', '1', '2', '2013-11-13 13:14:00');
INSERT INTO `internetcafe_record` VALUES ('11', '1', '6', '2013-11-18 19:40:00');
INSERT INTO `internetcafe_record` VALUES ('12', '1', '1', '2013-11-19 20:24:00');

-- ----------------------------
-- Table structure for `justice_education`
-- ----------------------------
DROP TABLE IF EXISTS `justice_education`;
CREATE TABLE `justice_education` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` varchar(10) NOT NULL,
  `location` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of justice_education
-- ----------------------------
INSERT INTO `justice_education` VALUES ('1', '2013-10-15', '浦口区');
INSERT INTO `justice_education` VALUES ('2', '2013-10-30', '浦口区');
INSERT INTO `justice_education` VALUES ('3', '2013-11-15', '浦口区');
INSERT INTO `justice_education` VALUES ('4', '2013-11-30', '浦口区');

-- ----------------------------
-- Table structure for `justice_education_attend`
-- ----------------------------
DROP TABLE IF EXISTS `justice_education_attend`;
CREATE TABLE `justice_education_attend` (
  `personid` int(10) unsigned NOT NULL,
  `educationid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`personid`,`educationid`),
  KEY `FK_EDU_EDU` (`educationid`),
  CONSTRAINT `FK_EDU_EDU` FOREIGN KEY (`educationid`) REFERENCES `justice_education` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_EDU_PERSON` FOREIGN KEY (`personid`) REFERENCES `person` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of justice_education_attend
-- ----------------------------
INSERT INTO `justice_education_attend` VALUES ('1', '1');
INSERT INTO `justice_education_attend` VALUES ('1', '2');
INSERT INTO `justice_education_attend` VALUES ('1', '3');
INSERT INTO `justice_education_attend` VALUES ('1', '4');

-- ----------------------------
-- Table structure for `justice_reform_plan`
-- ----------------------------
DROP TABLE IF EXISTS `justice_reform_plan`;
CREATE TABLE `justice_reform_plan` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `personid` int(10) unsigned NOT NULL,
  `adddate` varchar(10) NOT NULL,
  `analyse` varchar(200) DEFAULT NULL,
  `method` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`pkid`),
  KEY `FK_REFORM_PLAN_PERSON` (`personid`),
  CONSTRAINT `FK_REFORM_PLAN_PERSON` FOREIGN KEY (`personid`) REFERENCES `person` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of justice_reform_plan
-- ----------------------------
INSERT INTO `justice_reform_plan` VALUES ('1', '1', '2013-10-10', '分析信息asdfasdf', '矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案矫正方案');
INSERT INTO `justice_reform_plan` VALUES ('2', '1', '2013-10-30', '分析信息23333 asdfasdfasdf', '矫正方案2方案2方案2方案2方案2方案2方案2方案2方案2方案2方案2方案2方案2方案2方案2');

-- ----------------------------
-- Table structure for `justice_volunteer_work`
-- ----------------------------
DROP TABLE IF EXISTS `justice_volunteer_work`;
CREATE TABLE `justice_volunteer_work` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `personid` int(10) unsigned NOT NULL,
  `date` varchar(10) NOT NULL,
  `location` varchar(50) NOT NULL,
  `detail` varchar(50) NOT NULL,
  PRIMARY KEY (`pkid`),
  KEY `FK_VOL_PERSON` (`personid`),
  CONSTRAINT `FK_VOL_PERSON` FOREIGN KEY (`personid`) REFERENCES `person` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of justice_volunteer_work
-- ----------------------------
INSERT INTO `justice_volunteer_work` VALUES ('1', '1', '2013-10-12', 'XX小区', '打扫卫生');
INSERT INTO `justice_volunteer_work` VALUES ('2', '1', '2013-10-16', 'XX大街', '清理小广告');
INSERT INTO `justice_volunteer_work` VALUES ('3', '1', '2013-10-29', 'XZ公园', '清理垃圾');
INSERT INTO `justice_volunteer_work` VALUES ('4', '1', '2013-11-10', 'VV大街', '清理垃圾');

-- ----------------------------
-- Table structure for `liverecord`
-- ----------------------------
DROP TABLE IF EXISTS `liverecord`;
CREATE TABLE `liverecord` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `personid` int(10) unsigned NOT NULL,
  `hotelid` int(10) unsigned NOT NULL,
  `startdate` varchar(10) DEFAULT NULL,
  `enddate` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`pkid`),
  KEY `FK_LIVE_PERSON` (`personid`),
  KEY `FK_LIVE_HOTEL` (`hotelid`),
  CONSTRAINT `FK_LIVE_HOTEL` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_LIVE_PERSON` FOREIGN KEY (`personid`) REFERENCES `person` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of liverecord
-- ----------------------------
INSERT INTO `liverecord` VALUES ('1', '1', '1', '2013-10-12', '2013-10-13');
INSERT INTO `liverecord` VALUES ('2', '1', '2', '2013-11-15', '2013-11-16');

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `birth` varchar(10) NOT NULL COMMENT '出生日期',
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '性别',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('1', '施自强', '320111198706093532', '1987-06-09', '1');

-- ----------------------------
-- Table structure for `traffic_offence`
-- ----------------------------
DROP TABLE IF EXISTS `traffic_offence`;
CREATE TABLE `traffic_offence` (
  `pkid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `carid` int(10) unsigned NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `infomation` varchar(100) DEFAULT NULL,
  `time` varchar(19) NOT NULL,
  PRIMARY KEY (`pkid`),
  KEY `FK_TRAFFIC_CAR` (`carid`),
  CONSTRAINT `FK_TRAFFIC_CAR` FOREIGN KEY (`carid`) REFERENCES `car` (`pkid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of traffic_offence
-- ----------------------------
INSERT INTO `traffic_offence` VALUES ('1', '1', '大桥南路', '闯红灯', '2013-11-10 15:00:00');
INSERT INTO `traffic_offence` VALUES ('2', '1', '湖南路', '违章停车', '2013-11-20 19:00:00');
INSERT INTO `traffic_offence` VALUES ('3', '1', '大桥南路', '超速', '2013-12-01 02:00:00');
