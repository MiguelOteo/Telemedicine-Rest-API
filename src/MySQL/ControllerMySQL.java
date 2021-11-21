package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import common.CommonParams;
import models.Doctor;
import models.Patient;
import models.User;

public class ControllerMySQL {

	public User registerUser(String userName, String userEmail, String userEncryptedPassword, String userSalt) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.INSERT_NEW_USER);
			
			statement.setString(1, userName);
			statement.setString(2, userEmail);
			statement.setString(3, userEncryptedPassword);
			statement.setString(4, userSalt);
			
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
				User user = new User(resultSet.getInt("userId"), resultSet.getString("userName"), resultSet.getString("userEmail")
						, resultSet.getString("userEncryptedPassword"), resultSet.getString("userSalt"));
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
	
public Patient searchPatientByPatientId (int patientId) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_PATIENT_BY_PATIENTID);
			
			statement.setInt(1, patientId);
			ResultSet resultSet = statement.executeQuery();	
			
			if(resultSet.next()) {
				User user = searchUserById(resultSet.getInt("userId"));
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
	
	public User searchUserById(int userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_USER_BY_USERID);
			
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				User user = new User(resultSet.getInt("userId"), resultSet.getString("userName"), resultSet.getString("userEmail")
						, resultSet.getString("userEncryptedPassword"), resultSet.getString("userSalt"));
				connection.close();
				return user;
			} else {
				connection.close();
				return null;
			}
			
		} catch (Exception error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public List<Patient> listAllPatientsWithOutDoctor() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.LIST_ALL_PATIENTS);
			
			ResultSet resultSet = statement.executeQuery();
			
			List<Patient> patientsList = new LinkedList<Patient>();
			while(resultSet.next()) {
				User user = searchUserById(resultSet.getInt("userId"));
				Patient patient = new Patient(resultSet.getInt("patientId"), resultSet.getString("patientIdNumber"), user);
				patientsList.add(patient);
			}
			
			connection.close();
			return patientsList;
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public List<Patient> listAllDoctorPatients(int doctorId) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.LIST_ALL_DOCTOR_PATIENTS);
			
			statement.setInt(1, doctorId);
			ResultSet resultSet = statement.executeQuery();
			
			List<Patient> patientsList = new LinkedList<Patient>();
			while(resultSet.next()) {
				User user = searchUserById(resultSet.getInt("userId"));
				Patient patient = new Patient(resultSet.getInt("patientId"), resultSet.getString("patientIdNumber"), user);
				patientsList.add(patient);
			}
			
			connection.close();
			return patientsList;
			
		} catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public boolean addPatientToDoctor(int doctorId, int patientId) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.UPDATE_PATIENT_DOCTOR);
			
			statement.setInt(1, doctorId);
			statement.setInt(2, patientId);
			int result = statement.executeUpdate();

			if(result != 0) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;
			}
		}  catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePatientAssignment(int patientId) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.UPDATE_PATIENT_ASSIGNMENT_TO_NULL);
			
			statement.setInt(1, patientId);
			int result = statement.executeUpdate();

			if(result != 0) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;
			}
		}  catch(SQLException | ClassNotFoundException error) {
			error.printStackTrace();
			return false;
		}
	}
	
	public Doctor searchDoctorByDoctorId(int doctorId){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.SEARCH_DOCTOR_BY_DOCTORID);
			
			statement.setInt(1, doctorId);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				User user = searchUserById(resultSet.getInt("userId"));
				Doctor doctor = new Doctor(resultSet.getInt("doctorId"), resultSet.getString("doctorIdentification"), user);
				return doctor;
			} else {
				return null;
			}
			
		} catch (Exception error) {
			error.printStackTrace();
			return null;
		}
	}
	
	public boolean addPatientIdNumber(String patientIdNumber, int patientId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.UPDATE_PATIENT_ID_NUMBER);
			
			statement.setString(1, patientIdNumber);
			statement.setInt(2, patientId);
			
			int result = statement.executeUpdate();
			statement.close();
			
			if(result != 0) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;
			}

		} catch (Exception error) {
			error.printStackTrace();
			return false;
		}
		
	}
	
	public boolean addDoctorIdentification(String doctorIdentification, int doctorId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(CommonParams.BASE_DB_URL, CommonParams.DB_HOST, CommonParams.DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement(CommonParams.UPDATE_DOCTOR_DOCTOR_IDENTIFICATION);
			
			statement.setString(1, doctorIdentification);
			statement.setInt(2, doctorId);
			
			int result = statement.executeUpdate();
			statement.close();
			
			if(result != 0) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;
			}
			
		} catch (Exception error) {
			error.printStackTrace();
			return false;
		}
	}
}






















