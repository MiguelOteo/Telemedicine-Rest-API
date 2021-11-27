package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import MySQL.ControllerMySQL;
import models.APIRequest;
import models.APIResponse;
import models.Patient;

public class GetPatientInformationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;   

    public GetPatientInformationServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
				
		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);

		if(requestAPI.getPatientId() != 0) {
			
			Patient patient = controllerMySQL.searchPatientByPatientId(requestAPI.getPatientId());
			if(patient != null) {
				sendPatient(patient, response);
			} else {
				sendMessage("Patient not found", true, response);
			}
			
		} else {
			sendMessage("Patient ID missing", true, response);
		}
	}
	
	private void sendPatient(Patient patient, HttpServletResponse response) throws ServletException, IOException {
		
		APIResponse responseModel = new APIResponse();
		responseModel.setError(false);
		responseModel.setPatient(patient);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}
	
	private void sendMessage(String message, boolean error, HttpServletResponse response) throws ServletException, IOException {
		
		APIResponse responseModel = new APIResponse();
		responseModel.setError(error);
		responseModel.setAPImessage(message);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}
}
