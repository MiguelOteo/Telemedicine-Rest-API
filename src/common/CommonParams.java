package common;

public @interface CommonParams {

	// REST API base URL
	public static String BASE_API_URL = "http://localhost:8080/TelemedicineRestAPI/";
	
	// MySQL connection parameter
	public static String BASE_DB_URL = "jdbc:mysql://localhost:3306/telemedicineDB";
	public static String DB_PASSWORD = "8mo@oLbwLkGxCE8w";
	public static String DB_HOST = "root";
	
	// Insert data in the data base
	public static String INSERT_NEW_USER = "INSERT INTO Users (userName, userEmail, userPassword) VALUES (?, ?, ?);";
	public static String INSERT_NEW_DOCTOR = "INSERT INTO Doctors (userId) VALUES (?);";
	public static String INSERT_NEW_PATIENT = "INSERT INTO Patients (userId) VALUES (?);";
	
	// Search data from the data base
	public static String SEARCH_USER_BY_EMAIL = "SELECT * FROM Users WHERE userEmail = ?;";
	public static String SEARCH_PATIENT_BY_USERID = "SELECT * FROM Patients WHERE userId = ?;";
	public static String SEARCH_DOCTOR_BY_USERID = "SELECT * FROM Doctors WHERE userId = ?;";
	
	// Count data from the data base
	public static String COUNT_USERS_BY_EMAIL = "SELECT COUNT(userEmail) FROM Users WHERE userEmail = ?;";
}
