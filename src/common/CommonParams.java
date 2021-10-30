package common;

public @interface CommonParams {

	// REST API base URL
	public final String BASE_API_URL = "http://192.168.1.44:8080/TelemedicineRestAPI/";
	
	// MySQL connection parameter
	public final String BASE_DB_URL = "jdbc:mysql://localhost:3306/telemedicineDB";
	public final String DB_PASSWORD = "8mo@oLbwLkGxCE8w";
	public final String DB_HOST = "root";
	
	// Insert data in the data base
	public final String INSERT_NEW_USER = "INSERT INTO Users (userName, userEmail, userEncryptedPassword, userSalt) VALUES (?, ?, ?, ?);";
	public final String INSERT_NEW_DOCTOR = "INSERT INTO Doctors (userId) VALUES (?);";
	public final String INSERT_NEW_PATIENT = "INSERT INTO Patients (userId) VALUES (?);";
	
	// Search data from the data base
	public final String SEARCH_USER_BY_EMAIL = "SELECT * FROM Users WHERE userEmail = ?;";
	public final String SEARCH_PATIENT_BY_USERID = "SELECT * FROM Patients WHERE userId = ?;";
	public final String SEARCH_DOCTOR_BY_USERID = "SELECT * FROM Doctors WHERE userId = ?;";

}
