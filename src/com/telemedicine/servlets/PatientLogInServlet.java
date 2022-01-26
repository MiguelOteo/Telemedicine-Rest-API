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
import com.telemedicine.models.Patient;
import com.telemedicine.models.User;

public class PatientLogInServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public PatientLogInServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		APIResponse responseModel = new APIResponse();
		ControllerMySQL controllerMySQL = new ControllerMySQL(); 
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);
        
        if(requestAPI.getUserEmail() != null && requestAPI.getUserPassword() != null) {
        	
        	User user = controllerMySQL.searchUserByEmail(requestAPI.getUserEmail());
        	
        	if(user != null) {
        		
        		if(SaltBASE64Encryption.verifyUserPassword(requestAPI.getUserPassword(), user.getEncryptedPassword(), user.getUserSalt())) {
        			
        			Patient patient = controllerMySQL.searchPatientByUserId(user);
        			
        			if(patient != null) {
        				
            			responseModel.setPatient(patient);
            			sendAPImodel(responseModel, response);
        			} else {
    
        				sendMessage("Patient account not found", true, response);
        			}
        		} else {
        			sendMessage("Incorrect password", true, response);
        		}
        	} else {
        		sendMessage("No user found with that email", true, response);
        	}
        } else {
        	sendMessage("Parameters missing", true, response);
        }
	}
	
	public void sendAPImodel(APIResponse responseModel, HttpServletResponse response) throws ServletException, IOException {
		
		responseModel.setError(false);
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
