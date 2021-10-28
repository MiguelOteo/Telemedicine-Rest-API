package common;

public @interface CommonParams {

	// Connection parameters
	public static String BASE_URL = "jdbc:mysql://localhost:3306/telemedicineDB";
	public static String DB_PASSWORD = "8mo@oLbwLkGxCE8w";
	public static String DB_HOST = "root";
	
	// Query sequences
	public static String INSERT_NEW_USER = "INSERT INTO Users (userName, userEmail, userPassword) VALUES (?, ?, ?);";
	public static String SEARCH_USER_BY_EMAIL = "SELECT userEmail FROM Users WHERE userEmail = ?";
	
}
