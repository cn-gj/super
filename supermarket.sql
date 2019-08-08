/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.5.60 : Database - supermarket
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`supermarket` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `supermarket`;

/*Table structure for table `allot` */

DROP TABLE IF EXISTS `allot`;

CREATE TABLE `allot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `in_shop_id` int(11) DEFAULT NULL,
  `out_shop_id` int(11) DEFAULT NULL,
  `allot_type` int(11) DEFAULT NULL,
  `allot_state` int(11) DEFAULT NULL,
  `single_state` int(11) DEFAULT NULL,
  `single_money` double DEFAULT NULL,
  `single_date` date DEFAULT NULL,
  `allot_detail_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `allot` */

/*Table structure for table `allot_detail` */

DROP TABLE IF EXISTS `allot_detail`;

CREATE TABLE `allot_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allot_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `allot_detail` */

/*Table structure for table `cashier_desk` */

DROP TABLE IF EXISTS `cashier_desk`;

CREATE TABLE `cashier_desk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cashier_name` varchar(20) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `cashier_state` int(11) NOT NULL,
  `createDate` date NOT NULL,
  `modifyDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `cashier_desk` */

insert  into `cashier_desk`(`id`,`cashier_name`,`shop_id`,`cashier_state`,`createDate`,`modifyDate`) values (1,'贱贱收银台',3,1,'2019-08-01','2019-07-30'),(2,'洋洋收银',2,1,'2019-07-30','2019-07-30'),(3,'3号收银台',3,1,'2019-07-30','2019-07-30'),(4,'超级收银台',2,0,'2019-07-31','2019-07-31');

/*Table structure for table `check_detail` */

DROP TABLE IF EXISTS `check_detail`;

CREATE TABLE `check_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ismodify` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `inv_detail_id` int(11) DEFAULT NULL,
  `check_count` int(11) DEFAULT NULL,
  `bep_count` int(11) DEFAULT NULL,
  `cost_price` double DEFAULT NULL,
  `original_price` double DEFAULT NULL,
  `check_money` double DEFAULT NULL,
  `bep_money` double DEFAULT NULL,
  `cause_info` varchar(60) DEFAULT NULL,
  `inv_checked_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `check_detail` */

insert  into `check_detail`(`id`,`ismodify`,`goods_id`,`inv_detail_id`,`check_count`,`bep_count`,`cost_price`,`original_price`,`check_money`,`bep_money`,`cause_info`,`inv_checked_id`) values (25,NULL,1,1,0,-1,2,2,0,-2,'xxxxxxxx',15),(26,NULL,2,5,0,-1,10,10,0,-10,NULL,15),(27,NULL,3,7,0,-1,3,3,0,-3,NULL,15),(28,NULL,4,8,0,-1,3,3,0,-3,NULL,15),(29,NULL,8,9,0,-1,200,200,0,-200,NULL,15),(30,NULL,9,10,0,-1,10,10,0,-10,NULL,15);

/*Table structure for table `check_scope` */

DROP TABLE IF EXISTS `check_scope`;

CREATE TABLE `check_scope` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scope_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `check_scope` */

insert  into `check_scope`(`id`,`scope_name`) values (1,'人工盘点'),(2,'全场盘点');

/*Table structure for table `check_state` */

DROP TABLE IF EXISTS `check_state`;

CREATE TABLE `check_state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `check_state` */

insert  into `check_state`(`id`,`state_name`) values (1,'盘点录入'),(2,'盘点取消'),(3,'盘点完成');

/*Table structure for table `cust_emp_relation` */

DROP TABLE IF EXISTS `cust_emp_relation`;

CREATE TABLE `cust_emp_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `emp_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `cust_emp_relation` */

