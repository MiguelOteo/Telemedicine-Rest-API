CREATE TABLE users (
	
	userId INT PRIMARY KEY AUTO_INCREMENT,
	userName VARCHAR(30) NOT NULL, 
	userEmail VARCHAR(30) NOT NULL,
	userEncryptedPassword VARCHAR(256) NOT NULL,
	userSalt VARCHAR(15) NOT NULL
);

CREATE TABLE doctors (

	doctorId INT PRIMARY KEY AUTO_INCREMENT,
	doctorIdentification VARCHAR(15) DEFAULT NULL,
	userId INT NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE
);

CREATE TABLE patients (

	patientId INT PRIMARY KEY AUTO_INCREMENT,
	patientIdNumber VARCHAR(15) DEFAULT NULL,
	patientWeight REAL DEFAULT NULL,
	patientHeight REAL DEFAULT NULL,
	userId INT NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE,
	doctorId INT DEFAULT NULL,
	FOREIGN KEY (doctorId) REFERENCES doctors(doctorId) 
);

CREATE TABLE bitalinoRecords (

	recordsId INT PRIMARY KEY AUTO_INCREMENT,
	recordsStartDate DATETIME NOT NULL,
	recordsFrequency INT NOT NULL,
	recordsEMGValues VARCHAR(1000) NOT NULL,
	recordsECGValues VARCHAR(1000) NOT NULL,
	patientId INT NOT NULL,
	FOREIGN KEY (patientId) REFERENCES patients(patientId)
);