BEGIN TRANSACTION;

DROP TABLE IF EXISTS tournamentMatch;
DROP TABLE IF EXISTS tournamentRequest;
DROP TABLE IF EXISTS tournamentRoster;
DROP TABLE IF EXISTS teamRoster;
DROP TABLE IF EXISTS teamRequest;
DROP TABLE IF EXISTS tournaments;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS teams;

CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  email varchar(255) NOT NULL UNIQUE,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  role varchar(255) NOT NULL default('user')
);

CREATE TABLE teams (
  id serial PRIMARY KEY,
  team_name varchar(255) NOT NULL,
  game varchar(255) NOT NULL,
  accepting_members boolean NOT NULL,
  team_bio varchar(300)
);

CREATE TABLE teamRoster (
  user_id integer REFERENCES users (id),
  team_id integer REFERENCES teams (id),
  captain boolean NOT NULL DEFAULT FALSE,
  PRIMARY KEY (user_id, team_id)
);

CREATE TABLE teamRequest (
  user_id integer REFERENCES users (id),
  team_id integer REFERENCES teams (id),
  message varchar(300),
  constraint pk_teamRequest primary key (user_id, team_id)
);

CREATE TABLE tournaments (
  id serial PRIMARY KEY,
  tourney_name varchar(255) NOT NULL,
  game varchar(255) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  location varchar(255) NOT NULL,
  entry_fee integer NOT NULL, 
  prize_desc varchar(300) NOT NULL,
  tournament_owner integer REFERENCES users(id),
  accepting_entries boolean NOT NULL,
  completed boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE tournamentRequest (
  tourney_id integer REFERENCES tournaments (id), 
  team_id integer REFERENCES teams (id),
  message varchar(300),
  constraint pk_tournamentRequest primary key (tourney_id, team_id)
);

CREATE TABLE tournamentRoster (
  tourney_id integer REFERENCES tournaments (id), 
  team_id integer REFERENCES teams (id),
  constraint pk_tournamentRoster primary key (tourney_id, team_id)
);

CREATE TABLE tournamentMatch (
  match_id serial PRIMARY KEY,
  tourney_id integer REFERENCES tournaments (id),
  round_number integer NOT NULL,
  team_1_id integer REFERENCES teams (id) NOT NULL,
  team_2_id integer REFERENCES teams (id),
  team_1_score integer,
  team_2_score integer,
  winner_id integer REFERENCES teams (id)

);

INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('DirtyDan', '8FEzT3Yop2aA7MJNWw7dyg==', 'zFiGLfGKoTZPIVaF6bhqfRfFeSr3EsmgXfAlhZEw28rwi42Hou5NIjNVjJgIrtd6n50VseitoiS1hCyKqMJBIK0K+Tua5es8uh2pMerQoceujeMewXkGyAZbTdYSR172h2BhwiclB9UbhonIHsGlbW1kwGqsTddxA3jiy4vaCfI=', 'junk@junkmail.com', 'Ben', 'Peters');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('Brobes11', 'Zdtc91cLb+thWmu5fNNVBQ==', 't4BG5V9Mv9DDeL7G4cuQBf2D/xfIPA1GrGlEFxcMgpMjDbEHCfAylpG8owOfEXoo3kw5hFNXp/Of4G2RjsYp5RSAthqxkII5cC81WGQFARLAev204VGZQ7e67KX7HE8EjAhtmOYHQGmEK61551+rod/YuVacfd1OkVkiltByoKk=', 'brobes11@gmail.com', 'Taylor', 'Broberg');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('al3x', 'Zdtc91cLb+thWmu5fNNVBQ==', 't4BG5V9Mv9DDeL7G4cuQBf2D/xfIPA1GrGlEFxcMgpMjDbEHCfAylpG8owOfEXoo3kw5hFNXp/Of4G2RjsYp5RSAthqxkII5cC81WGQFARLAev204VGZQ7e67KX7HE8EjAhtmOYHQGmEK61551+rod/YuVacfd1OkVkiltByoKk=', 'alexelsener@gmail.com', 'Alex', 'Elsener');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('swisher', 'Zdtc91cLb+thWmu5fNNVBQ==', 't4BG5V9Mv9DDeL7G4cuQBf2D/xfIPA1GrGlEFxcMgpMjDbEHCfAylpG8owOfEXoo3kw5hFNXp/Of4G2RjsYp5RSAthqxkII5cC81WGQFARLAev204VGZQ7e67KX7HE8EjAhtmOYHQGmEK61551+rod/YuVacfd1OkVkiltByoKk=', 'coryswisher@gmail.com', 'Cory', 'Swisher');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('zaniad', 'Zdtc91cLb+thWmu5fNNVBQ==', 't4BG5V9Mv9DDeL7G4cuQBf2D/xfIPA1GrGlEFxcMgpMjDbEHCfAylpG8owOfEXoo3kw5hFNXp/Of4G2RjsYp5RSAthqxkII5cC81WGQFARLAev204VGZQ7e67KX7HE8EjAhtmOYHQGmEK61551+rod/YuVacfd1OkVkiltByoKk=', 'austinzani@gmail.com', 'Austin', 'Zani');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('smithsssss', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'susansmith@gmail.com', 'Susan', 'Smith');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('smithjs', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'johnsmith@gmail.com', 'John', 'Smith');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('puppyLover', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'trentadams@hotmail.com', 'Trent', 'Adams');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('bornwinner', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'justice4all@live.com', 'Justice', 'Winslow');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('chosenone', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'pistonlegend@aol.com', 'Grant', 'Hill');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('cincinnati513', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'baker@browns.org', 'Baker', 'Mayfield');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('goodluck', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'dexter@mlb.com', 'Dexter', 'Fowler');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('bigslim', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'zeller@hornets.com', 'Cody', 'Zeller');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('gosports222', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'mike123@gmail.com', 'Mike', 'Number');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('passwordistaco', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'itsalwaysthehusband@book.com', 'Michelle', 'Campbell');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('smashBROSchamp', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'livingsingle@hotmail.com', 'Queen', 'Latifah');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('twills', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'hotnready@lc.com', 'Little', 'Ceasar');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('ucbearcat16', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'zion@pels.com', 'Zion', 'Williamson');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('ayyitsal', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'jj@hotmail.com', 'John', 'Johnston');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('gobearcats62', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'legendsson@aol.com', 'Tim', 'Hardaway Jr.');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('flatearther', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'bill@ringer.com', 'Bill', 'Simmons');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('techelevatorgrad', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'outliers@gmail.com', 'Malcom', 'Gladwell');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('orangehead', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'beastmode@aol.com', 'Marshawn', 'Lynch');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('falloutboylover', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'beyonceshusband@tidal.com', 'Jay', 'Z');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('rosesarered', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'goodmusic@gmail.com', 'Kanye', 'West');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('worldwidewob', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'goreds@mlb.com', 'Pete', 'Rose');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('hoodiemelo', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'goreds2@mlb.com', 'Joey', 'Votto');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('dcthecoach', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'green@bengals.com', 'AJ', 'Green');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('thecorso', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'cravings@cookbook.com', 'Chrissy', 'Teagan');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('theXfactor', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'vampireweekend@gmail.com', 'Ezra', 'Koenig');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('camelCase', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'coderchick@hotmail.com', 'Erika', 'Alexander');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('PascalCase', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'java4lyfe@techelevator.org', 'Nina', 'Simone');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('kabob-case', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'dotnetrulez@techelevator.org', 'Matthew', 'Bennett');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('missingcomma', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'wyatt@campbell.edu', 'Wyatt', 'Varner');
INSERT INTO users(username, password, salt, email, first_name, last_name) VALUES ('stackOverflow', 'NFGOLZPdeL6HyDJjqJUeug==', 'ocM454mpQxRI9ZucEehbDdM5buMKgZJM7rF7UCqBX6U/2V03GYvIUCPxmvxcmXkfD1nCtY5oI/adhbg3ZgaprhH5hT6TmF1kych36uhr84TS1Sjm9MPUDmT0QVXEuBLkVICj/JP861SLUQKCciP6FhlKrd1OxDYVXizihsboPM4=', 'wannacracker2@gmail.com', 'Polly', 'Wells');



INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('The Sharks', 'Volleyball', true, 'Spiking opponents all day long!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('The Dolphins', 'Volleyball', true, 'Just Bump it!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Magic', 'Basketball', true, 'Orlando is our home.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Dust Devils', 'Volleyball', false, 'Sand or Indoor we will put you in the floor!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('The Eternals', 'Volleyball', true, 'Winning is all we know.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Win or Bust', 'Volleyball', true, 'If you don''t care about winning don''t join.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Zebra Gang', 'Volleyball', true, 'Looking to play volleyball and have fun!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Jr. Bearcats', 'Volleyball', false, 'Not good enough for collegiate sports so here we are.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Amigos', 'Volleyball', true, 'Friends just wanting to bump volleyballs around');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Awesome Anteaters', 'Basketball', true, 'Looking for a solid 3 and D wing.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Cool Colonels', 'Basketball', true, 'Real hoopers only please!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('The Tide', 'Basketball', true, 'Just some middle aged men reliving their glory days.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Golden Grizzlies', 'Basketball', true, 'If you look good in gold you belong here.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('KingFish', 'Basketball', false, 'We play pass first basketball!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Squaaad', 'Basketball', true, 'SQUAAAAAAAAD');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Silver Knights', 'Basketball', true, 'We play for the love of the game.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Little Giants', 'Basketball', true, 'Remember that movie? What a great movie!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Mighty Ducks', 'Soccer', false, 'Gordon Bombay is not our coach.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('The Titans', 'Soccer', true, 'We don''t call it soccer. It''s futbol!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Dillon Panthers', 'Soccer', true, 'Just as good as the football team!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('King Lizards', 'Soccer', false, 'Just love playing the beautiful game.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Mudhens', 'Hockey', true, 'Grab your sticks and hit the ice with us!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Pink Dragons', 'Bike Polo', true, 'We REEEAALLLLY need players.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Queens of the Court', 'Bike Polo', true, 'Showing men who is boss since 2017');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Ragin'' Cajuns', 'Euchre', false, 'We call at least one loner a game. Play it on the edge!');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Sea Lions', 'Euchre', true, 'We will call people for table talk.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Shark Bit', 'Poker', true, 'Training for the World Series of Poker');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Savannah Stampede', 'DnD', false, 'We meet on Tuesdays at 7PM');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Sugar Bears', 'Super Smash Brothers', true, 'If Kirby is your main please stay away.');
INSERT INTO teams(team_name, game, accepting_members, team_bio) VALUES ('Thundering Herd', 'Super Smash Brothers', true, 'Donkey Kong and Diddy Kong only please.');





INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,1,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,12,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,22,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,23,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,27,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (1,9,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,4,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,14,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,20,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,11,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,33,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (2,35,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (3,2,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (3,15,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (3,19,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (3,31,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (3,21,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (3,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (4,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (4,2,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (4,10,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (4,13,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (4,16,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (4,28,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,6,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,5,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,8,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,17,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,18,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,24,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (5,25,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (6,26,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (6,29,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (6,30,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (6,32,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (6,34,false);

INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,1,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,12,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (9,22,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (10,23,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (11,27,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (12,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,9,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (9,4,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (10,14,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (11,20,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (12,11,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,33,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,35,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (9,2,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (10,15,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (11,19,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (12,31,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,21,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (9,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (10,2,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (11,10,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (12,13,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,16,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,28,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (9,6,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (10,5,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (11,8,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (12,17,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,18,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,24,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (9,25,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (10,26,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (11,29,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (12,30,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (7,32,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (8,34,false);

INSERT INTO teamRoster(team_id, user_id, captain) VALUES (13,1,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (14,12,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (15,22,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (16,23,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,27,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,9,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (16,4,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (15,14,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (14,20,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (13,11,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (13,33,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (14,35,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (15,2,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (16,15,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,19,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,31,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,21,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (16,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (15,29,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (14,10,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (13,13,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (13,16,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (14,28,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (15,6,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (16,5,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,8,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,17,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,18,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,24,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (16,25,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (15,26,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (14,29,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (13,30,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (18,32,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (17,34,false);

INSERT INTO teamRoster(team_id, user_id, captain) VALUES (19,1,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (20,12,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (21,22,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,23,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (23,27,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (19,9,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (20,3,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (21,4,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,14,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (23,20,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,11,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,33,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (23,35,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,2,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (21,15,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (20,19,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (19,31,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (19,21,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (20,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (21,7,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,29,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (23,10,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,13,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,16,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (23,28,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,6,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (21,5,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (20,8,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (19,17,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,18,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (21,24,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (20,25,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (19,26,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (23,29,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,30,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (24,32,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (22,34,false);

INSERT INTO teamRoster(team_id, user_id, captain) VALUES (25,3,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (26,12,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (27,22,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,23,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,27,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (30,1,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (25,9,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (26,1,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (27,4,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,14,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,20,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (30,11,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (25,33,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (26,35,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (27,2,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,15,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,19,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (30,31,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (30,21,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,7,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,7,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (27,29,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (26,10,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (25,13,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (25,16,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (26,28,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (27,6,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,5,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,8,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (30,17,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (25,18,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (26,24,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (27,25,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,26,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,29,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (30,30,true);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (29,32,false);
INSERT INTO teamRoster(team_id, user_id, captain) VALUES (28,34,false);



INSERT INTO teamRequest(team_id, user_id, message) VALUES (1,13, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (1,25, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (1,4, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (2,15, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (2,19, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (2,34, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (3,8, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (3,30, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (3,20, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (4,11, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (4,22, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (4,33, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (5,10, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (5,11, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (5,22, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (6,22, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (6,35, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (6,1, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (7,11, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (7,19, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (7,27, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (8,4, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (8,11, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (8,20, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (9,20, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (9,35, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (9,11, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (10,22, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (10,25, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (11,6, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (11,25, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (11,24, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (12,8, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (12,9, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (12,32, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (13,12, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (13,14, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (13,17, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (14,13, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (14,21, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (14,31, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (15,31, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (15,32, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (15,33, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (16,12, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (16,13, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (16,14, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (17,21, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (17,22, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (17,23, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (18,20, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (19,3, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (19,2, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (19,1, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (20,26, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (20,27, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (20,28, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (21,16, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (21,18, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (21,20, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (22,30, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (22,31, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (22,32, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (23,1, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (23,2, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (24,6, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (24,7, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (24,8, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (25,6, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (25,7, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (25,31, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (26,19, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (26,20, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (26,21, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (27,14, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (27,16, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (27,19, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (28,17, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (28,18, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (28,19, 'I am the missing link for your teams success.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (29,33, 'Our friend Tom told me about your team.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (29,34, 'Can I join please?');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (29,35, 'I am very interested in your team!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (30,4, 'I''ve been kicked off all the other teams.');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (30,6, 'Your team seems cool!');
INSERT INTO teamRequest(team_id, user_id, message) VALUES (30,8, 'I am the missing link for your teams success.');



 
INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Smash Brothers World Championship','Super Smash Brothers', '2020-10-06','2020-10-08','San Francisco, CA',25,'Commemorative Joy-Cons and Title of World Champ',8,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries, completed)
VALUES('Sun in the Sand','Volleyball', '2020-03-06','2020-03-08','Tampa Bay, FL',250,'$50000 Cash Prize and a New Ford Escape',3,true,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Rucker Park Invitational','Basketball', '2020-05-06','2020-05-08','Brooklyn, NY',30,'$20,000 and Going Down in History as a Street Ball Legend',2,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Tri-State Invitational','Soccer', '2020-11-06','2020-11-08','Louisville, KY',10,'New Adidas kits for the Winning Team',4,false);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Southeast Ohio Streetball Tournament','Basketball', '2020-09-12','2020-09-19','Cincinnati, OH',30,'A year supply of Gatorade and New Team Jordans',5,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('So-Cal Invitational','Bike Polo', '2020-10-21','2020-10-25','Los Angeles, CA',5,'New Bikes from the LA Bike Polo Commission',12,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('National Semi-Pro Championship','Volleyball', '2020-08-06','2020-08-08','Orlando, FL',250,'$10,000 Cash Prize and the 2020 Misty May Treanor Trophy',1,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Vegas Invitational','Basketball', '2020-04-12','2020-04-19','Las Vegas, NV',30,'$100,000 Cash Prize for the Winning Team',3,false);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Kick-It Charleston','Soccer', '2020-09-06','2020-09-08','Charleston, SC',43,'New Nike kits for your entire team.',8,false);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('All In Albany','Euchre', '2020-07-06','2020-07-10','Albany, NY',100,'$25,000 Cash Prize',9,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('Bluff In Bluffdale','Poker', '2020-10-06','2020-10-08','Bluffdale, UT',35,'$10,000 Grand Prize to the Winner',10,true);

INSERT INTO tournaments(tourney_name,game,start_date,end_date,location,entry_fee,prize_desc,tournament_owner,accepting_entries)
VALUES('CAHL Championship','Hockey', '2020-08-06','2020-08-08','Cincinnati, OH',250,'$1,000 Cash Prize and New CCM Sticks',11,true);




INSERT INTO tournamentRequest(tourney_id, team_id,message)VALUES(1,30,'We would really like to join your tournament.');
INSERT INTO tournamentRequest(tourney_id, team_id,message)VALUES(3,3,'We would really like to join your tournament.');
INSERT INTO tournamentRequest(tourney_id, team_id,message)VALUES(4,19,'We would really like to join your tournament.');
INSERT INTO tournamentRequest(tourney_id, team_id,message)VALUES(5,17,'We would really like to join your tournament.');
INSERT INTO tournamentRequest(tourney_id, team_id,message)VALUES(7,6,'We would really like to join your tournament.');






INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (1,29);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,1);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,2);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,4);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,5);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,6);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,7);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,8);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (2,9);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (3,11);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (3,12);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (3,13);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (3,14);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (3,15);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (3,17);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (4,18);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (4,20);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (4,21);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (5,3);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (5,10);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (5,12);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (5,13);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (5,15);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (5,16);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (6,23);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (6,24);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (7,1);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (7,2);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (7,4);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (7,7);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (7,9);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,10);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,11);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,12);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,13);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,14);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,15);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,16);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,17);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (8,3);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (9,18);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (9,19);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (9,20);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (9,21);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (10,25);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (10,26);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (11,27);
INSERT INTO tournamentRoster(tourney_id, team_id) VALUES (12,22);


INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 1, 1, 2, 3, 1, 1);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 1, 5, 4, 3, 2, 5);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 1, 7, 6, 3, 2, 7);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 1, 8, 9, 2, 3, 9);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 2, 8, 1, 2, 3, 8);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 2, 2, 9, 3, 1, 2);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 2, 6, 5, 2, 3, 5);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 2, 7, 4, 3, 2, 7);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 3, 1, 9, 3, 2, 1);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 3, 8, 2, 3, 1, 8);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 3, 7, 5, 1, 3, 5);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 3, 6, 4, 3, 2, 6);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 4, 6, 2, 1, 3, 2);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 4, 1, 4, 2, 3, 4);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 4, 5, 9, 3, 2, 5);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 4, 8, 7, 1, 3, 7);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 5, 1, 7, 2, 3, 7);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 5, 8, 6, 3, 2, 8);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 5, 2, 5, 1, 3, 5);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (2, 5, 9, 4, 3, 1, 9);


INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 13, 3, 54, 45, 13);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 14, 10, 63, 69, 10);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 15, 11, 57, 54, 15);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 16, 12, 60, 52, 16);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 17, null, null, null, 17);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 2, 14, 3, 54, 45, 14);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 2, 15, 10, 63, 69, 10);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 2, 16, 11, 57, 54, 16);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 2, 17, 12, 60, 62, 12);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 2, 13, null, null, null, 13);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 16, 3, 54, 65, 3);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 17, 10, 61, 69, 10);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 13, 11, 57, 54, 13);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 14, 12, 60, 52, 14);
INSERT INTO tournamentmatch (tourney_id, round_number, team_1_id, team_2_id, team_1_score, team_2_score, winner_id) VALUES (8, 1, 15, null, null, null, 15);



COMMIT TRANSACTION;

SELECT * FROM users
SELECT * FROM tournamentRequest
SELECT * FROM tournamentRoster
SELECT * FROM teamRoster
SELECT * FROM teamRequest
SELECT * FROM tournaments
SELECT * FROM teams