/*
Navicat MySQL Data Transfer

Source Server         : pjjlt
Source Server Version : 50727
Source Host           : 127.0.0.1:3306
Source Database       : easy_file

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2019-11-10 11:31:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for project_file
-- ----------------------------
DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文档id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `folder_id` bigint(20) NOT NULL COMMENT '文件夹ID',
  `file_name` varchar(50) NOT NULL COMMENT '文件名称',
  `file_status` varchar(50) NOT NULL COMMENT '文件状态(DRAFT 草稿、NORMAL 正式文章、RECYCLE 回收站)',
  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='文档表';

-- ----------------------------
-- Records of project_file
-- ----------------------------
INSERT INTO `project_file` VALUES ('1', '4', '5', '文件测试', 'RECYCLE', '1', '2019-10-26 09:08:45', '2019-10-26 13:09:22', '');
INSERT INTO `project_file` VALUES ('2', '3', '8', '文件测试1', 'RECYCLE', '1', '2019-10-26 09:09:03', '2019-10-26 13:09:22', '');
INSERT INTO `project_file` VALUES ('3', '4', '10', '文件测试2', 'RECYCLE', '1', '2019-10-26 09:09:29', '2019-10-26 13:09:22', '');
INSERT INTO `project_file` VALUES ('4', '2', '2', '文章1', 'NORMAL', '1', '2019-10-26 16:00:31', '2019-10-26 16:13:03', '');
INSERT INTO `project_file` VALUES ('5', '2', '2', '文章2', 'NORMAL', '1', '2019-10-26 16:00:48', '2019-10-26 16:13:03', '更新了');
INSERT INTO `project_file` VALUES ('6', '2', '2', '文章3', 'NORMAL', '1', '2019-10-26 16:00:54', '2019-10-26 16:13:04', '');
INSERT INTO `project_file` VALUES ('7', '2', '2', '文章4', 'NORMAL', '1', '2019-10-26 16:00:57', '2019-10-26 16:00:57', '');

-- ----------------------------
-- Table structure for project_file_detail
-- ----------------------------
DROP TABLE IF EXISTS `project_file_detail`;
CREATE TABLE `project_file_detail` (
  `id` bigint(20) NOT NULL COMMENT '文档详情id(同file的id)',
  `detail` longtext NOT NULL COMMENT '文档详情',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档详情表';

-- ----------------------------
-- Records of project_file_detail
-- ----------------------------
INSERT INTO `project_file_detail` VALUES ('4', '###', '2019-10-26 16:00:31', '2019-10-26 16:00:31', '');
INSERT INTO `project_file_detail` VALUES ('5', '###', '2019-10-26 16:00:48', '2019-10-26 16:08:05', '');
INSERT INTO `project_file_detail` VALUES ('6', '###', '2019-10-26 16:00:54', '2019-10-26 16:00:54', '');
INSERT INTO `project_file_detail` VALUES ('7', '###', '2019-10-26 16:00:58', '2019-10-26 16:00:58', '');

-- ----------------------------
-- Table structure for project_folder
-- ----------------------------
DROP TABLE IF EXISTS `project_folder`;
CREATE TABLE `project_folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件夹id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父文件夹ID',
  `folder_name` varchar(50) NOT NULL COMMENT '文件夹名称',
  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `description` longtext COMMENT '文件夹描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='文件夹表';

-- ----------------------------
-- Records of project_folder
-- ----------------------------
INSERT INTO `project_folder` VALUES ('2', '2', '0', 'folder文件夹2', '1', null, '2019-10-26 10:57:05', '2019-10-26 15:59:06', '');
INSERT INTO `project_folder` VALUES ('3', '1', '0', 'folder文件夹3', '1', null, '2019-10-26 10:57:14', '2019-10-26 10:57:14', '');
INSERT INTO `project_folder` VALUES ('4', '1', '0', 'folder文件夹4', '1', null, '2019-10-26 10:57:17', '2019-10-26 10:57:17', '');
INSERT INTO `project_folder` VALUES ('6', '1', '2', 'folder文件夹6', '1', null, '2019-10-26 11:00:42', '2019-10-26 11:00:42', '');
INSERT INTO `project_folder` VALUES ('7', '1', '2', 'folder文件夹7', '1', null, '2019-10-26 11:00:45', '2019-10-26 11:00:45', '');

-- ----------------------------
-- Table structure for project_info
-- ----------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `project_name` varchar(50) NOT NULL COMMENT '项目名称',
  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `public_flag` bit(1) DEFAULT b'0' COMMENT '是否公开（0私有的 1公开的）',
  `description` longtext COMMENT '项目描述',
  `project_logo` varchar(100) DEFAULT NULL COMMENT '项目logo',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='项目表';

-- ----------------------------
-- Records of project_info
-- ----------------------------
INSERT INTO `project_info` VALUES ('1', 'test项目', '1', '\0', '1', null, '2019-10-25 15:07:31', '2019-10-25 15:07:31', '');
INSERT INTO `project_info` VALUES ('2', '新建项目', '1', '\0', null, null, '2019-10-26 09:01:08', '2019-10-26 09:01:08', '');
INSERT INTO `project_info` VALUES ('3', '新建项目1', '1', '\0', null, null, '2019-10-26 09:01:13', '2019-10-26 09:01:13', '');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` bit(1) DEFAULT b'0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', 'test权限', '0', '0', '#', '', '\0', 'test', '#', '2019-10-22 09:53:02', '2019-10-22 09:53:11', '更新了菜单');
INSERT INTO `sys_menu` VALUES ('2', '一级菜单1哈哈哈', '0', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:01:02', '2019-10-24 17:24:18', '');
INSERT INTO `sys_menu` VALUES ('3', '一级菜单2', '0', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:01:20', '2019-10-24 17:01:20', '');
INSERT INTO `sys_menu` VALUES ('4', '一级菜单3', '0', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:01:23', '2019-10-24 17:01:23', '');
INSERT INTO `sys_menu` VALUES ('5', '二级菜单11', '2', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:02:18', '2019-10-24 17:25:59', '');
INSERT INTO `sys_menu` VALUES ('6', '二级菜单12', '2', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:02:24', '2019-10-24 17:02:24', '');
INSERT INTO `sys_menu` VALUES ('7', '二级菜单13', '2', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:02:31', '2019-10-24 17:02:31', '');
INSERT INTO `sys_menu` VALUES ('8', '二级菜单21', '3', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:03:12', '2019-10-24 17:25:28', '');
INSERT INTO `sys_menu` VALUES ('9', '二级菜单22', '3', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:03:17', '2019-10-24 17:03:17', '');
INSERT INTO `sys_menu` VALUES ('10', '三级菜单111哈哈哈', '5', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:03:33', '2019-10-24 17:24:25', '');
INSERT INTO `sys_menu` VALUES ('11', '三级菜单112', '5', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:03:36', '2019-10-24 17:25:24', '');
INSERT INTO `sys_menu` VALUES ('13', '三级菜单211哈哈哈', '8', '0', '#', '', '\0', 'perms', '#', '2019-10-24 17:03:53', '2019-10-24 17:25:54', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'test角色', '2019-10-22 09:46:27', '2019-10-22 09:52:42', '更新了角色');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2', '1', '1', '2019-10-25 15:12:40', '2019-10-25 15:12:40', null);
INSERT INTO `sys_role_menu` VALUES ('3', '1', '2', '2019-10-25 15:12:40', '2019-10-25 15:12:40', null);
INSERT INTO `sys_role_menu` VALUES ('4', '1', '3', '2019-10-25 15:12:40', '2019-10-25 15:12:40', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `user_type` bit(1) DEFAULT b'0' COMMENT '用户类型(1为管理员)',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `head_img_url` varchar(250) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `status` bit(1) DEFAULT b'0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登陆时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'majian', 'majianUpdate', '', '962876933@qq.com', '13932125194', '', 'b3a5e2cf6835ff603ab9261f377ad6cb', '\0', '127.0.0.1', '2019-10-22 17:46:04', '2019-10-17 17:36:25', '2019-10-22 17:37:38', null);
INSERT INTO `sys_user` VALUES ('2', 'majian1', 'majian1', '\0', '962876934@qq.com', '13932112345', '', 'e06734f75dcfc657e396202f3d7ec9b2', '\0', '127.0.0.1', '2019-10-21 14:49:42', '2019-10-21 14:49:42', '2019-10-22 10:22:51', null);
INSERT INTO `sys_user` VALUES ('3', '_pjjlt', '_pjjlt', '\0', '9628769334@qq.com', '', '', 'e06734f75dcfc657e396202f3d7ec9b2', '\0', '127.0.0.1', '2019-10-26 09:02:55', '2019-10-22 16:44:53', '2019-10-22 16:44:53', null);

-- ----------------------------
-- Table structure for sys_user_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_project`;
CREATE TABLE `sys_user_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `project_id` bigint(20) NOT NULL COMMENT '项目ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户项目关系表';

-- ----------------------------
-- Records of sys_user_project
-- ----------------------------
INSERT INTO `sys_user_project` VALUES ('2', '1', '2', '2019-10-26 09:01:08', '2019-10-26 09:01:08', null);
INSERT INTO `sys_user_project` VALUES ('3', '1', '3', '2019-10-26 09:01:13', '2019-10-26 09:01:13', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('3', '1', '1', '1', '2019-10-25 15:09:52', '2019-10-25 15:09:52', null);

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `token` varchar(50) NOT NULL COMMENT '令牌',
  `expire_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户令牌表表';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('7', '1', '94d64ff6fc6744b6ad929c10661e9153', '2019-10-31 17:14:02', '2019-10-22 16:39:35', '2019-10-22 17:18:07', null);
INSERT INTO `sys_user_token` VALUES ('9', '1', '123', '2021-03-20 10:18:43', '2019-10-22 17:46:03', '2019-10-24 10:18:58', null);
INSERT INTO `sys_user_token` VALUES ('10', '3', '826fb06d9a544bbaaea05b85a77066fa', '2019-10-27 15:02:55', '2019-10-26 09:02:57', '2019-10-26 16:02:50', null);
