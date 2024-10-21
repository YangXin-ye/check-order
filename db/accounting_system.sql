/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql8
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3307
 Source Schema         : accounting_system

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 18/10/2024 16:27:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `url` varchar(255) DEFAULT NULL COMMENT '页面链接',
  `path` varchar(255) DEFAULT NULL COMMENT '链接',
  `parent` int DEFAULT NULL COMMENT '父级Id，没有父级为0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` (`id`, `name`, `label`, `icon`, `url`, `path`, `parent`) VALUES (1, 'main', '首页', 's-home', 'Home/Home', '/', 0);
INSERT INTO `menu` (`id`, `name`, `label`, `icon`, `url`, `path`, `parent`) VALUES (2, 'customer', '用户管理', 'user', NULL, NULL, 0);
INSERT INTO `menu` (`id`, `name`, `label`, `icon`, `url`, `path`, `parent`) VALUES (3, 'customerList', '客户列表', 'setting', 'customer/customerList', '/CustomerList', 2);
INSERT INTO `menu` (`id`, `name`, `label`, `icon`, `url`, `path`, `parent`) VALUES (4, 'commonCustomer', '公海客户', 'setting', 'customer/commonCustomer', '/CommonCustomer', 2);
INSERT INTO `menu` (`id`, `name`, `label`, `icon`, `url`, `path`, `parent`) VALUES (25, 'userInfo', '个人信息', 'user', '/upInfo', '/upInfo', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_accountion_record
-- ----------------------------
DROP TABLE IF EXISTS `t_accountion_record`;
CREATE TABLE `t_accountion_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL COMMENT '用户Id',
  `classify_id` int DEFAULT NULL COMMENT '分类Id',
  `type` int DEFAULT NULL COMMENT '1支出 2收入',
  `price` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete` tinyint NOT NULL DEFAULT '0' COMMENT '0未删除1删除',
  `year` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '年',
  `month` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '月',
  `day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '日',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收支明细';

-- ----------------------------
-- Records of t_accountion_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_budget
-- ----------------------------
DROP TABLE IF EXISTS `t_budget`;
CREATE TABLE `t_budget` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '1年预算2月预算',
  `budget_price` decimal(10,2) DEFAULT NULL COMMENT '预算金额',
  `user_id` int NOT NULL COMMENT '用户Id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete` tinyint NOT NULL DEFAULT '0' COMMENT '0未删除1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预算设置表';

-- ----------------------------
-- Records of t_budget
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_classify
-- ----------------------------
DROP TABLE IF EXISTS `t_classify`;
CREATE TABLE `t_classify` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` int NOT NULL COMMENT '用户Id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete` tinyint NOT NULL DEFAULT '0' COMMENT '0未删除1删除',
  `type` int DEFAULT NULL COMMENT '1支出 2收入',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收支分类';

-- ----------------------------
-- Records of t_classify
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_to_do_matter
-- ----------------------------
DROP TABLE IF EXISTS `t_to_do_matter`;
CREATE TABLE `t_to_do_matter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '提醒标题',
  `content` varchar(255) NOT NULL COMMENT '提醒内容',
  `user_id` int NOT NULL COMMENT '用户Id',
  `to_time` datetime NOT NULL COMMENT '提醒时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete` tinyint NOT NULL DEFAULT '0' COMMENT '0未删除1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='待办事项表';

-- ----------------------------
-- Records of t_to_do_matter
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `age` tinyint DEFAULT NULL COMMENT '年龄',
  `sex` tinyint DEFAULT NULL COMMENT '1男 2女',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete` tinyint NOT NULL DEFAULT '0' COMMENT '0未删除1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `u_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`u_id`, `username`, `password`, `name`, `avatar`, `gender`, `role`) VALUES (1, 'yxy', '123456', '杨芯', 'https://yxy-study.oss-cn-nanjing.aliyuncs.com/15d82062-43d9-4450-bfb5-fb81613d1348.jpg', '男', 'ADMIN');
INSERT INTO `users` (`u_id`, `username`, `password`, `name`, `avatar`, `gender`, `role`) VALUES (2, '123', '123', '123', 'https://yxy-study.oss-cn-nanjing.aliyuncs.com/fade2efb-ff55-4974-bd92-4cdf7a7fb4bd.jpg', '男', 'ADMIN');
INSERT INTO `users` (`u_id`, `username`, `password`, `name`, `avatar`, `gender`, `role`) VALUES (3, '1234', '123', '123', NULL, '男', 'ADMIN');
INSERT INTO `users` (`u_id`, `username`, `password`, `name`, `avatar`, `gender`, `role`) VALUES (4, '1234222', '81dc9bdb52d04dc20036dbd8313ed055', '1234', 'https://yxy-live-share.oss-cn-beijing.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20240419210622.jpg', '男', 'ADMIN');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
