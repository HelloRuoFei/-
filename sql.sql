/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.62 : Database - springbootbs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springbootbs` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `springbootbs`;

/*Table structure for table `bus_customer` */

DROP TABLE IF EXISTS `bus_customer`;

CREATE TABLE `bus_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_customer` */

insert  into `bus_customer`(`id`,`customername`,`zip`,`address`,`telephone`,`connectionperson`,`phone`,`bank`,`account`,`email`,`fax`,`available`) values (1,'小张超市','111111','武汉','027-9123131','张大明','13812312312321312','中国银行','654431331343413','213123@sina.com','430000',1),(2,'小明超市','222','深圳','0755-9123131','张小明','13812312312321312','中国银行','654431331343413','213123@sina.com','430000',1),(3,'快七超市','430000','武汉','027-11011011','雷生','13434134131','招商银行','6543123341334133','6666@66.com','545341',1);

/*Table structure for table `bus_goods` */

DROP TABLE IF EXISTS `bus_goods`;

CREATE TABLE `bus_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) DEFAULT NULL,
  `produceplace` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `goodspackage` varchar(255) DEFAULT NULL,
  `productcode` varchar(255) DEFAULT NULL,
  `promitcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `dangernum` int(11) DEFAULT NULL,
  `goodsimg` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_sq0btr2v2lq8gt8b4gb8tlk0i` (`providerid`) USING BTREE,
  CONSTRAINT `bus_goods_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_goods` */

insert  into `bus_goods`(`id`,`goodsname`,`produceplace`,`size`,`goodspackage`,`productcode`,`promitcode`,`description`,`price`,`number`,`dangernum`,`goodsimg`,`available`,`providerid`) values (1,'娃哈哈','武汉','120ML','瓶','PH12345','PZ1234','小孩子都爱的',2,488,10,'2019-12-29/A42773497F0D4DA596040B2975E4E020.gif',1,3),(2,'旺旺雪饼[小包]','仙桃','包','袋','PH12312312','PZ12312','好吃不上火',4,1100,10,'2019-12-29/A42773497F0D4DA596040B2975E4E020.gif',1,1),(3,'旺旺大礼包','仙桃','盒','盒','11','11','111',28,1021,100,'2019-12-29/A42773497F0D4DA596040B2975E4E020.gif',1,1),(4,'娃哈哈','武汉','200ML','瓶','11','111','12321',3,1100,10,'2019-12-29/A42773497F0D4DA596040B2975E4E020.gif',1,3),(5,'娃哈哈','武汉','300ML','瓶','1234','1232112','22221111',3,1000,100,'2019-12-29/A42773497F0D4DA596040B2975E4E020.gif',1,3),(11,'1','12','123','12','123','213','11',123,263246,123,'2019-12-29/A42773497F0D4DA596040B2975E4E020.gif',1,5);

/*Table structure for table `bus_inport` */

DROP TABLE IF EXISTS `bus_inport`;

CREATE TABLE `bus_inport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paytype` varchar(255) DEFAULT NULL,
  `inporttime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `inportprice` double DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_1o4bujsyd2kl4iqw48fie224v` (`providerid`) USING BTREE,
  KEY `FK_ho29poeu4o2dxu4rg1ammeaql` (`goodsid`) USING BTREE,
  CONSTRAINT `bus_inport_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`),
  CONSTRAINT `bus_inport_ibfk_2` FOREIGN KEY (`goodsid`) REFERENCES `bus_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_inport` */

