package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import MySQL.ControllerMySQL;
import models.User;

@WebServlet("/userRegistration")
public class UserRegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public UserRegistrationServlet() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject json =  new JSONObject();
		ControllerMySQL controllerMySQL = new ControllerMySQL(); 
		
		if(request.getParameter("userName") != null || request.getParameter("userEmail") != null  || request.getParameter("userPassword") != null || request.getParameter("userRepeatPassword") != null ) {
			
			String password = request.getParameter("userPassword");
			String passwordRepeat = request.getParameter("userRepeatPassword");
			
			if(password.equals(passwordRepeat)) {
						
				String userName = request.getParameter("userName");
				String userEmail = request.getParameter("userEmail");
				
				Boolean userExists = controllerMySQL.userExists(userEmail);
				
				if(userExists == false) {
					
					User user = controllerMySQL.registerUser(userName, userEmail, password);
					if(user != null) {
						
						json.put("error", "false");
						response.getOutputStream().print(json.toString());
						
					} else {
						json.put("error", "true");
						json.put("errorMsg", "Error creating the new user");
						response.getOutputStream().print(json.toString());
					}
					
				} else {
					json.put("error", "true");
					json.put("errorMsg", "User already exists");
					response.getOutputStream().print(json.toString());
				}
				
			} else {
				json.put("error", "true");
				json.put("errorMsg", "Password is not the same");
				response.getOutputStream().print(json.toString());
			}
			
		} else {
			json.put("error", "true");
			json.put("errorMsg", "Parameters missing");
			response.getOutputStream().print(json.toString());
		}
	}
}




