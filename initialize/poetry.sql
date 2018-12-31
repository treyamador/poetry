
/* create new user */
DROP USER IF EXISTS 'sqluser'@'localhost';
CREATE USER 'sqluser'@'localhost' IDENTIFIED BY 'sqluserpw';
GRANT ALL PRIVILEGES ON * . * TO 'sqluser'@'localhost';


/* remove this later */
DROP DATABASE IF EXISTS Poetry;
CREATE DATABASE Poetry;
USE Poetry;


/* create the poetry tables */
CREATE TABLE Poem 
	(
		PoemID		INTEGER			NOT NULL AUTO_INCREMENT,
		Title		VARCHAR(100),
		NumSections	INTEGER			NOT NULL,
		Date		DATE,
		PoetID		INTEGER			NOT NULL,
		PRIMARY KEY (PoemID),
		FOREIGN KEY (PoetID) REFERENCES Poet(PoetID)
	);

CREATE TABLE PoemSection 
	(
		PoemID		INTEGER			NOT NULL,
		SectionNum	INTEGER			NOT NULL,
		Text		VARCHAR(500),
		PRIMARY KEY (PoemID, SectionNum),
		FOREIGN KEY (PoemID) REFERENCES Poem(PoemID),
	);

CREATE TABLE Poet 
	(
		PoetID		INTEGER			NOT NULL,
		Name		VARCHAR(100)		NOT NULL,
		Birthdate	Date,
		PRIMARY KEY (PoetID),
	);






/*

DROP DATABASE IF EXISTS ArtBase;
CREATE DATABASE ArtBase;
USE ArtBase;

CREATE TABLE ARTIST
	(
		AName			VARCHAR(30)			NOT NULL,
		Birthplace		VARCHAR(15)			NOT NULL,
		Birthdate		DATE,
		Style			VARCHAR(30),
		PRIMARY KEY (AName)
	);

CREATE TABLE ARTWORK
	(
		Title			VARCHAR(30)			NOT NULL,
		Year			INTEGER,
		Type			VARCHAR(15),
		Price			DECIMAL(12,2),
		AName			VARCHAR(30)			NOT NULL,
		PRIMARY KEY (Title),
		FOREIGN KEY (AName) REFERENCES ARTIST(AName)
	);

CREATE TABLE CUSTOMER
	(
		CustId			VARCHAR(15)			NOT NULL, 
		CName			VARCHAR(30)			NOT NULL,
		Address			VARCHAR(30),
		Amount			INTEGER,
		PRIMARY KEY (CustId)
	);

CREATE TABLE GENRE
	(
		GName			VARCHAR(15)			NOT NULL,
		PRIMARY KEY (GName)
	);

CREATE TABLE CLASSIFY 
	(
		Title			VARCHAR(30)			NOT NULL,
		GName			VARCHAR(15)			NOT NULL,
		PRIMARY KEY (Title),
		FOREIGN KEY (GName) REFERENCES GENRE (GName)
	);

CREATE TABLE LIKE_GENRE
	(
		ID 			INTEGER 			NOT NULL AUTO_INCREMENT,
		CustID			VARCHAR(15)			NOT NULL,
		GName			VARCHAR(15)			NOT NULL,
		PRIMARY KEY (ID),
		FOREIGN KEY (GName) REFERENCES GENRE (GName)
	);
	
CREATE TABLE LIKE_ARTIST
	(
		ID 			INTEGER 			NOT NULL AUTO_INCREMENT,
		CustID			VARCHAR(30)			NOT NULL, 
		AName			VARCHAR(30)			NOT NULL,
		PRIMARY KEY (ID),
		FOREIGN KEY (AName) REFERENCES ARTIST(AName)
	);

INSERT INTO ARTIST (AName, Birthplace, Birthdate, Style) VALUES
	('Leonardo Da Vinci', 'Italy', '1620-09-12', 'Oil Painting'),
	('Leonid Mala', 'Detroit', '1934-01-27', 'Sculpture'),
	('Vivaldi', 'Minnesota', '1911-06-03', 'Oil Painting'),
	('Alden Franken', 'France', '1871-11-13', 'Landscape'),
	('Casper David Freidrich', 'Germany', '1781-01-07', 'Painting');

INSERT INTO ARTWORK (Title, Year, Type, Price, AName) VALUES
	('Wander Above the Sea of Fog', 1853, 'Romantic', 25000000.00, 'Casper David Freidrich'),
	('Restaurant at Noon', 1927, 'Modern', 100000.00, 'Vivaldi'),
	('Nude Descending Staircase', 1912, 'Modern', 3699999.99, 'Alden Franken'),
	('Watergate', 1968, 'Post-Modern', 12876.36, 'Leonid Mala'),
	('Christ on Accolade', 1681, 'Renaissance', 1000000000.00, 'Leonardo Da Vinci'),
	('X-Rays', 1988, 'Hyper-realist', 34000.00, 'Leonid Mala'),
	('Wind and Water', 1635, 'Renaissance', 100000000.00, 'Leonardo Da Vinci');
	
INSERT INTO CUSTOMER (CustId, CName, Address, Amount) VALUES
	('wt53gya', 'Brenda Cushing', '312 North Revenue', 25000),
	('gd11bnt', 'Francis M Frank', '482 S Ave', 300000),
	('iy95qzo', 'Sam Mendoza', '802 Via Short', 5000),
	('mx72ptu', 'Walter Yang', '555 Jefferson St', 1000);

INSERT INTO GENRE (GName) VALUES
	('Romantic'),
	('Modern'),
	('Post-Modern'),
	('Renaissance'),
	('Hyper-realist');

INSERT INTO CLASSIFY (Title, GName) VALUES
	('Wander Above the Sea of Fog', 'Romantic'),
	('Restaurant at Noon', 'Modern'),
	('Nude Descending Staircase', 'Modern'),
	('Watergate', 'Post-Modern'),
	('Christ on Accolade', 'Renaissance'),
	('X-Rays', 'Hyper-realist'),
	('Wind and Water', 'Renaissance');
	
INSERT INTO LIKE_GENRE(CustID, GName) VALUES
	('gd11bnt', 'Romantic'),
	('wt53gya', 'Modern'),
	('iy95qzo', 'Post-Modern'),
	('mx72ptu', 'Hyper-realist');

INSERT INTO LIKE_ARTIST (CustID, AName) VALUES
	('gd11bnt', 'Leonardo Da Vinci'),
	('wt53gya', 'Casper David Freidrich'),
	('iy95qzo', 'Vivaldi'),
	('mx72ptu', 'Alden Franken');

*/