insert  into `cust_emp_relation`(`id`,`customer_id`,`emp_id`) values (1,1,1),(2,2,2),(3,3,1),(4,4,1),(5,5,5),(6,6,3),(7,7,7),(8,8,12);

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(20) NOT NULL,
  `customer_linkman` varchar(20) DEFAULT NULL,
  `customer_phone` varchar(11) NOT NULL,
  `customer_email` varchar(50) DEFAULT NULL,
  `bank` varchar(20) DEFAULT NULL,
  `bank_num` varchar(20) DEFAULT NULL,
  `customer_address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`id`,`customer_name`,`customer_linkman`,`customer_phone`,`customer_email`,`bank`,`bank_num`,`customer_address`) values (1,'zhangsan','中国烟草公司','12345678901','123@qq.com','dffvb','234567','北京'),(2,'lisi','天美工作室','12345678567','1234@qq.com','2345','12345467','上海'),(3,'adiao','亚马逊','2345234567','123@qq.com','3445657','23456','上海'),(4,'美的','董明珠','1234567890','12345@qq.com','工商银行','12345678','北京'),(6,'澳门皇冠赌场','草叫','15898724576',NULL,NULL,NULL,'中关村'),(7,'洋洋食品有限公司','冯希瑶','12345678911','xiyao@yy.com','工商银行','1234567891234567899','洋洋家对面的面包店'),(8,'明日之子有限公司','华晨宇','15290365478','huahua@qq.com','农业银行','1657853456728987656','湖南长白山');

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_account` varchar(150) DEFAULT NULL,
  `emp_pwd` varchar(150) DEFAULT NULL,
  `emp_name` varchar(60) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `emp_status` int(11) DEFAULT NULL,
  `emp_phone` varchar(33) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `employee` */

insert  into `employee`(`id`,`emp_account`,`emp_pwd`,`emp_name`,`role_id`,`emp_status`,`emp_phone`,`createDate`,`shop_id`) values (1,'admin','123456','沈月',1,0,'15938409756','2019-07-12',2),(2,'zhangsan','123456','张三',2,0,'18967843215','2019-07-16',2),(3,'lisi','123456','李小龙',3,1,'130','2019-07-17',3),(4,'wangwu','123456','王五',1,0,'140','2019-07-15',2),(5,'fengxiyao','123456','冯希瑶',NULL,1,'160','2019-07-16',2),(7,'hongyinuo','123456','洪一诺',NULL,0,'160','2019-07-16',3),(10,'yangzi','123456','杨紫',3,0,'160','2019-07-16',3),(11,'linjiuge','6dc1fe26d37074c0b3b9209a73e40634','冯怡',1,0,'14332222112','2019-07-16',3),(12,'loveyxy','f6348da67e4604df77cd9dc1c71d1fc5','小海',1,0,'18680631678','2019-07-27',2),(21,'loveyxy1','9b92a69d432974e11350d1ef60f6695c','小海海',1,0,'18680631678','2019-07-27',2),(22,'guojian','0b4a6691e0ab637deb03f05555410318','贱贱',3,0,'1234567789','2019-07-27',4);

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(200) NOT NULL,
  `goods_code` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `member_price` double DEFAULT NULL,
  `whole_price` double NOT NULL,
  `cost_price` double NOT NULL,
  `spec_detail_id` int(11) NOT NULL,
  `unit_id` varchar(10) DEFAULT NULL,
  `goods_type_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `goods_address` varchar(30) DEFAULT NULL,
  `yield_date` date NOT NULL,
  `expiration_day` varchar(10) NOT NULL,
  `expiration_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`goods_name`,`goods_code`,`price`,`member_price`,`whole_price`,`cost_price`,`spec_detail_id`,`unit_id`,`goods_type_id`,`provider_id`,`goods_address`,`yield_date`,`expiration_day`,`expiration_date`) values (1,'洋乐多','2012364',5,4,3,2,1,'瓶',1,2,'帝国大厦','2019-07-25','30','2019-08-21'),(2,'高级的不能在高级的牙刷','12335468',10,17,15,5,2,'根',1,1,'xx村','2019-07-22','30','2019-08-14'),(3,'安慕','100100',5,NULL,4,3,1,'瓶',1,1,'河南','2019-09-09','30','2019-10-09'),(4,'伊利','110110',5,NULL,4,2,1,'瓶',2,1,'南阳','2019-07-27','60','2019-09-08'),(8,'韩都衣舍2019夏装新款复古初恋长裙学生气质雪纺连衣裙女LU6558荃','123456',328,NULL,320,180,11,'件',11,4,'江南西','2019-08-03','10000','2019-08-03'),(9,'云南白药牙膏国粹清新口气去黄牙垢去口臭美白亮白牙齿抗敏家庭装','123451',199,NULL,189,10,1,'支',12,4,'云南大理','2019-08-27','365','2020-08-25');

