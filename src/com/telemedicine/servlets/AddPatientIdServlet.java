package com.telemedicine.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.telemedicine.dao.ControllerMySQL;
import com.telemedicine.models.APIRequest;
import com.telemedicine.models.APIResponse;

public class AddPatientIdServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public AddPatientIdServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
				
		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);

		if(requestAPI.getPatientId() != 0 && requestAPI.getPatientIdNumber() != null) {

			boolean updateResult = controllerMySQL.addPatientIdNumber(requestAPI.getPatientIdNumber(), requestAPI.getPatientId());
			if(updateResult) {
				
				sendMessage("Id inserted successfuly", !updateResult, response);
			} else {
				sendMessage("ID insertion error", !updateResult, response);
			}
			
		} else {
			sendMessage("Parameters missing", true, response);
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
