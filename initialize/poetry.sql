
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
		NumSections	INTEGER			NOT NULL,
		Date		DATE,
		PoetID		INTEGER,
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
		Birthdate	Date,
		PRIMARY KEY (ID)
	);



/* test data */


INSERT INTO POEM(Title, NumSections, Date, PoetID, URL) VALUES
	('Metro', 2, '1920-09-12', 1, 'www.poemone.com'),
	('Infancy', 1, '1945-01-07', 2, 'www.poetizer.org/superpoem');

INSERT INTO POEMSECTION(PoemID, SectionNum, Text) VALUES 
	(1, 0, 'how much can it be?'),
	(1, 1, 'it an be such truth...'),
	(2, 0, 'a \n jungle');


