USE edu_manage;

-- 系统字典表 (sys_dict)
CREATE TABLE `sys_dict` (
  `dict_type` INT NOT NULL,
  `dict_value` INT NOT NULL,
  `dict_name` VARCHAR(255) NOT NULL,
  `dict_path` VARCHAR(255) NOT NULL,
  `status` TINYINT(1) NOT NULL,
  PRIMARY KEY (`dict_type`, `dict_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 文件表 (files)
CREATE TABLE `files` (
  `file_id` INT AUTO_INCREMENT PRIMARY KEY,
  `file_path` VARCHAR(255) NOT NULL,
  `upload_date` DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户表 (users)
CREATE TABLE `users` (
  `id` VARCHAR(20) PRIMARY KEY COMMENT'管理员账户ID',
  `password` VARCHAR(255) NOT NULL,
  `status` TINYINT(1) NOT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 部门表 (departments)
CREATE TABLE `departments` (
  `department_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '部门ID',
  `department_name` VARCHAR(255) NOT NULL UNIQUE COMMENT '部门名称',
  `update_time` DATETIME NOT NULL COMMENT '修改时间',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 员工表 (employees)
CREATE TABLE `employees` (
  `employee_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '员工ID',
  `name` VARCHAR(255) NOT NULL COMMENT '员工姓名',
  `gender` ENUM('W', 'M') NOT NULL COMMENT '性别W女M男',
  `image` VARCHAR(255) COMMENT '图像'，
  `role_type_id` INT COMMENT '员工角色类型，关联字典表 班主任 讲师 学工主管 教研主管',
  `entrydate` DATE COMMENT '入职时间',
  `department_id` INT COMMENT '部门ID',
  `create_time` DATETIME NOT NULL COMMENT '入职时间',
  `update_time` DATETIME NOT NULL COMMENT '修改时间', 
  FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 班级表 (classes)
CREATE TABLE `classes` (
  `class_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '班级ID',
  `class_name` VARCHAR(255) NOT NULL UNIQUE COMMENT '班级名称',
  `classroom` VARCHAR(255) COMMENT '教室',
  `start_time` DATE NOT NULL COMMENT '开课时间',
  `finish_time` DATE NOT NULL COMMENT '节课时间',
  `classmaster_id` VARCHAR(255) NOT NULL COMMENT '班主任ID',
  FOREIGN KEY (`classmaster_id`) REFERENCES `employees` (`employee_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 学员表 (students)
CREATE TABLE `students` (
  `student_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '学生ID',
  `phone` VARCHAR(20) UNIQUE COMMENT '手机号码',
  `class_id` INT COMMENT '班级ID',
  `name` VARCHAR(255) NOT NULL COMMENT '班级ID',
  `date_of_birth` DATE NOT NULL COMMENT '班级ID',
  `gender` ENUM('W', 'M') NOT NULL COMMENT '性别 W女M男',
  `top_degree` ENUM(1,2,3,4,5,6) COMMENT '最高学历 1初中2高中3大专4本科5硕士6博士',
  `disciplinary_number` INT COMMENT'违纪次数',
  `disciplinary_score` INT COMMENT'违纪扣分'
  `update_time` DATETIME NOT NULL COMMENT '修改时间', 
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 课程表 (courses)
CREATE TABLE `courses` (
  `course_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT'课程ID',
  `course_name` VARCHAR(255) NOT NULL UNIQUE COMMENT'课程名',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 课程安排表 (course_schedules)
CREATE TABLE `course_schedules` (
  `schedule_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT'课程安排表ID',
  `course_id` INT COMMENT'课程ID',
  `class_id` INT COMMENT'班级ID',
  `start_time` TIME NOT NULL COMMENT'开课时间',
  `end_time` TIME NOT NULL COMMENT'节课时间',
  `days_of_week` VARCHAR(20) COMMENT'上课日'
  `teacher_id` VARCHAR(255) NOT NULL COMMENT '教师ID',
  FOREIGN KEY (`teacher_id`) REFERENCES `employees` (`employee_id`)
  FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
