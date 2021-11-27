package com.telemedicine.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.telemedicine.dao.ControllerMySQL;
import com.telemedicine.models.APIRequest;
import com.telemedicine.models.APIResponse;
import com.telemedicine.models.Patient;

public class AddPatientsToDoctorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public AddPatientsToDoctorServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		APIResponse responseModel = new APIResponse();
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);

		int doctorId = requestAPI.getDoctorId();

		if (controllerMySQL.searchDoctorByDoctorId(doctorId) != null) {

			for (Integer patientId : requestAPI.getSelectedPatients()) {

				boolean insertion = controllerMySQL.addPatientToDoctor(doctorId, patientId);

				if (!insertion) {
					sendMessage("Error adding the patients", true, response);
					break;
				}
			}

			List<Patient> patientList = controllerMySQL.listAllPatientsWithOutDoctor();

			if (patientList != null) {
				responseModel.setNoDoctorPatients(patientList);
				sendPatients(responseModel, response);
			} else {
				sendMessage("No patients found", false, response);
			}

		} else {
			sendMessage("Error verifiying your account", true, response);
		}
	}

	private void sendMessage(String message, boolean error, HttpServletResponse response) throws ServletException, IOException {

		APIResponse responseModel = new APIResponse();
		responseModel.setError(error);
		responseModel.setAPImessage(message);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}

	private void sendPatients(APIResponse responseModel, HttpServletResponse response) throws ServletException, IOException {

		responseModel.setError(false);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}

}