insert  into `bus_inport`(`id`,`paytype`,`inporttime`,`operateperson`,`number`,`remark`,`inportprice`,`providerid`,`goodsid`) values (1,'微信','2018-05-07 00:00:00','张三',100,'备注',3.5,1,1),(2,'支付宝','2018-05-07 00:00:00','张三',1000,'无',2.5,3,3),(3,'银联','2018-05-07 00:00:00','张三',100,'1231',111,3,3),(4,'银联','2018-05-07 00:00:00','张三',1000,'无',2,3,1),(5,'银联','2018-05-07 00:00:00','张三',100,'无',1,3,1),(6,'银联','2018-05-07 00:00:00','张三',100,'1231',2.5,1,2),(8,'支付宝','2018-05-07 00:00:00','张三',100,'',1,3,1),(10,'支付宝','2018-08-07 17:17:57','超级管理员',100,'sadfasfdsa',1.5,3,1),(11,'支付宝','2018-09-17 16:12:25','超级管理员',21,'21',21,1,3),(12,'微信','2018-12-25 16:48:24','超级管理员',100,'123213213',12321321,3,1),(13,'支付宝','2019-12-30 04:43:28','snow',123123,'123123',123,5,11),(14,'支付宝','2019-12-30 05:28:16','snow',123123,'123',123,5,11),(15,'支付宝','2019-12-30 05:29:19','snow',111111,'11',11111,5,11),(16,'支付宝','2019-12-30 05:38:26','snow',121100,'123',123,5,11),(18,'微信','2019-12-30 06:56:38','snow',7789,'89',89,5,11);

/*Table structure for table `bus_outport` */

DROP TABLE IF EXISTS `bus_outport`;

CREATE TABLE `bus_outport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `outputtime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `outportprice` double(10,2) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_outport` */

insert  into `bus_outport`(`id`,`providerid`,`paytype`,`outputtime`,`operateperson`,`outportprice`,`number`,`remark`,`goodsid`) values (1,3,'微信','2019-08-16 08:19:50','超级管理员',12321321.00,1,'',1),(2,3,'微信','2019-08-16 08:26:54','超级管理员',12321321.00,11,'11',1),(3,5,'支付宝','2019-12-30 06:41:37','snow',123.00,121143,'123',11),(4,5,'支付宝','2019-12-30 06:42:05','snow',123.00,121100,'123',11),(5,5,'微信','2019-12-30 06:56:53','snow',89.00,8889,'89',11),(6,5,'微信','2019-12-30 06:58:41','snow',89.00,7889,'89',11),(7,5,'微信','2019-12-30 07:04:22','snow',89.00,100,'89',11);

/*Table structure for table `bus_provider` */

DROP TABLE IF EXISTS `bus_provider`;

CREATE TABLE `bus_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_provider` */

insert  into `bus_provider`(`id`,`providername`,`zip`,`address`,`telephone`,`connectionperson`,`phone`,`bank`,`account`,`email`,`fax`,`available`) values (1,'旺旺食品','4340001','仙桃','0728-4124312','小明','13413441141','中国农业银行','654124345131','12312321@sina.com','5123123',1),(2,'达利园食品','430000','汉川','0728-4124312','大明','13413441141','中国农业银行','654124345131','12312321@sina.com','5123123',1),(3,'娃哈哈集团','513131','杭州','21312','小晨','12312','建设银行','512314141541','123131','312312',1),(5,'1','1','1','1','11','1','1是','1','1','1',1);

/*Table structure for table `bus_sales` */

DROP TABLE IF EXISTS `bus_sales`;

CREATE TABLE `bus_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salestime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `saleprice` decimal(10,2) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_sales` */

/*Table structure for table `bus_salesback` */

DROP TABLE IF EXISTS `bus_salesback`;

CREATE TABLE `bus_salesback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salesbacktime` datetime DEFAULT NULL,
  `salebackprice` double(10,2) DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_salesback` */

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  `ordernum` int(11) DEFAULT NULL COMMENT '排序码【为了调事显示顺序】',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`id`,`pid`,`title`,`open`,`remark`,`address`,`available`,`ordernum`,`createtime`) values (1,0,'总经办',1,'大BOSS','深圳',1,1,'2019-04-10 14:06:32'),(2,1,'销售部',0,'程序员屌丝','武汉',1,2,'2019-04-10 14:06:32'),(3,1,'运营部',0,'无','武汉',1,3,'2019-04-10 14:06:32'),(4,1,'生产部',0,'无','武汉',1,4,'2019-04-10 14:06:32'),(5,2,'销售一部',0,'销售一部','武汉',1,5,'2019-04-10 14:06:32'),(6,2,'销售二部',0,'销售二部','武汉',1,6,'2019-04-10 14:06:32'),(7,3,'运营一部',0,'运营一部','武汉',1,7,'2019-04-10 14:06:32'),(18,4,'生产一部',0,'生产食品','武汉',1,11,'2019-04-13 09:49:38');

