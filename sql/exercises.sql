# https://leetcode.com/problems/delete-duplicate-emails/description/
delete p from Person p, Person q where p.email = q.email and p.id > q.id

# https://leetcode.com/problems/rising-temperature/
select w1.id from Weather w1, Weather w2 where w1.temperature > w2.temperature and datediff(w1.recordDate, w2.recordDate) = 1

# https://leetcode.com/problems/combine-two-tables/
select p.firstName, p.lastName, a.city, a.state
from Person p left join Address a
on p.personId = a.personId

# https://leetcode.com/problems/employees-earning-more-than-their-managers/
select e.name as Employee
from Employee e left join Employee m 
on e.managerId = m.id
where e.salary > m.salary

# https://leetcode.com/problems/managers-with-at-least-5-direct-reports/
select m.name
from Employee m
where (select count(e.id) from Employee e where e.managerId = m.id) >= 5

# https://leetcode.com/problems/tree-node/
select t.id,
case
  when t.p_id is null then "Root"
  when (select count(tt.id) from Tree tt where tt.p_id = t.id) = 0 then "Leaf"
  else "Inner"
end as type 
from Tree t

# TIME LIMIT EXCEEDED! Need to optimize it -> https://leetcode.com/problems/customers-who-bought-all-products/
select distinct (c.customer_id) from Customer c
where (select count(distinct cc.product_key) from Customer cc where cc.customer_id = c.customer_id group by cc.customer_id) =
      (select count(p.product_key) from Product p)

