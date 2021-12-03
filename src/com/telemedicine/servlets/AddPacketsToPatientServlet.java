package com.telemedicine.servlets;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.telemedicine.dao.ControllerMySQL;
import com.telemedicine.models.APIRequest;
import com.telemedicine.models.APIResponse;
import com.telemedicine.models.BitalinoPackage;

public class AddPacketsToPatientServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public AddPacketsToPatientServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);

		BitalinoPackage bitalinopackage = requestAPI.getBitalinopackage();
		int patientId = bitalinopackage.getPatientId();
		
		if (controllerMySQL.searchPatientByPatientId(patientId) != null) {

				Timestamp packageDate = bitalinopackage.getRecordsDate();
				int packageFrequency = bitalinopackage.getRecordFreq();
				String packageEMG = bitalinopackage.getemgData();
				String packageECG = bitalinopackage.getecgData();
				
				boolean insertion = controllerMySQL.addPacketsToPatient(packageDate, packageFrequency, packageEMG, packageECG, patientId);

				if (insertion) {
					sendMessage("Package succesfully sent", false, response);
				} else {
					sendMessage("Error adding the BITalino Data Packets", true, response);
				}
				
		} else {
			sendMessage("Client not found", true, response);
		}
	}

	private void sendMessage(String message, boolean error, HttpServletResponse response) throws ServletException, IOException {

		APIResponse responseModel = new APIResponse();
		responseModel.setError(error);
		responseModel.setAPImessage(message);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}
}
