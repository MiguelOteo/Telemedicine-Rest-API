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
	public final String SEARCH_USER_BY_USERID = "SELECT * FROM Users WHERE userId = ?;";
	public final String SEARCH_PATIENT_BY_USERID = "SELECT * FROM Patients WHERE userId = ?;";
	public final String SEARCH_PATIENT_BY_PATIENTID = "SELECT * FROM Patients WHERE patientId = ?;";
	public final String SEARCH_DOCTOR_BY_USERID = "SELECT * FROM Doctors WHERE userId = ?;";
	public final String SEARCH_DOCTOR_BY_DOCTORID = "SELECT * FROM Doctors WHERE doctorId = ?;";
	
	// List all form the database
	public final String LIST_ALL_PATIENTS = "SELECT * FROM Patients WHERE doctorId IS NULL;";
	public final String LIST_ALL_DOCTOR_PATIENTS = "SELECT * FROM Patients WHERE doctorId = ?;";
	
	// Update data base 
	public final String UPDATE_PATIENT_DOCTOR = "UPDATE Patients SET doctorId = ? WHERE patientId = ?;";
	public final String UPDATE_PATIENT_ASSIGNMENT_TO_NULL = "UPDATE Patients SET doctorId = NULL WHERE patientId = ?;";
	public final String UPDATE_PATIENT_ID_NUMBER = "UPDATE Patients SET patientIdNumber = ? WHERE patientId = ?;";
	public final String UPDATE_DOCTOR_DOCTOR_IDENTIFICATION = "UPDATE Doctors SET doctorIdentification = ? WHERE doctorId = ?;";
	public final String CHANGE_USER_PASSWORD = "UPDATE Users SET userEncryptedPassword = ? WHERE userId = ?;";
	public final String UPDATE_USER_ACCOUNT = "UPDATE Users SET userName = ? , userEmail = ? WHERE userId = ?";
}
