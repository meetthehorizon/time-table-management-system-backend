DROP TABLE IF EXISTS `attendance`;

CREATE TABLE
  `attendance` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `slot_id` bigint DEFAULT NULL,
    `student_id` bigint DEFAULT NULL,
    `is_present` tinyint (1) DEFAULT NULL,
    `curr_date` date DEFAULT NULL,
    `remark` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `slot_id` (`slot_id`),
    KEY `student_id` (`student_id`),
    CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`slot_id`) REFERENCES `slots` (`id`) ON DELETE CASCADE,
    CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `courses`;

CREATE TABLE
  `courses` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `section_id` bigint DEFAULT NULL,
    `subject_req_id` int DEFAULT NULL,
    `teacher_req_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `section_id` (`section_id`),
    KEY `subject_req_id` (`subject_req_id`),
    KEY `teacher_req_id` (`teacher_req_id`),
    CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`) ON DELETE CASCADE,
    CONSTRAINT `courses_ibfk_2` FOREIGN KEY (`subject_req_id`) REFERENCES `subject_req` (`id`) ON DELETE CASCADE,
    CONSTRAINT `courses_ibfk_3` FOREIGN KEY (`teacher_req_id`) REFERENCES `teacher_req` (`id`) ON DELETE SET NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `employee`;

CREATE TABLE
  `employee` (
    `id` bigint DEFAULT NULL,
    `school_id` bigint DEFAULT NULL,
    KEY `id` (`id`),
    KEY `school_id` (`school_id`),
    CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE SET NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `enrollment`;

CREATE TABLE
  `enrollment` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `student_id` bigint DEFAULT NULL,
    `section_id` bigint DEFAULT NULL,
    `enroll_year` year DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `student_id` (`student_id`),
    KEY `section_id` (`section_id`),
    CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
    CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`) ON DELETE CASCADE
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `leaves`;

CREATE TABLE
  `leaves` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `enroll_id` bigint DEFAULT NULL,
    `comment` varchar(500) DEFAULT NULL,
    `drive_link` varchar(100) DEFAULT NULL,
    `curr_date` date DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `enroll_id` (`enroll_id`),
    CONSTRAINT `leaves_ibfk_1` FOREIGN KEY (`enroll_id`) REFERENCES `enrollment` (`id`) ON DELETE CASCADE
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `parent`;

CREATE TABLE
  `parent` (
    `id` bigint DEFAULT NULL,
    KEY `id` (`id`),
    CONSTRAINT `parent_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `school`;

CREATE TABLE
  `school` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `phone` char(10) DEFAULT NULL,
    `email` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `phone` (`phone`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `sections`;

CREATE TABLE
  `sections` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `school_id` bigint DEFAULT NULL,
    `class` tinyint DEFAULT NULL,
    `running_year` year DEFAULT NULL,
    `class_teacher_id` bigint DEFAULT NULL,
    `section` char(1) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `school_id` (`school_id`, `class`, `section`, `running_year`),
    KEY `class_teacher_id` (`class_teacher_id`),
    CONSTRAINT `sections_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE,
    CONSTRAINT `sections_ibfk_2` FOREIGN KEY (`class_teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL,
    CONSTRAINT `sections_chk_1` CHECK ((`class` between 1 and 12))
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `slots`;

CREATE TABLE
  `slots` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `course_id` bigint DEFAULT NULL,
    `start_time` time DEFAULT NULL,
    `end_time` time DEFAULT NULL,
    `day` enum ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN') DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `course_id` (`course_id`),
    CONSTRAINT `slots_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `student`;

CREATE TABLE
  `student` (
    `id` bigint DEFAULT NULL,
    `parent_id` bigint DEFAULT NULL,
    KEY `id` (`id`),
    KEY `parent_id` (`parent_id`),
    CONSTRAINT `student_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    CONSTRAINT `student_ibfk_2` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`id`) ON DELETE SET NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `subject`;

CREATE TABLE
  `subject` (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) DEFAULT NULL,
    `code` varchar(10) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `subject_req`;

CREATE TABLE
  `subject_req` (
    `id` int NOT NULL AUTO_INCREMENT,
    `class` tinyint DEFAULT NULL,
    `num_lectures` int DEFAULT NULL,
    `num_lab` int DEFAULT NULL,
    `subject_id` int DEFAULT NULL,
    `position` enum ('PRT', 'TGT', 'PGT') DEFAULT NULL,
    `attendance_criteria` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `subject_id` (`subject_id`),
    CONSTRAINT `subject_req_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE,
    CONSTRAINT `subject_req_chk_1` CHECK ((`class` between 1 and 12)),
    CONSTRAINT `subject_req_chk_2` CHECK ((`attendance_criteria` between 0 and 100))
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE
  `teacher` (
    `id` bigint DEFAULT NULL,
    `subject_id` int DEFAULT NULL,
    `position` enum ('PRT', 'TGT', 'PGT') DEFAULT NULL,
    KEY `id` (`id`),
    KEY `subject_id` (`subject_id`),
    CONSTRAINT `teacher_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    CONSTRAINT `teacher_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE SET NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `teacher_req`;

CREATE TABLE
  `teacher_req` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `school_id` bigint DEFAULT NULL,
    `position` enum ('PRT', 'TGT', 'PGT') DEFAULT NULL,
    `subject_id` int DEFAULT NULL,
    `teacher_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `school_id` (`school_id`),
    KEY `subject_id` (`subject_id`),
    KEY `teacher_id` (`teacher_id`),
    CONSTRAINT `teacher_req_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE,
    CONSTRAINT `teacher_req_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE,
    CONSTRAINT `teacher_req_ibfk_3` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `users`;

CREATE TABLE
  `users` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(100) DEFAULT NULL,
    `email` varchar(100) DEFAULT NULL,
    `phone` char(10) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `role` enum (
      'USER',
      'ADMIN',
      'PARENT',
      'STUDENT',
      'TEACHER',
      'TT_INCHARGE',
      'SCHOOL_INCHARGE',
      'GENERAL_MANAGER'
    ) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `phone` (`phone`)
  ) ENGINE = InnoDB AUTO_INCREMENT = 8 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
