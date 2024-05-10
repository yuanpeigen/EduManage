/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100414
 Source Host           : localhost:3306
 Source Schema         : edu_manage

 Target Server Type    : MySQL
 Target Server Version : 100414
 File Encoding         : 65001

 Date: 10/05/2024 11:55:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `class_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名称',
  `classroom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教室',
  `start_time` date NOT NULL COMMENT '开课时间',
  `finish_time` date NOT NULL COMMENT '节课时间',
  `classmaster_id` int(11) NOT NULL COMMENT '班主任ID',
  PRIMARY KEY (`class_id`) USING BTREE,
  UNIQUE INDEX `class_name`(`class_name`) USING BTREE,
  INDEX `classmaster_id`(`classmaster_id`) USING BTREE,
  CONSTRAINT `classes_ibfk_1` FOREIGN KEY (`classmaster_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (2, '软件2班', '202', '2024-05-05', '2024-05-13', 6);
INSERT INTO `classes` VALUES (3, '软件1班', '203', '2024-05-05', '2024-05-01', 6);
INSERT INTO `classes` VALUES (8, '软件3班', '102', '2024-04-30', '2024-05-01', 6);

-- ----------------------------
-- Table structure for course_schedules
-- ----------------------------
DROP TABLE IF EXISTS `course_schedules`;
CREATE TABLE `course_schedules`  (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程安排表ID',
  `course_id` int(11) NOT NULL COMMENT '课程ID',
  `class_id` int(11) NOT NULL COMMENT '班级ID',
  `teacher_id` int(11) NOT NULL COMMENT '教师ID',
  PRIMARY KEY (`schedule_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  INDEX `course_id`(`course_id`) USING BTREE,
  INDEX `class_id`(`class_id`) USING BTREE,
  CONSTRAINT `course_schedules_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `employees` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_schedules_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `course_schedules_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_schedules
-- ----------------------------
INSERT INTO `course_schedules` VALUES (1, 11, 2, 10);
INSERT INTO `course_schedules` VALUES (2, 11, 3, 7);
INSERT INTO `course_schedules` VALUES (6, 8, 3, 7);
INSERT INTO `course_schedules` VALUES (7, 8, 2, 7);
INSERT INTO `course_schedules` VALUES (8, 9, 3, 10);
INSERT INTO `course_schedules` VALUES (9, 7, 3, 7);
INSERT INTO `course_schedules` VALUES (10, 7, 2, 10);

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `course_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名',
  `start_time` time(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` time(0) NULL DEFAULT NULL COMMENT '结束时间',
  `days_of_week` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上课日',
  PRIMARY KEY (`course_id`) USING BTREE,
  UNIQUE INDEX `course_name`(`course_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES (7, '形式语言', '00:41:36', '20:41:33', '1');
INSERT INTO `courses` VALUES (8, 'c++', NULL, NULL, '1,3');
INSERT INTO `courses` VALUES (9, 'java', NULL, '20:26:31', '1,2,3');
INSERT INTO `courses` VALUES (11, 'web', '21:34:55', '22:34:58', '2,4');

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
  `department_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `department_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`department_id`) USING BTREE,
  UNIQUE INDEX `department_name`(`department_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES (4, '学工部', '2024-05-09 14:45:45');
INSERT INTO `departments` VALUES (5, '教研部', '2024-05-09 14:47:24');
INSERT INTO `departments` VALUES (6, '行政部', '2024-05-09 14:48:32');
INSERT INTO `departments` VALUES (7, '后勤部', '2024-05-09 14:49:33');

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
  `gender` tinyint(4) NOT NULL COMMENT '性别1男2女',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图像',
  `job` tinyint(4) NULL DEFAULT NULL COMMENT '员工角色类型，1 班主任 2讲师 3学工主管 4教研主管',
  `entrydate` date NULL DEFAULT NULL COMMENT '入职时间',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime(0) NOT NULL COMMENT '入职时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`employee_id`) USING BTREE,
  INDEX `department_id`(`department_id`) USING BTREE,
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (6, '万晓珑', 2, 'https://zombie-web.oss-cn-beijing.aliyuncs.com/a1e300a4-b469-4d2d-b834-2c14ef2021b8.png', 1, '2024-05-19', 4, '2024-05-04 22:48:19', '2024-05-09 15:55:48');
INSERT INTO `employees` VALUES (7, '姜薇', 2, 'https://zombie-web.oss-cn-beijing.aliyuncs.com/ee867255-3991-45f8-a7d9-ee91ca547bd0.png', 2, '2024-05-11', 5, '2024-05-04 22:48:27', '2024-05-09 15:55:20');
INSERT INTO `employees` VALUES (10, '辛国栋', 1, 'https://zombie-web.oss-cn-beijing.aliyuncs.com/47ad0956-67ea-4bf3-8fd7-c125a05c2fe9.jpg', 2, '2024-05-12', 5, '2024-05-07 22:34:32', '2024-05-09 14:49:53');

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files`  (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `upload_date` datetime(0) NOT NULL,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of files
-- ----------------------------

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `student_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `class_id` int(11) NULL DEFAULT NULL COMMENT '班级ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名称',
  `date_of_birth` date NOT NULL COMMENT '出生日期',
  `gender` tinyint(4) NOT NULL COMMENT '性别 1女2男',
  `top_degree` tinyint(4) NULL DEFAULT NULL COMMENT '最高学历 1初中2高中3大专4本科5硕士6博士',
  `disciplinary_number` int(11) NULL DEFAULT NULL COMMENT '违纪次数',
  `disciplinary_score` int(11) NULL DEFAULT NULL COMMENT '违纪扣分',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  INDEX `class_id`(`class_id`) USING BTREE,
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2022211856 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES (2022211840, '13394485198', 2, '李政道', '2004-05-06', 1, 3, 2, 32, '2024-05-09 16:00:02');
INSERT INTO `students` VALUES (2022211855, '15802581002', 2, '章彬', '2024-05-09', 1, 4, 2, 5, '2024-05-09 15:56:20');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_type` int(11) NOT NULL,
  `dict_value` int(11) NOT NULL,
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dict_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`dict_type`, `dict_value`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员账户ID',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('admin', '123');
INSERT INTO `users` VALUES ('admin2', '123');

SET FOREIGN_KEY_CHECKS = 1;
