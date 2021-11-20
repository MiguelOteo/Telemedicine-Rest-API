package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import MySQL.ControllerMySQL;
import models.APIRequest;
import models.APIResponse;

@WebServlet("/deletePatientAssignment")
public class DeletePatientAssignmentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public DeletePatientAssignmentServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);

		if(requestAPI.getPatientId() != 0) {
			
			boolean updateResult = controllerMySQL.deletePatientAssignment(requestAPI.getPatientId());
			if(updateResult) {
				
				sendMessage("Patient deleted successfuly", !updateResult, response);
			} else {
				sendMessage("Error deleting the patient", !updateResult, response);
			}
			
		} else {
			sendMessage("Patient ID parameter missing", true, response);
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
