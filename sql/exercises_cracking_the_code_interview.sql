
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
ORDER BY count(sc.courseid) desc
limit (select (count(*) * 0.2) as total from courses);              -- return only top 30% of courses

-- courses with at least 4 students enrolled
select c.coursename, count(sc.courseid)
from courses as c left join studentcourses as sc on c.courseid = sc.courseid
where (select count(DISTINCT studentid) from studentcourses sc2 where sc2.courseid = c.courseid) > 4
group by (c.courseid);

-- update name of student attending all courses
update students set studentname='most enrolled!'
WHERE studentid = (
    select ss.studentid
    from students as ss left join studentcourses sscc on ss.studentid = sscc.studentid
    group BY (ss.studentid)
    order by count(sscc.courseid) DESC
    limit 1
);

-- how many courses there are?
select count(*) from courses;

-- student enrolled in more courses
select ss.studentid, ss.studentname
    from students as ss left join studentcourses sscc on ss.studentid = sscc.studentid
    group BY (ss.studentid)
    order by count(sscc.courseid) DESC limit 1;



-- From "Cracking the code interview" list of coding questions (page 172)
-- 14.1
select TenantID
from Tenants as t, AptTenants as ap 
where t.TenandId = ap.TenantID and 
    (select count(TenantID from AptTenants as ap2 where ap2.TenantID = ap.TenantID ) > 1

-- 14.2
select b.BuildingID, count(RequestID)
from Buildings as b left join Apartments as a on b.BuildingID = a.BuildingID 
    left join Request as r on a.AptID = r.AptId
where r.Status = 'Open'
group BY (b.BuildingID)

