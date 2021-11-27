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

public class ChangePasswordServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public ChangePasswordServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);
		
		if(requestAPI.getUserId() != 0 && requestAPI.getUserPassword() != null && requestAPI.getUserNewPassword() != null) {
			
			User user = controllerMySQL.searchUserById(requestAPI.getUserId());
			if(user != null) {
				
				boolean oldPasswordCorrect = SaltBASE64Encryption.verifyUserPassword(requestAPI.getUserPassword(), user.getEncryptedPassword(), user.getUserSalt());
				if(oldPasswordCorrect) {
					
					String newEncryptedPassword = SaltBASE64Encryption.encryptPassword(requestAPI.getUserNewPassword(), user.getUserSalt());
					
					boolean result = controllerMySQL.changePassword(newEncryptedPassword, requestAPI.getUserId());
					if(result) {
						sendPassword(newEncryptedPassword, "Password changed", response);	
					} else {
						sendMessage("Error updating password", true, response);
					}
				} else {
					sendMessage("Old password is not correct", true, response);
				}	
			} else {
				sendMessage("User could not be found", true, response);
			}
		} else {
			sendMessage("Parameters missing", true, response);
		}
	}
	
	private void sendPassword(String encryptedPassword, String message, HttpServletResponse response) throws ServletException, IOException {
		
		APIResponse responseModel = new APIResponse();
		responseModel.setEncryptedPassword(encryptedPassword);
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