/*Table structure for table `goods_type` */

DROP TABLE IF EXISTS `goods_type`;

CREATE TABLE `goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_type_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `goods_type` */

insert  into `goods_type`(`id`,`goods_type_name`) values (1,'瓶装奶'),(2,'调味品'),(11,'连衣裙'),(12,'牙膏');

/*Table structure for table `inventory_check` */

DROP TABLE IF EXISTS `inventory_check`;

CREATE TABLE `inventory_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) DEFAULT NULL,
  `check_scope_id` int(11) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `check_date` date DEFAULT NULL,
  `check_state_id` int(11) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `inventory_check` */

insert  into `inventory_check`(`id`,`store_id`,`check_scope_id`,`createDate`,`check_date`,`check_state_id`,`emp_id`) values (15,1,2,'2019-08-05','2019-08-05',1,11);

/*Table structure for table `inventory_detail` */

DROP TABLE IF EXISTS `inventory_detail`;

CREATE TABLE `inventory_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `goods_count` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `inventory_detail` */

insert  into `inventory_detail`(`id`,`goods_id`,`goods_count`,`store_id`) values (1,1,38,1),(2,2,30,2),(3,3,46,3),(4,4,187,3),(5,2,-1,1),(7,3,10,1),(8,4,-7,1),(9,8,22,1),(10,9,19,1),(11,9,20,5);

/*Table structure for table `level` */

DROP TABLE IF EXISTS `level`;

CREATE TABLE `level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_name` varchar(20) NOT NULL,
  `level_image` varchar(50) DEFAULT NULL,
  `discount` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `level` */

insert  into `level`(`id`,`level_name`,`level_image`,`discount`) values (2,'银卡','','0.8'),(3,'金卡','','0.85'),(4,'砖石卡','','0.8'),(5,'普通会员','adasdcsz','0.99');

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_name` varchar(20) NOT NULL,
  `member_phone` varchar(11) NOT NULL,
  `member_birthday` date DEFAULT NULL,
  `member_sex` varchar(6) DEFAULT NULL,
  `shop_id` int(11) NOT NULL,
  `level_id` int(11) NOT NULL,
  `total_money` double NOT NULL DEFAULT '0',
  `total_sorce` int(11) NOT NULL DEFAULT '0',
  `residue_money` double NOT NULL DEFAULT '0',
  `residue_sorce` int(11) NOT NULL DEFAULT '0',
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `member` */

insert  into `member`(`id`,`member_name`,`member_phone`,`member_birthday`,`member_sex`,`shop_id`,`level_id`,`total_money`,`total_sorce`,`residue_money`,`residue_sorce`,`createDate`) values (1,'张三','13422222222','2019-07-22','男',2,4,200,200,200,200,'2019-07-22 00:00:00'),(2,'lisi','13522222222','2019-07-10','女',3,3,200,300,200,300,'2019-07-11 00:00:00'),(7,'marry','12345678911','2019-07-28','女',3,2,0,10,0,5,'2019-07-29 18:58:53');

/*Table structure for table `oper_info` */

DROP TABLE IF EXISTS `oper_info`;

CREATE TABLE `oper_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oper_date` date DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `oper_state` int(11) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `oper_model_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oper_info` */

/*Table structure for table `oper_model` */

DROP TABLE IF EXISTS `oper_model`;

