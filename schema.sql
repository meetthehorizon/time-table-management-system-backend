DROP DATABASE IF EXISTS attendance_system;

CREATE DATABASE attendance_system;

USE attendance_system;

CREATE TABLE
    users (
        id BIGINT AUTO_INCREMENT,
        name VARCHAR(100),
        email VARCHAR(100),
        phone CHAR(10),
        address VARCHAR(255),
        password VARCHAR(255),
        PRIMARY KEY (id),
        UNIQUE (email),
        UNIQUE (phone)
    );

CREATE TABLE
    user_roles (
        user_role_id BIGINT AUTO_INCREMENT,
        user_id BIGINT,
        role_name ENUM (
            'ROLE_USER',
            'ROLE_ADMIN',
            'ROLE_PARENT',
            'ROLE_STUDENT',
            'ROLE_TEACHER',
            'ROLE_EMPLOYEE',
            'ROLE_TT_INCHARGE',
            'ROLE_SCHOOL_INCHARGE',
            'ROLE_GENERAL_MANAGER'
        ) NOT NULL,
        PRIMARY KEY (user_role_id),
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        UNIQUE (user_id, role_name)
    );

CREATE TABLE
    parent (
        id BIGINT,
        PRIMARY KEY (id),
        FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE
    student (
        id BIGINT,
        parent_id BIGINT,
        PRIMARY KEY (id),
        FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (parent_id) REFERENCES parent (id) ON DELETE SET NULL ON UPDATE CASCADE
    );

CREATE TABLE
    school (
        id BIGINT AUTO_INCREMENT,
        name VARCHAR(255),
        address VARCHAR(255),
        phone CHAR(10),
        email VARCHAR(100),
        PRIMARY KEY (id),
        UNIQUE (email),
        UNIQUE (phone)
    );

CREATE TABLE
    employee (
        id BIGINT,
        school_id BIGINT,
        PRIMARY KEY (id),
        FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (school_id) REFERENCES school (id) ON DELETE SET NULL ON UPDATE CASCADE
    );

CREATE TABLE
    subject (
        id INT AUTO_INCREMENT,
        name VARCHAR(100),
        code VARCHAR(10),
        PRIMARY KEY (id),
        UNIQUE (code)
    );

CREATE TABLE
    teacher (
        id BIGINT,
        subject_id INT,
        position ENUM ('PRT', 'TGT', 'PGT'),
        PRIMARY KEY (id),
        FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE SET NULL ON UPDATE CASCADE
    );

CREATE TABLE
    teacher_req (
        id BIGINT AUTO_INCREMENT,
        school_id BIGINT,
        position ENUM ('PRT', 'TGT', 'PGT'),
        subject_id INT,
        teacher_id BIGINT,
        PRIMARY KEY (id),
        FOREIGN KEY (school_id) REFERENCES school (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE SET NULL ON UPDATE CASCADE
    );

CREATE TABLE
    subject_req (
        id INT AUTO_INCREMENT,
        class TINYINT,
        num_lectures INT,
        num_lab INT,
        subject_id INT,
        position ENUM ('PRT', 'TGT', 'PGT'),
        attendance_criteria INT,
        PRIMARY KEY (id),
        CHECK (class BETWEEN 1 AND 12),
        CHECK (attendance_criteria BETWEEN 0 AND 100),
        FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE
    sections (
        id BIGINT AUTO_INCREMENT,
        school_id BIGINT,
        class TINYINT,
        running_year YEAR,
        class_teacher_id BIGINT,
        section CHAR(1),
        PRIMARY KEY (id),
        FOREIGN KEY (school_id) REFERENCES school (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CHECK (class BETWEEN 1 AND 12),
        FOREIGN KEY (class_teacher_id) REFERENCES teacher (id) ON DELETE SET NULL ON UPDATE CASCADE,
        UNIQUE (school_id, class, section, running_year)
    );

CREATE TABLE
    enrollment (
        id BIGINT AUTO_INCREMENT,
        student_id BIGINT,
        section_id BIGINT,
        enroll_year YEAR,
        PRIMARY KEY (id),
        FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (section_id) REFERENCES sections (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE
    leaves (
        id BIGINT AUTO_INCREMENT,
        enroll_id BIGINT,
        comment VARCHAR(500),
        drive_link VARCHAR(100),
        curr_date DATE,
        PRIMARY KEY (id),
        FOREIGN KEY (enroll_id) REFERENCES enrollment (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE
    courses (
        id BIGINT AUTO_INCREMENT,
        section_id BIGINT,
        subject_req_id INT,
        teacher_req_id BIGINT,
        PRIMARY KEY (id),
        FOREIGN KEY (section_id) REFERENCES sections (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (subject_req_id) REFERENCES subject_req (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (teacher_req_id) REFERENCES teacher_req (id) ON DELETE SET NULL ON UPDATE CASCADE
    );

CREATE TABLE
    slots (
        id BIGINT AUTO_INCREMENT,
        course_id BIGINT,
        start_time TIME,
        end_time TIME,
        day ENUM ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'),
        PRIMARY KEY (id),
        FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE
    attendance (
        id BIGINT AUTO_INCREMENT,
        slot_id BIGINT,
        student_id BIGINT,
        is_present BOOL,
        curr_date DATE,
        remark VARCHAR(100),
        PRIMARY KEY (id),
        FOREIGN KEY (slot_id) REFERENCES slots (id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE ON UPDATE CASCADE
    );

SHOW TABLES;
