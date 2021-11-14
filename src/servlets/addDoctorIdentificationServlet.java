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

@WebServlet("/addDoctorIdentification")
public class addDoctorIdentificationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public addDoctorIdentificationServlet() {super();}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println(request.getParameter("doctorId"));
		System.out.println(request.getParameter("doctorIdentification"));

		if(request.getParameter("doctorId") != "" && request.getParameter("doctorIdentification") != "") {
			
			int doctorId = Integer.parseInt(request.getParameter("doctorId"));
			String doctorIdentification = request.getParameter("doctorIdentification");

			boolean updateResult = controllerMySQL.addDoctorIdentification(doctorIdentification, doctorId);
			if(updateResult) {
				
				sendMessage("ID inserted successfuly", !updateResult, response);
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
