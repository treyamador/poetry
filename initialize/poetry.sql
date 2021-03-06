
/* create new user */
DROP USER IF EXISTS 'sqluser'@'localhost';
CREATE USER 'sqluser'@'localhost' IDENTIFIED BY 'sqluserpw';
GRANT ALL PRIVILEGES ON * . * TO 'sqluser'@'localhost';


/* remove this later */
DROP DATABASE IF EXISTS poetry;
CREATE DATABASE poetry;
USE poetry;


/* create the poetry tables */

CREATE TABLE poem 
	(
		id		INTEGER			NOT NULL AUTO_INCREMENT,
		title		VARCHAR(100),
		poet_id		INTEGER,
		date		DATE,
		url		VARCHAR(100)		NOT NULL,
		PRIMARY KEY (id)
	);

CREATE TABLE poemsection 
	(
		poem_id		INTEGER			NOT NULL,
		section_num	INTEGER			NOT NULL,
		text		VARCHAR(250),
		PRIMARY KEY (poem_id, section_num),
		FOREIGN KEY (poem_id) REFERENCES poem(id)
	);

CREATE TABLE poet 
	(
		id		INTEGER			NOT NULL,
		name		VARCHAR(100)		NOT NULL,
		birthdate	DATE,
		PRIMARY KEY (id)
	);



/* test data */


INSERT INTO poem(title, poet_id, date, url) VALUES
	('Metro', 1, '1920-09-12', 'www.poemone.com'),
	('Infancy', 2, '1945-01-07', 'www.poetizer.org/superpoem');

INSERT INTO poemsection(poem_id, section_num, text) VALUES 
	(1, 1, 'how much can it be?'),
	(1, 2, 'it an be such truth...'),
	(2, 1, 'a \n jungle');


