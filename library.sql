/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : library

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 24/12/2021 11:09:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `book_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK，图书id',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '国际标准书号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '图书名称',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '作者',
  `press` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '出版社',
  `lend_num` int(0) NULL DEFAULT 0 COMMENT '借阅次数',
  `type_id` int(0) NULL DEFAULT 1 COMMENT '图书分类',
  `borrow_num` int(0) NULL DEFAULT 0 COMMENT '可借阅数',
  `number` int(0) NULL DEFAULT 0 COMMENT '库存(默认0)',
  `lend_stu` int(0) NULL DEFAULT 1 COMMENT '1=可借状态，-1=不可借状态，默认1',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'https://library-management-1305004688.cos.ap-guangzhou.myqcloud.com/noimg.jpg' COMMENT '图片url',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` bigint(0) NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`book_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (2, '7-5304-2130-1', '数据库系统', '臧兰', '北京科学技术出版社', 2, 3, 0, 4, 1, 'http://library-management-1305004688.cos.ap-nanjing.myqcloud.com/1640314892139223.jpg', '王五', 1624609038307);
INSERT INTO `book` VALUES (4, '978-7-5641-0769-7', 'Java and XML = Java和XML', 'McLaughlin, Brett D. Edelson, Justin', 'Southeast University Press', 9, 1, 6, 1, 1, 'http://library-management-1305004688.cos.ap-nanjing.myqcloud.com/1640314947722689.jpg', 'admin', 1623747053568);
INSERT INTO `book` VALUES (6, '978-7-100-11969-6', '百年孤独', '马尔克斯著 范晔译', '南海出版公司', 20, 1, 4, 2, 1, 'http://library-management-1305004688.cos.ap-nanjing.myqcloud.com/1640315011930882.jpeg', 'admin', 1623747053568);
INSERT INTO `book` VALUES (21, '978-7-5641-0769-7', '三国演义', '罗贯中', '人民文学出版社', 0, 1, 3, 3, 1, 'http://library-management-1305004688.cos.ap-nanjing.myqcloud.com/1640314379667616.jpeg', '王五', 1624612343835);
INSERT INTO `book` VALUES (23, '978-7-5641-0769-7', '红楼梦', '曹雪芹著 高鹗著', '商务印书馆', 0, 4, 2, 4, 1, 'http://library-management-1305004688.cos.ap-nanjing.myqcloud.com/1640314517811270.jpeg', '王五', 1624694954046);
INSERT INTO `book` VALUES (26, '978-7-5641-0769-7', '论十大关系', '毛泽东', '北京出版社', 0, 5, 6, 6, 1, 'http://library-management-1305004688.cos.ap-nanjing.myqcloud.com/1640313853659257.jpg', '管理员', 1640313591738);

-- ----------------------------
-- Table structure for book_record
-- ----------------------------
DROP TABLE IF EXISTS `book_record`;
CREATE TABLE `book_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK，图书记录id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '记录人',
  `record_type` smallint(0) NOT NULL DEFAULT 0 COMMENT '记录类型(1=借阅，2=归还)',
  `book_id` int(0) NULL DEFAULT 0 COMMENT '图书id',
  `book_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '图书名称',
  `create_time` bigint(0) NULL DEFAULT 0 COMMENT '记录时间',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_record
-- ----------------------------
INSERT INTO `book_record` VALUES (1, 1, 2, 5, '红楼梦', 1623747053568);
INSERT INTO `book_record` VALUES (2, 3, 2, 8, '三国演义', 1623747053568);
INSERT INTO `book_record` VALUES (3, 3, 2, 10, '数据库系统', 1623747053568);
INSERT INTO `book_record` VALUES (4, 9, 2, 15, '百年孤独', 1623747053568);
INSERT INTO `book_record` VALUES (5, 4, 2, 8, NULL, 1624282886160);
INSERT INTO `book_record` VALUES (6, 7, 2, 3, NULL, 1624285861238);
INSERT INTO `book_record` VALUES (7, 17, 2, 4, '生物学', 1624286089802);
INSERT INTO `book_record` VALUES (8, 17, 2, 6, '西游记', 1624286120220);
INSERT INTO `book_record` VALUES (9, 17, 2, 2, '数据库系统', 1624286512895);
INSERT INTO `book_record` VALUES (10, 17, 2, 8, '生物学', 1624286604483);
INSERT INTO `book_record` VALUES (11, 8, 2, 8, '生物学', 1624286654936);
INSERT INTO `book_record` VALUES (12, 5, 2, 8, '生物学', 1624286660055);
INSERT INTO `book_record` VALUES (13, 9, 2, 5, '红楼梦', 1624414372920);
INSERT INTO `book_record` VALUES (14, 17, 2, 1, 'Java and XML = Java和XML', 1624527771407);
INSERT INTO `book_record` VALUES (15, 17, 2, 4, '红楼梦', 1624528964739);
INSERT INTO `book_record` VALUES (16, 17, 2, 6, '百年孤独', 1624528976305);
INSERT INTO `book_record` VALUES (17, 17, 2, 6, '百年孤独', 1624530457250);
INSERT INTO `book_record` VALUES (18, 17, 2, 4, 'Java and XML = Java和XML', 1624531307794);
INSERT INTO `book_record` VALUES (19, 17, 2, 6, '百年孤独', 1624531360873);
INSERT INTO `book_record` VALUES (20, 17, 2, 7, '百年孤独', 1624531388774);
INSERT INTO `book_record` VALUES (21, 17, 2, 8, 'Java and XML = Java和XML', 1624531408160);
INSERT INTO `book_record` VALUES (22, 17, 2, 7, '百年孤独', 1624531427008);
INSERT INTO `book_record` VALUES (23, 17, 2, 6, '百年孤独', 1624531536747);
INSERT INTO `book_record` VALUES (24, 17, 2, 8, 'Java and XML = Java和XML', 1624531569397);
INSERT INTO `book_record` VALUES (25, 9, 2, 4, 'Java and XML = Java和XML', 1624531961754);
INSERT INTO `book_record` VALUES (26, 17, 2, 8, '三国演义', 1624612288463);
INSERT INTO `book_record` VALUES (27, 17, 1, 23, '红楼梦', 1624765097534);
INSERT INTO `book_record` VALUES (28, 17, 1, 1, '红楼梦1', 1624765183670);
INSERT INTO `book_record` VALUES (29, 17, 2, 4, 'Java and XML = Java和XML', 1624765186406);
INSERT INTO `book_record` VALUES (30, 17, 2, 5, '红楼梦', 1624765188408);
INSERT INTO `book_record` VALUES (31, 17, 1, 3, '数据库系统', 1624765199865);
INSERT INTO `book_record` VALUES (32, 25, 1, 23, '红楼梦', 1625045053837);

-- ----------------------------
-- Table structure for book_type
-- ----------------------------
DROP TABLE IF EXISTS `book_type`;
CREATE TABLE `book_type`  (
  `type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK，分类id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类名',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_type
