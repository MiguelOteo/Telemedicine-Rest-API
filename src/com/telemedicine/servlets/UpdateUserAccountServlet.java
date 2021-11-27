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
import com.telemedicine.models.User;

public class UpdateUserAccountServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public UpdateUserAccountServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);
		
		if(requestAPI.getUserId() != 0) {
			
			if(requestAPI.getUserEmail() == null && requestAPI.getUserName() == null) {
				
				sendMessage("Parameters Missing", true, response);
			} else {
			
				User user = controllerMySQL.searchUserById(requestAPI.getUserId());
				if(user != null) {
					
					if(requestAPI.getUserEmail() == null) {
						
						boolean result = controllerMySQL.updateAccount(requestAPI.getUserName(), user.getEmail(), requestAPI.getUserId());
						if(result) {
							sendAccountData(requestAPI.getUserName(), "", "Name updated successfully", response);
						} else {
							sendMessage("Error updating your name", true, response);
						}
					} else {
						
						User userExists = controllerMySQL.searchUserByEmail(requestAPI.getUserEmail());
						if(userExists != null) {
							
							sendMessage("Email already in used", true, response);
						} else {
							boolean result = controllerMySQL.updateAccount(user.getName(), requestAPI.getUserEmail(), requestAPI.getUserId());
							if(result) {
								sendAccountData("", requestAPI.getUserEmail(), "Email updated successfully", response);
							} else {
								sendMessage("Error updating your email", true, response);
							}
						}
					}	
				} else {
					sendMessage("User could not be found", true, response);
				}
			}
		} else {
			sendMessage("User ID Missing", true, response);
		}
	}
	
	private void sendAccountData(String userName, String userEmail, String message, HttpServletResponse response) throws ServletException, IOException {
		
		APIResponse responseModel = new APIResponse();
		if(!userEmail.equals("")) {	responseModel.setUserEmail(userEmail);}
		if(!userName.equals("")) {responseModel.setUserName(userName);}
		responseModel.setError(false);
		responseModel.setAPImessage(message);
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
