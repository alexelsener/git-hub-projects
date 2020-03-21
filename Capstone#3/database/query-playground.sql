SELECT parkCode, parkName, state, acreage, milesOfTrail, numberOfCampSites, climate, 
yearFounded, annualVisitorCount, inspirationalQuote, inspirationalQuoteSource, parkDescription, entryFee, numberOfAnimalSpecies
FROM park
ORDER BY parkName;


SELECT parkcode, fivedayforecastvalue, low, high, forecast 
FROM weather;    
WHERE parkcode = 'GNP'
ORDER BY fivedayforecastvalue;

select * from park;

INSERT INTO survey_result (parkcode, emailaddress, state, activitylevel) 
VALUES ('ENP', 'steveS@gmail.com', 'Alabama', 'Extemely Active');

select park.parkName, park.state, park.parkDescription, count(survey_result.parkcode) as fav_count from park
join survey_result on survey_result.parkcode = park.parkCode
group by park.parkCode
order by fav_count desc, park.parkName;

SELECT parkCode, parkName, state, acreage, milesOfTrail, numberOfCampSites, climate,  
yearFounded, annualVisitorCount, inspirationalQuote, inspirationalQuoteSource, parkDescription, 
entryFee, numberOfAnimalSpecies, 
latitude, longitude FROM park 
WHERE parkCode = 'YNP' ;
