package models;

import java.util.List;

public class Doctor extends User {

	private int doctorId;
	
	// Id of the doctor (Documentation)
	private String doctorIdentification;
	
	//List of the patients the doctor looks after
	private List<Patient> listOfPatients;
	
	// Default constructor
	public Doctor() {}
	
	// Constructor
	public Doctor(int userId, int doctorId, String doctorName, String doctorEmail, String doctorPassword, String doctorIdentification) {
		
		super(userId, doctorName, doctorEmail, doctorPassword);
		
		this.doctorId = doctorId;
		this.doctorIdentification = doctorIdentification;
		this.listOfPatients = null;
	}
	
	public Doctor(int doctorId, String doctorIdentification, User user) {
		super(user.getUserId(), user.getName(), user.getEmail(), user.getPassword());
		
		this.doctorId = doctorId;
		this.doctorIdentification = doctorIdentification;
		this.listOfPatients = null;
	}

	// Getter and Setter methods
	public String getDoctorIdNumber() {return doctorIdentification;}

	public void setDoctorIdNumber(String doctorIdentification) {this.doctorIdentification = doctorIdentification;}
	
	public int getDoctorId() {return doctorId;}

	public void setDoctorId(int doctorId) {this.doctorId = doctorId;}

	public List<Patient> getListOfPatients() {return listOfPatients;}

	public void setListOfPatients(List<Patient> listOfPatients) {this.listOfPatients = listOfPatients;}
}