CREATE TABLE `oper_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(30) DEFAULT NULL,
  `table_name` varchar(30) DEFAULT NULL,
  `data_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oper_model` */

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_id` int(11) NOT NULL,
  `shop_id` varchar(20) NOT NULL,
  `place_order_date` date NOT NULL,
  `ready_date` date NOT NULL,
  `emp_id` int(11) NOT NULL,
  `single_state` int(11) NOT NULL DEFAULT '0',
  `take_state` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`pro_id`,`shop_id`,`place_order_date`,`ready_date`,`emp_id`,`single_state`,`take_state`) values (1,1,'2','2019-07-23','2019-07-23',3,1,2),(8,1,'3','2019-08-01','2019-08-18',10,1,2),(9,1,'3','2019-08-01','2019-08-18',10,2,0),(11,1,'3','2019-08-03','2019-08-18',7,1,1),(12,1,'3','2019-08-03','2019-08-25',7,1,1),(13,2,'3','2019-08-04','2019-08-27',7,0,0),(14,4,'3','2019-08-04','2019-08-20',11,1,1);

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_count` int(11) NOT NULL COMMENT '商品数量',
  `total_money` double NOT NULL COMMENT '总金额',
  `order_id` int(11) NOT NULL COMMENT '订单id',
  `order_type` int(11) NOT NULL COMMENT '采购或批发订单标识标识(0:采购,1:批发)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `order_detail` */

insert  into `order_detail`(`id`,`goods_id`,`goods_count`,`total_money`,`order_id`,`order_type`) values (1,1,20,20,2,1),(2,2,20,40,2,1),(3,3,10,30,1,1),(4,1,20,20,1,1),(5,2,10,100,8,0),(6,2,10,100,9,0),(7,3,1,3,9,0),(8,4,1,3,9,0),(9,2,1,10,11,0),(10,3,1,3,11,0),(11,4,1,3,11,0),(12,2,1,10,12,0),(13,3,1,3,12,0),(14,4,1,3,12,0),(15,2,40,400,13,1),(16,1,40,400,13,1),(17,2,40,400,14,1),(18,1,40,400,14,1),(19,1,1,3,17,1),(20,2,1,15,17,1),(21,3,1,4,17,1),(22,4,1,4,17,1),(23,4,1,4,1,0),(24,1,1,3,18,1),(25,1,18,36,13,0),(26,8,22,4400,14,0),(27,9,19,190,14,0),(28,8,5,1600,19,1),(29,9,2,378,19,1);

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permissions_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permissions` */

/*Table structure for table `pro_shop_relation` */

DROP TABLE IF EXISTS `pro_shop_relation`;

CREATE TABLE `pro_shop_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `pro_shop_relation` */

insert  into `pro_shop_relation`(`id`,`pro_id`,`shop_id`) values (1,1,2),(2,2,3),(3,1,3),(4,4,3),(5,3,3),(6,5,3);

/*Table structure for table `provider` */

DROP TABLE IF EXISTS `provider`;

CREATE TABLE `provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_name` varchar(30) NOT NULL,
  `pro_linkman` varchar(20) DEFAULT NULL,
  `pro_phone` varchar(11) NOT NULL,
  `pro_email` varchar(30) DEFAULT NULL,
  `bank` varchar(20) DEFAULT NULL,
  `bank_num` varchar(20) DEFAULT NULL,
  `pro_address` varchar(50) DEFAULT NULL,
  `should_money` double DEFAULT NULL,
  `pro_status` int(11) NOT NULL,
  `ready_money` double DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `provider` */

insert  into `provider`(`id`,`pro_name`,`pro_linkman`,`pro_phone`,`pro_email`,`bank`,`bank_num`,`pro_address`,`should_money`,`pro_status`,`ready_money`,`createDate`) values (1,'洋洋供应','张三','12333333333','122321@163.com','工商银行','1234567891123456789','广州市海珠区电大四楼',0.01,1,0,'2019-07-23 00:00:00'),(2,'随便供应','lisi','13333333333','333@163.com','建设银行','234534544467888888','广州天河区',2,1,1,'2019-07-23 00:00:00'),(3,'建建','贱贱','13838383438','null','null','null','广东省广州市海珠区',NULL,1,NULL,'2019-07-26 16:39:15'),(4,'小海供应','小海','11111111111','123@qq.com','工商银行','1234567891234567891','江南西',NULL,1,NULL,'2019-08-01 00:40:23'),(5,'北京烤鸭有限公司','佟年','18680631790','tongnian@qq.com','建设银行','1234567891234567891','北京二环路',NULL,0,NULL,'2019-08-04 11:15:29');

/*Table structure for table `role_per_relation` */

DROP TABLE IF EXISTS `role_per_relation`;

CREATE TABLE `role_per_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `per_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_per_relation` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) DEFAULT NULL,
  `role_name` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`id`,`role_name`) values (1,'超级管理员'),(2,'收银员'),(3,'总经理');

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(50) NOT NULL,
  `shop_account` varchar(50) NOT NULL,
  `shop_pwd` varchar(50) NOT NULL,
  `shop_linkman` varchar(20) NOT NULL,
  `shop_phone` varchar(11) DEFAULT NULL,
  `shop_logo` varchar(50) DEFAULT NULL,
  `shop_hours` varchar(20) DEFAULT NULL,
  `shop_advice` varchar(255) DEFAULT NULL,
  `shop_address` varchar(150) DEFAULT NULL,
  `shop_type_id` int(11) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `shop` */

