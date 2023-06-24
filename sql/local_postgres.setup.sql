

-- ################# START TABLES CREATION #################
create table Students (
    StudentID int not null,
    StudentName VARCHAR(200),
    PRIMARY key (StudentID)
);
create table Teachers (
    TeacherID int not null,
    TeacherName varchar(255),
    primary key (TeacherID)
);
create table Courses (
    CourseID int not null,
    CourseName varchar(255),
    TeacherID int,
    primary key (CourseID),
    Foreign Key (TeacherID) REFERENCES Teachers(TeacherID)
);
create table StudentCourses (
    CourseID int not null,
    StudentID int not null,
    PRIMARY KEY (CourseID, StudentID)
);

drop table students, studentcourses, teachers, courses;

-- ################# END TABLES CREATION #################


-- ################# START DATA POPULATION #################
insert into Students (StudentID, StudentName) VALUES 
(1, 'gonzalo'),
(2, 'andres'),
(3, 'mario'),
(4, 'benito'),
(5, 'sotelo'),
(6, 'sermon'),
(7, 'simon'),
(8, 'gaby'),
(9, 'lazy - not taking courses');


insert into Teachers (TeacherID, TeacherName) VALUES 
(1, 'teacher1'),
(2, 'teacher2'),
(3, 'teacher3'),
(4, 'teacher4 - no students'),
(5, 'teacher5 - no students');


insert into courses (courseid, coursename, teacherid) VALUES
(1, 'course1 - popular', 1),
(2, 'course2 - even student ids', 2),
(3, 'course3 - odd student ids', 3),
(4, 'course4 - greater than 4', 1),
(5, 'course5 - below 3 inclusive', 3),
(6, 'course6 - all except 8', 3);


insert into studentcourses (courseid, studentid) values 
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(2, 2),
(2, 4),
(2, 6),
(2, 8),
(3, 1),
(3, 3),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(5, 1),
(5, 2),
(5, 3),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 7);

--
-- ################# END DATA POPULATION #################