/*Table structure for table `sys_loginfo` */

DROP TABLE IF EXISTS `sys_loginfo`;

CREATE TABLE `sys_loginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `loginip` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=336 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_loginfo` */

insert  into `sys_loginfo`(`id`,`loginname`,`loginip`,`logintime`) values (172,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 11:33:35'),(173,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 12:39:47'),(174,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:05:28'),(175,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:23:57'),(176,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:27:42'),(177,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:29:07'),(178,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:34:45'),(179,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:36:05'),(180,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:41:26'),(181,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:45:36'),(182,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 13:54:23'),(183,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 14:09:59'),(184,'snow-system','0:0:0:0:0:0:0:1','2019-12-14 14:33:25'),(185,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 02:36:01'),(186,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 02:38:49'),(187,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 02:42:57'),(188,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 03:08:00'),(189,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 07:06:19'),(190,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 14:42:12'),(191,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 14:45:30'),(192,'snow-system','0:0:0:0:0:0:0:1','2019-12-16 14:47:15'),(193,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 06:16:03'),(194,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 06:23:53'),(195,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 06:46:36'),(196,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 07:55:14'),(197,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 08:18:42'),(198,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 08:24:29'),(199,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 08:42:12'),(200,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 08:58:11'),(201,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 09:12:46'),(202,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 09:29:20'),(203,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 09:33:59'),(204,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 15:01:55'),(205,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 16:02:15'),(206,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 16:04:45'),(207,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 16:14:44'),(208,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 16:36:07'),(209,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 16:40:35'),(210,'snow-system','0:0:0:0:0:0:0:1','2019-12-20 16:48:27'),(211,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 03:53:51'),(212,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 04:07:49'),(213,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 04:24:35'),(214,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 08:54:22'),(215,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 09:00:08'),(216,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 09:22:25'),(217,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 09:57:29'),(218,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 11:27:29'),(219,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 11:56:33'),(220,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 15:18:38'),(221,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 15:20:36'),(222,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 15:33:05'),(223,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 15:42:00'),(224,'snow-system','0:0:0:0:0:0:0:1','2019-12-23 15:45:47'),(225,'snow-system','0:0:0:0:0:0:0:1','2019-12-24 02:21:06'),(226,'snow-system','0:0:0:0:0:0:0:1','2019-12-24 14:41:08'),(227,'snow-system','0:0:0:0:0:0:0:1','2019-12-24 14:49:12'),(228,'snow-system','0:0:0:0:0:0:0:1','2019-12-24 14:58:10'),(229,'snow-system','0:0:0:0:0:0:0:1','2019-12-24 15:14:59'),(230,'snow-system','0:0:0:0:0:0:0:1','2019-12-24 16:28:55'),(231,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 02:49:24'),(232,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 04:39:29'),(233,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 05:09:43'),(234,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 05:12:14'),(235,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 05:23:59'),(236,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 05:52:58'),(237,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 05:58:03'),(238,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 08:15:20'),(239,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 08:19:37'),(240,'snow-system','0:0:0:0:0:0:0:1','2019-12-25 14:51:16'),(241,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 05:36:35'),(242,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 08:10:27'),(243,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 08:24:35'),(244,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 09:13:17'),(245,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 10:39:22'),(246,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 10:48:39'),(247,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 11:54:36'),(248,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 12:32:43'),(249,'ruoruo-ruoruo','0:0:0:0:0:0:0:1','2019-12-26 12:35:07'),(250,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 12:35:31'),(251,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 14:13:49'),(252,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 14:22:46'),(253,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 14:41:14'),(254,'snow-system','0:0:0:0:0:0:0:1','2019-12-26 15:06:35'),(255,'snow-system','0:0:0:0:0:0:0:1','2019-12-27 11:36:08'),(256,'snow-system','0:0:0:0:0:0:0:1','2019-12-27 16:33:23'),(257,'snow-snow','0:0:0:0:0:0:0:1','2019-12-27 16:35:18'),(258,'snow-system','0:0:0:0:0:0:0:1','2019-12-27 16:36:01'),(259,'snow-snow','0:0:0:0:0:0:0:1','2019-12-27 16:37:07'),(260,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:09:32'),(261,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:12:45'),(262,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:18:38'),(263,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:21:50'),(264,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:25:52'),(265,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:27:44'),(266,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:30:26'),(267,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:33:39'),(268,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 03:37:38'),(269,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 04:06:49'),(270,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 04:13:44'),(271,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 04:32:48'),(272,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 13:11:32'),(273,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 13:14:00'),(274,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 13:57:17'),(275,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 14:01:05'),(276,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 14:02:03'),(277,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 14:26:52'),(278,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 14:27:58'),(279,'snow-system','0:0:0:0:0:0:0:1','2019-12-28 15:00:46'),(280,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 06:20:15'),(281,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 06:25:24'),(282,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 06:43:42'),(283,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 06:46:39'),(284,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 07:03:28'),(285,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 07:09:18'),(286,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 07:43:36'),(287,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:10:09'),(288,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:13:55'),(289,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:23:27'),(290,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:31:11'),(291,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:33:13'),(292,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:38:17'),(293,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:40:44'),(294,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 08:47:42'),(295,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 09:16:35'),(296,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 09:17:36'),(297,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 12:00:08'),(298,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 12:09:25'),(299,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 13:07:58'),(300,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 13:43:43'),(301,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 13:54:55'),(302,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 14:11:59'),(303,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 14:36:35'),(304,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 15:09:15'),(305,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 16:06:01'),(306,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 16:25:46'),(307,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 16:27:19'),(308,'snow-system','0:0:0:0:0:0:0:1','2019-12-29 17:18:15'),(309,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 02:45:36'),(310,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 02:46:41'),(311,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 02:51:17'),(312,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 02:57:05'),(313,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 02:59:07'),(314,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 03:41:59'),(315,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 03:48:58'),(316,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 03:51:12'),(317,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 04:16:39'),(318,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 04:19:07'),(319,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 04:20:53'),(320,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 04:42:40'),(321,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 04:53:52'),(322,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 04:59:43'),(323,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 05:27:39'),(324,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 05:36:33'),(325,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 05:37:57'),(326,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 05:40:59'),(327,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 05:52:22'),(328,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 06:08:34'),(329,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 06:40:07'),(330,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 06:41:19'),(331,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 06:53:40'),(332,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 06:55:34'),(333,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 07:04:06'),(334,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 07:08:51'),(335,'snow-system','0:0:0:0:0:0:0:1','2019-12-30 11:59:09');

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `createtime` datetime DEFAULT NULL,
  `opername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_notice` */

insert  into `sys_notice`(`id`,`title`,`content`,`createtime`,`opername`) values (14,'asdsa','asdasdsdfsdf','2019-12-14 14:33:50','snow');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '权限类型[menu/permission]',
  `title` varchar(255) DEFAULT NULL,
  `percode` varchar(255) DEFAULT NULL COMMENT '权限编码[只有type= permission才有  user:view]',
  `icon` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`pid`,`type`,`title`,`percode`,`icon`,`href`,`target`,`open`,`ordernum`,`available`) values (1,0,'menu','仓库管理系统',NULL,'&#xe68e;','','',1,1,1),(2,1,'menu','基础管理',NULL,'&#xe857;','','',1,2,1),(3,1,'menu','进货管理',NULL,'&#xe645;','',NULL,0,3,1),(4,1,'menu','销售管理',NULL,'&#xe611;','','',0,4,1),(5,1,'menu','系统管理',NULL,'&#xe614;','','',0,5,1),(6,1,'menu','其它管理',NULL,'&#xe628;','','',0,6,1),(7,2,'menu','客户管理',NULL,'&#xe651;','/bus/toCustomerManager','',0,7,1),(8,2,'menu','供应商管理',NULL,'&#xe658;','/bus/toProviderManager','',0,8,1),(9,2,'menu','商品管理',NULL,'&#xe657;','/bus/toGoodsManager','',0,9,1),(10,3,'menu','商品进货',NULL,'&#xe756;','/bus/toInportManager','',0,10,1),(11,3,'menu','商品退货查询',NULL,'&#xe65a;','/bus/toOutportManager','',0,11,1),(12,4,'menu','商品销售',NULL,'&#xe65b;','','',0,12,1),(13,4,'menu','销售退货查询',NULL,'&#xe770;','','',0,13,1),(14,5,'menu','部门管理',NULL,'&#xe770;','/sys/toDeptManager','',0,14,1),(15,5,'menu','菜单管理',NULL,'&#xe857;','/sys/toMenuManager','',0,15,1),(16,5,'menu','权限管理','','&#xe857;','/sys/toPermissionManager','',0,16,1),(17,5,'menu','角色管理','','&#xe650;','/sys/toRoleManager','',0,17,1),(18,5,'menu','用户管理','','&#xe612;','/sys/toUserManager','',0,18,1),(21,6,'menu','登陆日志',NULL,'&#xe675;','/sys/toLoginfoManager','',0,21,1),(22,6,'menu','系统公告',NULL,'&#xe756;','/sys/toNoticeManager',NULL,0,22,1),(23,6,'menu','图标管理',NULL,'&#xe670;','../resources/page/icon.html',NULL,0,23,1),(30,14,'permission','添加部门','dept:create','',NULL,NULL,0,24,1),(31,14,'permission','修改部门','dept:update','',NULL,NULL,0,26,1),(32,14,'permission','删除部门','dept:delete','',NULL,NULL,0,27,1),(34,15,'permission','添加菜单','menu:create','','','',0,29,1),(35,15,'permission','修改菜单','menu:update','',NULL,NULL,0,30,1),(36,15,'permission','删除菜单','menu:delete','',NULL,NULL,0,31,1),(38,16,'permission','添加权限','permission:create','',NULL,NULL,0,33,1),(39,16,'permission','修改权限','permission:update','',NULL,NULL,0,34,1),(40,16,'permission','删除权限','permission:delete','',NULL,NULL,0,35,1),(42,17,'permission','添加角色','role:create','',NULL,NULL,0,37,1),(43,17,'permission','修改角色','role:update','',NULL,NULL,0,38,1),(44,17,'permission','角色删除','role:delete','',NULL,NULL,0,39,1),(46,17,'permission','分配权限','role:selectPermission','',NULL,NULL,0,41,1),(47,18,'permission','添加用户','user:create','',NULL,NULL,0,42,1),(48,18,'permission','修改用户','user:update','',NULL,NULL,0,43,1),(49,18,'permission','删除用户','user:delete','',NULL,NULL,0,44,1),(51,18,'permission','用户分配角色','user:selectRole','',NULL,NULL,0,46,1),(52,18,'permission','重置密码','user:resetPwd',NULL,NULL,NULL,0,47,1),(53,14,'permission','部门查询','dept:view',NULL,NULL,NULL,0,48,1),(54,15,'permission','菜单查询','menu:view',NULL,NULL,NULL,0,49,1),(55,16,'permission','权限查询','permission:view',NULL,NULL,NULL,0,50,1),(56,17,'permission','角色查询','role:view',NULL,NULL,NULL,0,51,1),(57,18,'permission','用户查询','user:view',NULL,NULL,NULL,0,52,1),(68,7,'permission','客户查询','customer:view',NULL,NULL,NULL,NULL,60,1),(69,7,'permission','客户添加','customer:create',NULL,NULL,NULL,NULL,61,1),(70,7,'permission','客户修改','customer:update',NULL,NULL,NULL,NULL,62,1),(71,7,'permission','客户删除','customer:delete',NULL,NULL,NULL,NULL,63,1),(73,21,'permission','日志查询','info:view',NULL,NULL,NULL,NULL,65,1),(74,21,'permission','日志删除','info:delete',NULL,NULL,NULL,NULL,66,1),(75,21,'permission','日志批量删除','info:batchdelete',NULL,NULL,NULL,NULL,67,1),(76,22,'permission','公告查询','notice:view',NULL,NULL,NULL,NULL,68,1),(77,22,'permission','公告添加','notice:create',NULL,NULL,NULL,NULL,69,1),(78,22,'permission','公告修改','notice:update',NULL,NULL,NULL,NULL,70,1),(79,22,'permission','公告删除','notice:delete',NULL,NULL,NULL,NULL,71,1),(81,8,'permission','供应商查询','provider:view',NULL,NULL,NULL,NULL,73,1),(82,8,'permission','供应商添加','provider:create',NULL,NULL,NULL,NULL,74,1),(83,8,'permission','供应商修改','provider:update',NULL,NULL,NULL,NULL,75,1),(84,8,'permission','供应商删除','provider:delete',NULL,NULL,NULL,NULL,76,1),(86,22,'permission','公告查看','notice:viewnotice',NULL,NULL,NULL,NULL,78,1),(91,9,'permission','商品查询','goods:view',NULL,NULL,NULL,0,79,1),(92,9,'permission','商品添加','goods:create',NULL,NULL,NULL,0,80,1),(101,9,'permission','123','123',NULL,NULL,NULL,0,83,1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`remark`,`available`,`createtime`) values (1,'超级管理员','拥有所有菜单权限',1,'2019-04-10 14:06:32'),(4,'基础数据管理员','基础数据管理员',1,'2019-04-10 14:06:32'),(5,'仓库管理员','仓库管理员',1,'2019-04-10 14:06:32'),(6,'销售管理员','销售管理员',1,'2019-04-10 14:06:32'),(7,'系统管理员','系统管理员',1,'2019-04-10 14:06:32'),(11,'测试','测试专用',1,'2019-12-25 08:26:19');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`rid`) USING BTREE,
  KEY `FK_tcsr9ucxypb3ce1q5qngsfk33` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`rid`,`pid`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,21),(1,22),(1,23),(1,31),(1,34),(1,35),(1,36),(1,38),(1,39),(1,40),(1,42),(1,43),(1,44),(1,46),(1,47),(1,48),(1,49),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,68),(1,69),(1,70),(1,71),(1,73),(1,74),(1,75),(1,76),(1,77),(1,78),(1,79),(1,81),(1,82),(1,83),(1,84),(1,86),(1,91),(1,92),(4,1),(4,2),(4,5),(4,7),(4,8),(4,9),(4,14),(4,15),(4,16),(4,17),(4,18),(4,30),(4,31),(4,32),(4,34),(4,35),(4,36),(4,38),(4,39),(4,40),(4,42),(4,43),(4,44),(4,46),(4,47),(4,48),(4,49),(4,51),(4,52),(4,53),(4,54),(4,55),(4,56),(4,57),(4,68),(4,69),(4,70),(4,71),(4,81),(4,82),(4,83),(4,84),(4,91),(4,92),(5,1),(5,3),(5,4),(5,10),(5,11),(5,12),(5,13),(6,1),(6,4),(6,12),(6,13),(7,1),(7,5),(7,6),(7,14),(7,15),(7,16),(7,17),(7,18),(7,21),(7,22),(7,23),(7,30),(7,31),(7,32),(7,34),(7,35),(7,36),(7,38),(7,39),(7,40),(7,42),(7,43),(7,44),(7,46),(7,47),(7,48),(7,49),(7,51),(7,52),(7,53),(7,54),(7,55),(7,56),(7,57),(7,73),(7,74),(7,75),(7,76),(7,77),(7,78),(7,79),(7,86),(11,1),(11,2),(11,5),(11,7),(11,8),(11,9),(11,14),(11,15),(11,16),(11,17),(11,18),(11,31),(11,34),(11,35),(11,36),(11,38),(11,39),(11,40),(11,42),(11,43),(11,44),(11,46),(11,47),(11,48),(11,49),(11,51),(11,52),(11,53),(11,54),(11,55),(11,56),(11,57),(11,68),(11,69),(11,70),(11,71),(11,81),(11,82),(11,83),(11,84),(11,91),(11,92),(11,101);