insert  into `shop`(`id`,`shop_name`,`shop_account`,`shop_pwd`,`shop_linkman`,`shop_phone`,`shop_logo`,`shop_hours`,`shop_advice`,`shop_address`,`shop_type_id`,`createDate`,`parent_id`) values (2,'精致便利店','jz1234567','1234567','王大状','13422222222','\\61bf96e1a45146aab84d810db1f47a85.jpg','7:20-21:30','你要的精致都在这里','广东省广州市海珠区',11,'2019-06-14 00:00:00',3),(3,'洋洋便利店','yy1234567','1234567','洋哥哥','13438384389','','7:30-21:30','你要的洋气都在这里','广东省广州市海珠区',11,'2019-07-16 00:00:00',NULL),(4,'贱贱便利店','jj1234567','1234567','贱贱','13453443323','','7：30-21：30','你要的贱贱就在这里','广东省广州市海珠区',11,'2019-07-16 00:00:00',3),(6,'碧桂园茉莉花','dd1234567','34ea005aa732f5adeeeb0e1047b4b85b','李德元','13522222222',NULL,NULL,'中国广东清远','清远',11,'2019-07-18 09:32:36',3),(7,'昭君水果批发铺','wangzhaojun','6d3db10a5894d60143f01f36390326ad','王昭君','15290346780',NULL,NULL,'undefined','芳村大道',11,'2019-07-23 09:04:44',3),(8,'当当便利店','wangzhaojun1','6bf04232006eccbf449d0d8d355efdae','当当','13522222222',NULL,'7:30-21:30','当当当','东莞厚街',11,'2019-07-23 09:17:21',3),(9,'当当便利店1','wangzhaojun2','d103d2572102e1fe2a79b5926cdfc676','当当','13522222222',NULL,'11:54-12:54','拉拉','龙溪2',11,'2019-07-23 09:18:31',3);

/*Table structure for table `shop_type` */

DROP TABLE IF EXISTS `shop_type`;

CREATE TABLE `shop_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_type_name` varchar(20) NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `shop_type` */

insert  into `shop_type`(`id`,`shop_type_name`,`createDate`) values (11,'加盟店','2019-07-31 21:52:17');

/*Table structure for table `spec` */

DROP TABLE IF EXISTS `spec`;

CREATE TABLE `spec` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spec_name` varchar(20) NOT NULL,
  `spec_tmp_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `spec` */

insert  into `spec`(`id`,`spec_name`,`spec_tmp_id`) values (4,'尺码',1),(5,'功能',2),(6,'颜色',1),(7,'容器',2),(8,'腰围',3),(10,'尺码',9),(11,'颜色',9),(12,'尺码',10),(13,'颜色',10),(14,'尺码',11),(15,'颜色',11),(16,'口味',12),(17,'包装',12),(18,'重量',12),(20,'类型',14);

