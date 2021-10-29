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
		
		APIResponse responseModel = new APIResponse();
		Gson gsonConverter = new Gson();
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
								responseModel.setError(false);
								response.getWriter().print(gsonConverter.toJson(responseModel));
								response.getWriter().flush();
							} else {
								responseModel.setError(true);
								responseModel.setErrorMsg("Error inserting new patient");
								response.getWriter().print(gsonConverter.toJson(responseModel));
								response.getWriter().flush();
							}
						} else {
							
							Boolean doctorCreated = controllerMySQL.registerDoctor(user.getUserId());
							
							if(doctorCreated == true) {
								responseModel.setError(false);
								response.getWriter().print(gsonConverter.toJson(responseModel));
								response.getWriter().flush();
							} else {
								responseModel.setError(true);
								responseModel.setErrorMsg("Error inserting new doctor");
								response.getWriter().print(gsonConverter.toJson(responseModel));
								response.getWriter().flush();
							}
						}
					} else {
						responseModel.setError(true);
						responseModel.setErrorMsg("Error inserting new user");
						response.getWriter().print(gsonConverter.toJson(responseModel));
						response.getWriter().flush();
					}
				} else {
					responseModel.setError(true);
					responseModel.setErrorMsg("User already exists");
					response.getWriter().print(gsonConverter.toJson(responseModel));
					response.getWriter().flush();
				}
			} else {
				responseModel.setError(true);
				responseModel.setErrorMsg("Password is not the same");
				response.getWriter().print(gsonConverter.toJson(responseModel));
				response.getWriter().flush();
			}
		} else {
			responseModel.setError(true);
			responseModel.setErrorMsg("Parameters missing");
			response.getWriter().print(gsonConverter.toJson(responseModel));
			response.getWriter().flush();
		}
	}
}