/*Table structure for table `sys_role_user` */

DROP TABLE IF EXISTS `sys_role_user`;

CREATE TABLE `sys_role_user` (
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`) USING BTREE,
  KEY `FK_203gdpkwgtow7nxlo2oap5jru` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_role_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_user` */

insert  into `sys_role_user`(`rid`,`uid`) values (1,2),(4,3),(5,4),(6,5),(11,12);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT '1',
  `ordernum` int(11) DEFAULT NULL,
  `type` int(255) DEFAULT NULL COMMENT '用户类型[0超级管理员1，管理员，2普通用户]',
  `imgpath` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_3rrcpvho2w1mx1sfiuuyir1h` (`deptid`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`deptid`) REFERENCES `sys_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`name`,`loginname`,`address`,`sex`,`remark`,`pwd`,`deptid`,`hiredate`,`mgr`,`available`,`ordernum`,`type`,`imgpath`,`salt`) values (1,'snow','system','系统深处的男人',1,'超级管理员','532ac00e86893901af5f0be6b704dbc7',1,'2018-06-25 11:06:34',NULL,1,1,0,'../resources/images/defaultusertitle.jpg','04A93C74C8294AA09A8B974FD1F4ECBB'),(2,'李四','lisi','武汉',0,'KING','b07b848d69e0553b80e601d31571797e',1,'2018-06-25 11:06:36',NULL,1,2,1,'../resources/images/defaultusertitle.jpg','FC1EE06AE4354D3FBF7FDD15C8FCDA71'),(3,'王五','ww','武汉',1,'管理员','3c3f971eae61e097f59d52360323f1c8',3,'2018-06-25 11:06:38',2,1,3,1,'../resources/images/defaultusertitle.jpg','3D5F956E053C4E85B7D2681386E235D2'),(4,'赵六','zl','武汉',1,'程序员','2e969742a7ea0c7376e9551d578e05dd',4,'2018-06-25 11:06:40',3,1,4,1,'../resources/images/defaultusertitle.jpg','6480EE1391E34B0886ACADA501E31145'),(5,'孙七','sq','武汉',1,'程序员','47b4c1ad6e4b54dd9387a09cb5a03de1',2,'2018-06-25 11:06:43',4,1,5,1,'../resources/images/defaultusertitle.jpg','FE3476C3E3674E5690C737C269FCBF8E'),(6,'刘八','lb','深圳',1,'程序员','02f075b2a724122c4e4c11fbc499ec05',4,'2018-08-06 11:21:12',3,1,6,1,'../resources/images/defaultusertitle.jpg','BE378A2AA67B4240A36BF9749DE0994E'),(12,'snow','snow','123',1,'123','60c1956fb0c2f0ac89740358294fc122',18,'2019-12-27 11:36:56',4,1,7,1,NULL,'0A3D55B5BE1F4437BB64BB047A0450AE');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
