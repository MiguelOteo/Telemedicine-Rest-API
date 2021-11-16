CREATE TABLE users (
	
	userId INT PRIMARY KEY AUTO_INCREMENT,
	userName VARCHAR(15) NOT NULL, 
	userEmail VARCHAR(30) NOT NULL,
	userEncryptedPassword VARCHAR(256) NOT NULL,
	userSalt VARCHAR(15) NOT NULL
);

CREATE TABLE doctors (

	doctorId INT PRIMARY KEY AUTO_INCREMENT,
	doctorIdentification VARCHAR(15) DEFAULT NULL,
	userId INT NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(userId)
);

CREATE TABLE patients (

	patientId INT PRIMARY KEY AUTO_INCREMENT,
	patientIdNumber VARCHAR(15) DEFAULT NULL,
	userId INT NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(userId),
	doctorId INT DEFAULT NULL,
	FOREIGN KEY (doctorId) REFERENCES doctors(doctorId)
);