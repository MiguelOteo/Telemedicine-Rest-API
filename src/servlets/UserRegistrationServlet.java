package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import MySQL.ControllerMySQL;
import encryption.SaltBASE64Encryption;
import models.APIRequest;
import models.APIResponse;
import models.User;

public class UserRegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public UserRegistrationServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

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
						if(requestAPI.getUserType().equals("Patient")) {
							
							Boolean patientCreated = controllerMySQL.registerPatient(user.getUserId());
							if(patientCreated == true) {
								
								sendMessage("Patient account created", false, response);
							} else {
								sendMessage("Error inserting new patient", true, response);
							}
						} else {
							
							Boolean doctorCreated = controllerMySQL.registerDoctor(user.getUserId());
							if(doctorCreated == true) {
								
								sendMessage("Doctor account created", false, response);
							} else {
								sendMessage("Error inserting new doctor", true, response);
							}
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




