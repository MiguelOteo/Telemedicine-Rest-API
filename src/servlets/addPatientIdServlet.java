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

@WebServlet("/addPatientIdNumber")
public class addPatientIdServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public addPatientIdServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println("Patient ID: " + request.getParameter("patientId"));
		System.out.println("Patient ID numb: " +request.getParameter("patientIdNumber"));

		if(request.getParameter("patientId") != "" && request.getParameter("patientIdNumber") != "") {
			
			int patientId = Integer.parseInt(request.getParameter("patientId"));
			String patientIdNumber = request.getParameter("patientIdnumber");

			boolean updateResult = controllerMySQL.addPatientIdNumber(patientIdNumber, patientId);
			if(updateResult) {
				
				sendMessage("Id inserted successfuly", !updateResult, response);
			} else {
				sendMessage("ID insertion error", !updateResult, response);
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
