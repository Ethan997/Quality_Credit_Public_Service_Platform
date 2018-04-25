/*
Navicat MySQL Data Transfer

Source Server         : springboot-starter
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : xiang_shan

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-28 18:47:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for complaints
-- ----------------------------
DROP TABLE IF EXISTS `complaints`;
CREATE TABLE `complaints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `status` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of complaints
-- ----------------------------

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `p_standard_url` varchar(255) DEFAULT '',
  `t_standard_url` varchar(255) DEFAULT '',
  `is_public` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `Industry` varchar(255) NOT NULL,
  `agency_code` varchar(255) NOT NULL,
  `registration_time` datetime NOT NULL,
  `business_scope` longtext NOT NULL,
  `administrative_division` varchar(255) NOT NULL,
  `legal_representative` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `srr_url` varchar(255) DEFAULT NULL,
  `qcf_url` varchar(255) DEFAULT NULL,
  `srp_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enterprise
-- ----------------------------
INSERT INTO `enterprise` VALUES ('19', '北京百度网讯科技有限公司', '搜索引擎', '802100433', '2001-06-11 01:03:04', '技术服务、技术培训、技术推广；设计、开发、销售计算机软件；经济信息咨询；利用www.baidu.com、www.hao123.com(www.hao222.net、www.hao222.com)网站发布广告；设计、制作、代理、发布广告；货物进出口、技术进出口、代理进出口；医疗软件技术开发；委托生产电子产品、玩具、照相器材；销售家用电器、机械设备、五金交电、电子产品、文化用品、照相器材、计算机、软件及辅助设备、化妆品、卫生用品、体育用品、纺织品、服装、鞋帽、日用品、家具、首饰、避孕器具、工艺品、钟表、眼镜、玩具、汽车及摩托车配件、仪器仪表、塑料制品、花、草及观赏植物、建筑材料、通讯设备；预防保健咨询；公园门票、文艺演出、体育赛事、展览会票务代理；因特网信息服务业务（除出版、教育、医疗保健以外的内容）；第一类增值电信业务中的在线数据处理与交易处理业务、国内因特网虚拟专用网业务、因特网数据中心业务；第二类增值电信业务中的因特网接入服务业务、呼叫中心业务、信息服务业务（不含固定网电话信息服务和互联网信息服务）；图书、电子出版物、音像制品批发、零售、网上销售；利用信息网络经营音乐娱乐产品，游戏产品，演出剧（节）目、表演，动漫产品（文化经营许可证有效期至2020年04月17日）；演出经纪。（企业依法自主选择经营项目，开展经营活动；演出经纪以及依法须经批准的项目，经相关部门批准后依批准的内容开展经营活动；不得从事本市产业政策禁止和限制类项目的经营活动。）', '海淀', '梁志祥', '北京市海淀区上地十街10号百度大厦2层', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/enterprise/1.bmp', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/enterprise/2.bmp', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/enterprise/3.bmp');

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(255) NOT NULL,
  `href` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_info
-- ----------------------------
INSERT INTO `file_info` VALUES ('134', '1.bmp', '2018-01-28 16:34:33', '陈齐康', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/department/1.bmp');
INSERT INTO `file_info` VALUES ('135', '2.bmp', '2018-01-28 16:34:47', '陈齐康', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/department/2.bmp');
INSERT INTO `file_info` VALUES ('136', '1.bmp', '2018-01-28 17:04:07', '陈齐康', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/enterprise/1.bmp');
INSERT INTO `file_info` VALUES ('137', '2.bmp', '2018-01-28 17:04:29', '陈齐康', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/enterprise/2.bmp');
INSERT INTO `file_info` VALUES ('138', '3.bmp', '2018-01-28 17:04:51', '陈齐康', 'http://xiang-shan-quality-platform.oss-cn-shenzhen.aliyuncs.com/enterprise/3.bmp');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `create_user` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `content` longtext NOT NULL,
  `content_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('8', '新增内容', '陈齐康', '2018-01-28 14:54:21', '<p>在此处编辑新闻</p><p>法规文件测试</p><p><br></p>', 'POLICY_AND_REGULATION_DOCUMENTS');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for rank
-- ----------------------------
DROP TABLE IF EXISTS `rank`;
CREATE TABLE `rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rank
-- ----------------------------
INSERT INTO `rank` VALUES ('1', '现代科技学院', 'BLANK');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `realname` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `deleted` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'administrator', '$2a$10$gYJ0hmsRI9FCuqmIDrykBexHMWzqFOJfvmDA0TchrZFGtP./vO912', '洪涛', 'MALE', '13216170021', 'false');
INSERT INTO `user` VALUES ('19', 'wangyusen', '$2a$10$0D974Oq9NRW2iBLTCbx1DOEJvF9/rmPu1BpBX2qM1M32JHAxtVZcy', '王雨森', 'MALE', '13342940395', 'false');
