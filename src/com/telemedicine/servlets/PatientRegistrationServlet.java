package com.telemedicine.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.telemedicine.dao.ControllerMySQL;
import com.telemedicine.encryption.SaltBASE64Encryption;
import com.telemedicine.models.APIRequest;
import com.telemedicine.models.APIResponse;
import com.telemedicine.models.User;

public class PatientRegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public PatientRegistrationServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL(); 
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);
		
		if(requestAPI.getUserName() != null && requestAPI.getUserEmail() != null && requestAPI.getUserPassword() != null && requestAPI.getUserPasswordRepeat() != null) {
			
			if(requestAPI.getUserPassword().equals(requestAPI.getUserPasswordRepeat())) {
								
				User userExists = controllerMySQL.searchUserByEmail(requestAPI.getUserEmail());
				
				if(userExists == null) {
					
					String userSalt = SaltBASE64Encryption.getSaltvalue(15);
					String userEncryptedPassword = SaltBASE64Encryption.encryptPassword(requestAPI.getUserPassword(), userSalt);
					
					User user = controllerMySQL.registerUser(requestAPI.getUserName(), requestAPI.getUserEmail(), userEncryptedPassword, userSalt);
					
					if(user != null) {
						
						Boolean patientCreated = controllerMySQL.registerPatient(user.getUserId());
						if(patientCreated == true) {
								
							sendMessage("Patient account created", false, response);
						} else {
							sendMessage("Error inserting new patient", true, response);
						}
					} else {
						sendMessage("Error inserting new user", true, response);
					}
				} else {
					sendMessage("User already exists", true, response);
				}
			} else {
				sendMessage("Password is not the same", true, response);
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
