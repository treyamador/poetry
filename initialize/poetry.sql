
/* create new user */
DROP USER IF EXISTS 'sqluser'@'localhost';
CREATE USER 'sqluser'@'localhost' IDENTIFIED BY 'sqluserpw';
GRANT ALL PRIVILEGES ON * . * TO 'sqluser'@'localhost';


/* remove this later */
DROP DATABASE IF EXISTS Poetry;
CREATE DATABASE Poetry;
USE Poetry;


/* create the poetry tables */

CREATE TABLE POEM 
	(
		ID		INTEGER			NOT NULL AUTO_INCREMENT,
		Title		VARCHAR(100),
		PoetID		INTEGER,
		Date		DATE,
		URL		VARCHAR(100)		NOT NULL,
		PRIMARY KEY (ID)
	);

CREATE TABLE POEMSECTION 
	(
		PoemID		INTEGER			NOT NULL,
		SectionNum	INTEGER			NOT NULL,
		Text		VARCHAR(250),
		PRIMARY KEY (PoemID, SectionNum),
		FOREIGN KEY (PoemID) REFERENCES POEM(ID)
	);

CREATE TABLE POET 
	(
		ID		INTEGER			NOT NULL,
		Name		VARCHAR(100)		NOT NULL,
		Birthdate	DATE,
		PRIMARY KEY (ID)
	);



/* test data */


INSERT INTO POEM(Title, PoetID, Date, URL) VALUES
	('Metro', 1, '1920-09-12', 'www.poemone.com'),
	('Infancy', 2, '1945-01-07', 'www.poetizer.org/superpoem');

INSERT INTO POEMSECTION(PoemID, SectionNum, Text) VALUES 
	(1, 1, 'how much can it be?'),
	(1, 2, 'it an be such truth...'),
	(2, 1, 'a \n jungle');


