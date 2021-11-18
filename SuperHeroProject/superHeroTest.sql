DROP DATABASE IF EXISTS superHeroTest;
CREATE DATABASE superHeroTest;

USE superHeroTest;

CREATE TABLE superHuman(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    description VARCHAR(100) NOT NULL,
    hero BOOLEAN
);

CREATE TABLE power(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(50) NOT NULL
);

CREATE TABLE location(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
    description VARCHAR(250) NOT NULL,
	streetNumber VARCHAR(5),
    streetName VARCHAR(30),
    city VARCHAR(45),
    state CHAR(2),
    zip CHAR(5),
	latitude DECIMAL (8, 6),
    longitude DECIMAL (9, 6)
);

CREATE TABLE organization(
	id INT PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(25) NOT NULL,
    description VARCHAR(250) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    email VARCHAR(40) NOT NULL,
	hero BOOLEAN,
    locationId INT,
    FOREIGN KEY (locationId) REFERENCES location(id)
);

CREATE TABLE super_human_power(
	superHumanId INT NOT NULL,
    powerId INT NOT NULL,
    FOREIGN KEY (superHumanId) REFERENCES superHuman(id),
    FOREIGN KEY (powerId) REFERENCES power(id)
);

CREATE TABLE super_human_organization_affiliation(
	superHumanId INT NOT NULL,
    organizationId INT NOT NULL,
    FOREIGN KEY (superHumanId) REFERENCES superHuman(id),
    FOREIGN KEY (organizationId) REFERENCES organization(id)
);

CREATE TABLE sighting(
	id INT PRIMARY KEY AUTO_INCREMENT, 
    name VARCHAR(25) NOT NULL,
	date DATE NOT NULL,
    description VARCHAR(100) NOT NULL,
    superHumanId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY (superHumanId) REFERENCES superHuman(id),
    FOREIGN KEY (locationId) REFERENCES location(id)
)