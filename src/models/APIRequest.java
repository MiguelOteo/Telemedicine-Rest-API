package models;

import java.util.List;

public class APIRequest {

	private int userId;
	private String userType;
	private String userName;
	private String userEmail;
	private String userPassword;
	private String userPasswordRepeat;
	private String userNewPassword;
	
	private int doctorId;
	private int patientId;
	private String doctorIdentification;
	private String patientIdNumber;
	
	private List<Integer> selectedPatients;
	
	public APIRequest() {}

	public List<Integer> getSelectedPatients() {return selectedPatients;}

	public void setSelectedPatients(List<Integer> selectedPatients) {this.selectedPatients = selectedPatients;}

	public int getDoctorId() {return doctorId;}

	public void setDoctorId(int doctorId) {this.doctorId = doctorId;}

	public String getUserType() {return userType;}

	public void setUserType(String userType) {this.userType = userType;}

	public String getUserName() {return userName;}

	public void setUserName(String userName) {this.userName = userName;}

	public String getUserEmail() {return userEmail;}

	public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

	public String getUserPassword() {return userPassword;}

	public void setUserPassword(String userPassword) {this.userPassword = userPassword;}

	public String getUserPasswordRepeat() {return userPasswordRepeat;}

	public void setUserPasswordRepeat(String userPasswordRepeat) {this.userPasswordRepeat = userPasswordRepeat;}

	public int getPatientId() {return patientId;}

	public void setPatientId(int patientId) {this.patientId = patientId;}

	public String getDoctorIdentification() {return doctorIdentification;}

	public void setDoctorIdentification(String doctorIdentification) {this.doctorIdentification = doctorIdentification;}

	public String getPatientIdNumber() {return patientIdNumber;}

	public void setPatientIdNumber(String patientIdNumber) {this.patientIdNumber = patientIdNumber;}

	public String getUserNewPassword() {return userNewPassword;}

	public void setUserNewPassword(String userNewPassword) {this.userNewPassword = userNewPassword;}

	public int getUserId() {return userId;}

	public void setUserId(int userId) {this.userId = userId;}		
}