/*Table structure for table `spec_detail` */

DROP TABLE IF EXISTS `spec_detail`;

CREATE TABLE `spec_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spec_detail_name` varchar(50) NOT NULL,
  `spec_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `spec_detail` */

insert  into `spec_detail`(`id`,`spec_detail_name`,`spec_id`) values (1,'美白、抗敏感、护龈、口气清新',5),(2,'XXS',4),(4,'XS',4),(10,'S',4),(11,'M',4),(13,'罐子',17),(17,'粉色',6),(19,'L',4),(20,'XL',4),(22,'白色',6),(23,'按压式',7);

/*Table structure for table `spec_tmp` */

DROP TABLE IF EXISTS `spec_tmp`;

CREATE TABLE `spec_tmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spec_tmp_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `spec_tmp` */

insert  into `spec_tmp`(`id`,`spec_tmp_name`) values (1,'衣服'),(2,'牙膏'),(3,'牛仔裤'),(11,'鞋子'),(12,'薯片'),(14,'刮胡刀');

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_name` varchar(50) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `store` */

insert  into `store`(`id`,`store_name`,`shop_id`) values (1,'洋洋仓库',3),(2,'随便仓库',4),(3,'精致仓库',2),(5,'龙溪1号仓库',3);

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cashier_desk_id` int(11) NOT NULL,
  `place_order_date` date NOT NULL,
  `member_id` int(11) DEFAULT NULL,
  `original_count_money` double NOT NULL,
  `practical_money` double NOT NULL,
  `discount_money` double DEFAULT NULL,
  `sell_count` int(11) NOT NULL,
  `single_score` int(11) DEFAULT NULL,
  `emp_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `ticket` */

insert  into `ticket`(`id`,`cashier_desk_id`,`place_order_date`,`member_id`,`original_count_money`,`practical_money`,`discount_money`,`sell_count`,`single_score`,`emp_id`) values (1,2,'2019-07-31',NULL,70,70,0,3,0,11),(5,1,'2019-07-31',2,15,12.75,2.25,3,15,11),(6,1,'2019-07-31',2,15,12.75,2.25,3,15,11),(7,1,'2019-07-31',2,15,12.75,2.25,3,15,11),(11,3,'2019-08-02',2,5,4.25,0.75,1,5,11),(14,3,'2019-08-06',2,35,29.75,5.25,7,35,11);

/*Table structure for table `ticket_detail` */

DROP TABLE IF EXISTS `ticket_detail`;

CREATE TABLE `ticket_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `goods_count` int(11) NOT NULL,
  `total_money` double NOT NULL,
  `ticket_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `ticket_detail` */

insert  into `ticket_detail`(`id`,`goods_id`,`goods_count`,`total_money`,`ticket_id`) values (1,1,2,10,1),(2,3,4,20,1),(3,4,8,40,1),(4,3,2,10,5),(5,4,1,5,5),(6,4,2,10,6),(7,3,1,5,6),(8,4,2,10,7),(9,3,1,5,7),(10,4,1,5,11),(11,4,7,35,14);

/*Table structure for table `whole_order` */

DROP TABLE IF EXISTS `whole_order`;

CREATE TABLE `whole_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  `place_order_date` date DEFAULT NULL,
  `ready_date` date DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `single_state` int(11) DEFAULT NULL,
  `take_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `whole_order` */

insert  into `whole_order`(`id`,`customer_id`,`shop_id`,`place_order_date`,`ready_date`,`emp_id`,`single_state`,`take_state`) values (1,1,3,'2019-07-25','2018-12-31',1,1,2),(2,1,3,'2019-07-25','2019-07-25',1,0,0),(13,1,3,'2019-08-03','2019-08-31',1,0,0),(14,1,3,'2019-08-03','2019-08-31',1,0,0),(17,1,4,'2019-08-03','2019-08-12',11,1,1),(18,6,3,'2019-08-03','2019-08-19',11,1,2),(19,8,3,'2019-08-04','2019-08-04',11,1,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
