package com.telemedicine.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePatientDataServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public UpdatePatientDataServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ControllerMySQL controllerMySQL = new ControllerMySQL();
		
		// --> You need to call this method to insert the data (weight and height) into the patient
		// boolean result = controllerMySQL.updatePatientData(patientId, patientWeight, patientHeight);
	}
}
