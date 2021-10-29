package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.CommonParams;
import models.Doctor;
import models.Patient;
import models.User;

public class ControllerMySQL {

	public User registerUser(String userName, String userEmail, String userPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.INSERT_NEW_USER);
			
			statement.setString(1, userName);
			statement.setString(2, userEmail);
			statement.setString(3, userPassword);
			
			statement.executeUpdate();		
			connection.close();
			
			User user = searchUserByEmail(userEmail);
			return user;
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public boolean registerDoctor(int userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.INSERT_NEW_DOCTOR);
			
			statement.setInt(1, userId);
			
			statement.executeUpdate();
			connection.close();
			return true;
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return false;
		}
	}
	
	public boolean registerPatient(int userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.INSERT_NEW_PATIENT);
			
			statement.setInt(1, userId);
			
			statement.executeUpdate();
			connection.close();
			return true;
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return false;
		}
	}
	
	public User searchUserByEmail(String userEmail) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_USER_BY_EMAIL);
			
			statement.setString(1, userEmail);
			ResultSet resultSet = statement.executeQuery();	
			
			if(resultSet.next()) {
				User user = new User(resultSet.getInt("userId"), resultSet.getString("userName"), resultSet.getString("userEmail"), resultSet.getString("userPassword"));
				connection.close();
				return user;
			} else {
				connection.close();
				return null;
			}
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public Patient searchPatientByUserId (User user) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_PATIENT_BY_USERID);
			
			statement.setInt(1, user.getUserId());
			ResultSet resultSet = statement.executeQuery();	
			
			if(resultSet.next()) {
				Patient patient = new Patient(resultSet.getInt("patientId"), resultSet.getString("patientIdNumber"), user);
				connection.close();
				return patient;
			} else {
				connection.close();
				return null;
			}
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public Doctor searchDoctorByUserId (User user) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_DOCTOR_BY_USERID);
			
			statement.setInt(1, user.getUserId());
			ResultSet resultSet = statement.executeQuery();	
			
			if(resultSet.next()) {
				Doctor doctor = new Doctor(resultSet.getInt("doctorId"), resultSet.getString("doctorIdentification"), user);
				connection.close();
				return doctor;
			} else {
				connection.close();
				return null;
			}
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}
}






















