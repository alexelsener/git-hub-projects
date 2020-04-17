select * from users where UPPER(username) = UPPER('aus7in');

INSERT INTO teams (team_name, game, team_bio, accepting_members) VALUES ('whatevers', 'basketball', 'We like to play games', true)

select * from teamroster WHERE team_id = 3

DELETE FROM teamroster WHERE captain = false

select * from users

SELECT team_name, game, team_bio FROM teams WHERE id IN (SELECT team_id FROM teamroster WHERE user_id = 1)

UPDATE users SET first_name = 'austin', last_name = 'zani', email = 'austinzani@me.com' WHERE username = 'zaniad'

SELECT * from teams

INSERT INTO teamroster (user_id,team_id,captain )VALUES (1,3,true)

SELECT * FROM tournaments

select * from tournaments

INSERT INTO tournaments (tourney_name, game, start_date, end_date, location, prize_desc, tournament_owner, entry_fee, accepting_entries) VALUES ('World Series', 'Baseball', '10/05/2020', '10/12/2020' , 'Cinncinnati, OH', 'World Series Trohpy and title of World Champions', 2, 200, false)


INSERT INTO tournamentroster (tourney_id, team_id) VALUES (2,2)

SELECT * from tournamentrequest

SELECT users.username, message
FROM teamrequest
JOIN users ON teamrequest.user_id = users.id
WHERE team_id = 2

SELECT * FROM tournaments 
WHERE id IN (SELECT tourney_id FROM tournamentroster WHERE team_id IN (SELECT id FROM teamroster WHERE user_id = 1))

SELECT id, username, role, email, first_name, last_name, teamroster.captain FROM users 
JOIN teamroster ON teamroster.user_id = users.id
WHERE id IN (SELECT user_id FROM teamRoster WHERE team_id = 3) AND teamroster.team_id = 3;

INSERT INTO tournamentrequest (team_id, tourney_id, message) VALUES (2,1,'test')

SELECT * FROM tournamentrequest

DELETE FROM tournamentrequest WHERE team_id = 1 AND tourney_id = 1

SELECT * FROM tournamentrequest

SELECT * FROM tournamentroster

INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (5,1,1,1,5);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (4,2,1,1,4);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (3,null,1,1,3);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (5,2,1,2,5);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (4,3,1,2,3);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (1,null,1,2,1);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (5,3,1,3,5);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (2,1,1,3,1);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (4,null,1,3,4);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (5,4,1,4,5);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (3,1,1,4,1);
INSERT INTO tournamentmatch (team_1_id, team_2_id, tourney_id, round_number ,winner_id) VALUES (2,null,1,4,2);




SELECT * FROM users

SELECT * FROM teamrequest
SELECT * FROM tournamentmatch

SELECT * FROM tournamentmatch WHERE tourney_id = 1 AND round_number = 1


SELECT * FROM teams


SELECT team_id FROM teamroster WHERE user_id = 5 AND captain = true



SELECT teams.team_name, COUNT(tournamentmatch.winner_id) AS wins FROM tournamentroster
JOIN tournamentmatch ON tournamentmatch.winner_id = tournamentroster.team_id
JOIN teams ON tournamentroster.team_id = teams.id
WHERE tournamentroster.tourney_id = 1
GROUP BY teams.team_name
ORDER BY wins desc LIMIT 3;



SELECT * FROM tournaments;
ORDER BY wins desc, team_name 
LIMIT 3;

SELECT * FROM tournaments

UPDATE tournaments SET completed = true WHERE id = 4;