-- ----------------------------
INSERT INTO `book_type` VALUES (1, '外文图书', 1623747053568, 'admin');
INSERT INTO `book_type` VALUES (2, '中文图书', 1623747053568, 'admin');
INSERT INTO `book_type` VALUES (3, '计算机图书', 1623747053568, 'admin');
INSERT INTO `book_type` VALUES (4, '文学', 1623747053568, 'admin');
INSERT INTO `book_type` VALUES (5, '历史', 1623747053568, 'admin');

-- ----------------------------
-- Table structure for lend_limit
-- ----------------------------
DROP TABLE IF EXISTS `lend_limit`;
CREATE TABLE `lend_limit`  (
  `limit_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK，借书限制id',
  `role_id` int(0) NOT NULL COMMENT '角色id',
  `max_number` int(0) NULL DEFAULT 0 COMMENT '最大借书量',
  `max_time` int(0) NULL DEFAULT 0 COMMENT '最大借书时长(单位：天)',
  PRIMARY KEY (`limit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '借阅限制表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lend_limit
-- ----------------------------
INSERT INTO `lend_limit` VALUES (1, 1, 3, 4);
INSERT INTO `lend_limit` VALUES (2, 2, 5, 3);
INSERT INTO `lend_limit` VALUES (3, 3, 2, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK，角色id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '角色名',
  `create_time` int(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '学生', 54271912);
INSERT INTO `role` VALUES (2, '老师', 54271912);
INSERT INTO `role` VALUES (3, '其他用户', 54271912);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'PK，用户id',
  `role_id` int(0) NOT NULL DEFAULT 3 COMMENT '角色名id',
  `usr` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户登陆帐号',
  `pwd` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户名',
  `auth` smallint(0) NULL DEFAULT 0 COMMENT '权限(1=管理员，0=用户，默认0)',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
  `sex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '未知' COMMENT '性别(男，女，未知)',
  `create_time` bigint(0) NULL DEFAULT 0 COMMENT '创建时间(时间毫秒数)',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `FK_ROLE`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (9, 3, '12', '43', '王五', 1, '192@qq.com', '男', 1623747053568);
INSERT INTO `user` VALUES (13, 2, '1211', '123', '李四', 0, '12480@qq.com', '女', 1624613604997);
INSERT INTO `user` VALUES (16, 2, '421', '312', '学生2', 0, '2940@qq.com', '未知', 1624613604997);
INSERT INTO `user` VALUES (17, 2, 'admin', '123', '管理员', 1, ' 12480@qq.com', '女', 1624613604997);
INSERT INTO `user` VALUES (25, 1, 'zhangsan', '123', '学生1', 0, 'gasadc12y@163.com', '女', 1624615248298);
INSERT INTO `user` VALUES (26, 2, '12321321', '12', 'eqw', 0, '31234@qq.com', '男', 1624630123716);
INSERT INTO `user` VALUES (29, 3, '3213.0', '1231.0', '312.0', 0, '312.0', '男', 1624613604997);
INSERT INTO `user` VALUES (31, 3, '3213', '1231', '312', 0, '312', '男', 1624710814415);
INSERT INTO `user` VALUES (32, 3, '3123', '412', '21', 0, '412', '女', 1624759309908);
INSERT INTO `user` VALUES (33, 3, 'user', '123', '用户2', 0, '412', '女', 1624759309908);
INSERT INTO `user` VALUES (34, 3, 'user1', '123', '学生3', 0, '74912@qq.com', '女', 1624787972272);

SET FOREIGN_KEY_CHECKS = 1;
