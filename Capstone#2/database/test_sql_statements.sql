select park_id, name, location, establish_date, area, visitors, description 
from park 
where name ilike 'A%';


select campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee
from campground;

select * from campground where campground_id = 1;


select site_id, c.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities, c.daily_fee
from site s 
join campground c on s.campground_id = c.campground_id
where c.campground_id = 1;

select * from reservation where site_id = 1;

select * from site where site_id = 1;

SELECT reservation_id, site_ID, name, start_date, num_days, 
(start_date + num_days) as end_date, create_date 
FROM reservation r JOIN site s USING (site_id) 
WHERE SITE_ID = 1;


SELECT s.site_ID, s.site_number, name, start_date, num_days, 
(start_date + num_days) as end_date, create_date 
FROM reservation r JOIN site s on r.site_id = s.site_id 
WHERE NOT (start_date,(start_date + num_days)) OVERLAPS ('2/10/2020','2/15/2020')
AND r.site_id is not null;

SELECT s.site_id, s.site_number 
FROM site s
LEFT JOIN reservation r on s.site_id = r.site_id
WHERE NOT (start_date,(start_date + num_days)) OVERLAPS ('2/21/2020','2/22/2020')
AND r.reservation_id IS NULL;

SELECT s.site_ID, s.site_number
FROM site s 
LEFT JOIN reservation r on s.site_id = r.site_id 
WHERE NOT (start_date,(start_date + num_days)) OVERLAPS ('2/21/2020','2/22/2020')
AND s.site_id IS NULL
;


SELECT site_ID 
FROM reservation
WHERE (start_date,(start_date + num_days)) OVERLAPS ('2/21/2020','2/25/2020')
;
--WINNER WINNER CHICKEN DINNER
SELECT site_id, site_number 
FROM site 
WHERE campgrouNOT (site_id IN (SELECT site_ID FROM reservation WHERE (start_date,(start_date + num_days)) OVERLAPS ('2/11/2020','2/15/2020')))
;


SELECT site_id, c.campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities, c.daily_fee FROM site s
JOIN campground c ON s.campground_id = c.campground_id 
WHERE NOT (site_id IN (SELECT site_ID FROM reservation WHERE (start_date,(start_date + num_days)) OVERLAPS ('2/20/2020', '2/25/2020')))
AND c.campground_id = 1
LIMIT 5
;

select * from reservation where reservation_id = 46;
select * from site where site_id = 470;
select * from campground where name like '%eawall';

select * from site;

select * from reservation;
