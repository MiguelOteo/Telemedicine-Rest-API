package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import MySQL.ControllerMySQL;
import models.APIResponse;
import models.User;

@WebServlet("/userRegistration")
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
		
		if(request.getParameter("userName") != "" && request.getParameter("userEmail") != "" && request.getParameter("userPassword") != "" && request.getParameter("userRepeatPassword") != "") {
			
			String password = request.getParameter("userPassword");
			String passwordRepeat = request.getParameter("userRepeatPassword");
			
			if(password.equals(passwordRepeat)) {
						
				String userName = request.getParameter("userName");
				String userEmail = request.getParameter("userEmail");
				String userType = request.getParameter("userType");
				
				User userExists = controllerMySQL.searchUserByEmail(userEmail);
				
				if(userExists == null) {
					
					User user = controllerMySQL.registerUser(userName, userEmail, password);
					
					if(user != null) {
						if(userType.equals("Patient")) {
							
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
		
		Gson gsonConverter = new Gson();
		APIResponse responseModel = new APIResponse();
		responseModel.setError(error);
		responseModel.setAPImessage(message);
		response.getWriter().print(gsonConverter.toJson(responseModel));
		response.getWriter().flush();
	}
}




