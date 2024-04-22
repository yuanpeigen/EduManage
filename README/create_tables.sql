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
  `phone` VARCHAR(20) PRIMARY KEY,
  `password` VARCHAR(255) NOT NULL,
  `photo` INT,
  `status` TINYINT(1) NOT NULL,
  `user_type` ENUM('A', 'B') NOT NULL,
  FOREIGN KEY (`photo`) REFERENCES `files` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 部门表 (departments)
CREATE TABLE `departments` (
  `department_id` INT AUTO_INCREMENT PRIMARY KEY,
  `department_name` VARCHAR(255) NOT NULL UNIQUE,
  `description` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 员工表 (employees)
CREATE TABLE `employees` (
  `employee_id` INT AUTO_INCREMENT PRIMARY KEY,
  `phone` VARCHAR(20),
  `department_id` INT,
  `name` VARCHAR(255) NOT NULL,
  `role_type_id` INT,
  FOREIGN KEY (`phone`) REFERENCES `users` (`phone`),
  FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 班级表 (classes)
CREATE TABLE `classes` (
  `class_id` INT AUTO_INCREMENT PRIMARY KEY,
  `class_name` VARCHAR(255) NOT NULL UNIQUE,
  `department_id` INT,
  `description` TEXT,
  FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 学员表 (students)
CREATE TABLE `students` (
  `student_id` INT AUTO_INCREMENT PRIMARY KEY,
  `phone` VARCHAR(20),
  `class_id` INT,
  `name` VARCHAR(255) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `email` VARCHAR(255),
  FOREIGN KEY (`phone`) REFERENCES `users` (`phone`),
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 课程表 (courses)
CREATE TABLE `courses` (
  `course_id` INT AUTO_INCREMENT PRIMARY KEY,
  `course_name` VARCHAR(255) NOT NULL UNIQUE,
  `description` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 课程安排表 (course_schedules)
CREATE TABLE `course_schedules` (
  `schedule_id` INT AUTO_INCREMENT PRIMARY KEY,
  `course_id` INT,
  `class_id` INT,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;