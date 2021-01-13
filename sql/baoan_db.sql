/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.1.73
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 192.168.1.73:3306
 Source Schema         : baoan_db

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 13/01/2021 20:17:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_efile_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_efile_info`;
CREATE TABLE `sys_efile_info`  (
  `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `file_sid` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件流水号',
  `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件原名称',
  `file_path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(10) NULL DEFAULT NULL COMMENT '文件大小',
  `file_sort` int(10) NULL DEFAULT NULL COMMENT '文件排序号',
  `file_desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件描述',
  `is_binding` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否绑定: 1:已绑定、2:未绑定',
  `business_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务key值',
  `created_dt` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_efile_store
-- ----------------------------
DROP TABLE IF EXISTS `sys_efile_store`;
CREATE TABLE `sys_efile_store`  (
  `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储名称',
  `store_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储编码',
  `store_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储路径',
  `store_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储描述',
  `is_virtual` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否虚拟路径1:是，2:否',
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_dt` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_dt` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `psid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父类Id',
  `state_type` int(10) NULL DEFAULT NULL COMMENT '状态标识:1 启动,2 禁止',
  `store_identifier` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '存储唯一标识符',
  PRIMARY KEY (`sid`) USING BTREE,
  UNIQUE INDEX `store_identifier`(`store_identifier`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_verification_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_verification_code`;
CREATE TABLE `sys_verification_code`  (
  `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `created_dt` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_verification_code
-- ----------------------------
INSERT INTO `sys_verification_code` VALUES ('798996344856510464', '1QKU', '2021-01-13 19:26:13');
INSERT INTO `sys_verification_code` VALUES ('798996703310118912', 'QXSQ', '2021-01-13 19:27:39');
INSERT INTO `sys_verification_code` VALUES ('798996705562460160', 'G4X3', '2021-01-13 19:27:39');
INSERT INTO `sys_verification_code` VALUES ('798996706338406400', 'A4SW', '2021-01-13 19:27:39');
INSERT INTO `sys_verification_code` VALUES ('798996707252764672', 'UQ12', '2021-01-13 19:27:40');
INSERT INTO `sys_verification_code` VALUES ('798996708041293824', 'E8HC', '2021-01-13 19:27:40');
INSERT INTO `sys_verification_code` VALUES ('798996708884348928', 'TXV7', '2021-01-13 19:27:40');

-- ----------------------------
-- Table structure for ucas_auth_privilege
-- ----------------------------
DROP TABLE IF EXISTS `ucas_auth_privilege`;
CREATE TABLE `ucas_auth_privilege`  (
  `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `privilege_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `privilege_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `psid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父Id',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单路由',
  `order_rank` int(3) NULL DEFAULT NULL COMMENT '序号',
  `privilege_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限类型1:项目，2菜单，3按钮',
  `privilege_description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '2' COMMENT '状态(1:禁用,2:启用)',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_dt` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `version` int(9) NULL DEFAULT 1 COMMENT '版本号',
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_dt` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `zone_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域机构',
  `icon_name` int(15) NULL DEFAULT NULL COMMENT '图标名称',
  `value1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段1',
  `value2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段2',
  `value3` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段3',
  `delete_flag` int(1) NULL DEFAULT 1 COMMENT '删除标识(1:未删除，2：已删除)',
  `depth` int(64) NULL DEFAULT NULL COMMENT '权限层级',
  `unique_sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作日志记录uuid',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ucas_auth_role_r2_privilege
-- ----------------------------
DROP TABLE IF EXISTS `ucas_auth_role_r2_privilege`;
CREATE TABLE `ucas_auth_role_r2_privilege`  (
  `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `privilege_sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限Id',
  `user_sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户Id',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_dt` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `version` int(9) NULL DEFAULT 1 COMMENT '版本号',
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_dt` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `zone_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域机构',
  `reserve1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段1',
  `reserve2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段2',
  `reserve3` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ucas_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `ucas_auth_user`;
CREATE TABLE `ucas_auth_user`  (
  `sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `user_pin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别（1:男,2:女）',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '常用电话',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '2' COMMENT '状态(1:禁用,2:启用)',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_dt` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `version` int(9) NULL DEFAULT 1 COMMENT '版本号',
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_dt` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `zone_org_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区域机构',
  `organiztion_sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门sid',
  `value1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段1',
  `value2` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段2',
  `value3` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备用字段3',
  `delete_flag` int(1) NULL DEFAULT 1 COMMENT '删除标识(1:未删除，2：已删除)',
  `session_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会话Id',
  `user_category` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户类别(系统管理员\\安全保密管理员\\安全审计员\\普通用户)',
  `unique_sid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作日志记录uuid',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ucas_auth_user
-- ----------------------------
INSERT INTO `ucas_auth_user` VALUES ('1', 'superadmin', '超级管理员', '123456', NULL, '', NULL, NULL, '2', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
