package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import MySQL.ControllerMySQL;
import encryption.SaltBASE64Encryption;
import models.APIRequest;
import models.APIResponse;
import models.Doctor;
import models.Patient;
import models.User;

@WebServlet("/userLogin")
public class UserLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public UserLoginServlet() {super();}

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
        			Doctor doctor = controllerMySQL.searchDoctorByUserId(user);
        			
        			if(patient != null && doctor == null) {
        				
            			responseModel.setPatient(patient);
            			sendAPImodel(responseModel, response);
        			} else {
        				
            			responseModel.setDoctor(doctor);
            			sendAPImodel(responseModel, response);
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
