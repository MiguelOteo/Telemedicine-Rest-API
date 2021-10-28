package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.CommonParams;
import models.User;

public class ControllerMySQL {

	public User registerUser(String userName, String userEmail, String userPassword) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.INSERT_NEW_USER);
			
			statement.setString(1, userName);
			statement.setString(2, userEmail);
			statement.setString(3, userPassword);
			
			statement.executeUpdate();		
			connection.close();
			return new User();
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}

	public boolean userExists(String userEmail) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_USER_BY_EMAIL);
			
			statement.setString(1, userEmail);
			ResultSet resultSet = statement.executeQuery();		
			connection.close();
			
			if (!resultSet.isBeforeFirst()) {	
				return false;
			} else {
				return true;
			}
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return false;
		}
	}
	
	
}
