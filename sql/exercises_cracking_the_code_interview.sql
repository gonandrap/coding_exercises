
-- teacher class size : all teachers and how many studednts they each teach.

select t.teacherId, count(sc.studentid)
from teachers as t left join courses as c on t.teacherid=c.teacherid 
    left JOIN studentcourses as sc on c.courseid=sc.courseid
group by t.teacherid
order by count(sc.studentid) desc;


-- number courses each student is taking
select s studentid, count(sc.courseid)
from students as s left join studentcourses as sc on s.studentid = sc.studentid
group by (s.studentid)
order by count(sc.courseid) ASC;

-- most popular course
select c.coursename, count(sc.courseid)
from courses as c left join studentcourses as sc on c.courseid = sc.courseid
group by (c.courseid)
ORDER BY count(sc.courseid) desc limit 1;