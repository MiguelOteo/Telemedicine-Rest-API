package models;

public class APIResponse {
	
	// Tell if response is an error or not
	private boolean error;
	
	// API response if error is false
	private int APIresponseId;
	private Doctor doctor;
	private Patient patient;
	
	// Return the API error message
	private String errorMsg;
	
	// Default constructor
	public APIResponse() {}
	
	// Constructor
	public APIResponse(int APIresponseId, boolean error, Doctor doctor, Patient patient, String errorMsg) {
		this.error = error;
		this.APIresponseId = APIresponseId;
		this.doctor = doctor;
		this.patient = patient;
		this.errorMsg = errorMsg;
	}

	// Getter and Setter methods
	public boolean isError() {return error;}

	public void setError(boolean error) {this.error = error;}

	public int getAPIresponseId() {return APIresponseId;}

	public void setAPIresponseId(int APIresponseId) {this.APIresponseId = APIresponseId;}

	public User getDoctor() {return doctor;}

	public void setDoctor(Doctor doctor) {this.doctor =doctor;}
	
	public Patient getPatient() {return patient;}

	public void setPatient(Patient patient) {this.patient = patient;}

	public String getErrorMsg() {return errorMsg;}

	public void setErrorMsg(String errorMsg) {this.errorMsg = errorMsg;}
}
