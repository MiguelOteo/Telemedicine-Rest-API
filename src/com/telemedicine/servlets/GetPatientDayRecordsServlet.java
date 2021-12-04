package com.telemedicine.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.telemedicine.dao.ControllerMySQL;
import com.telemedicine.models.APIRequest;
import com.telemedicine.models.APIResponse;
import com.telemedicine.models.BitalinoPackage;

public class GetPatientDayRecordsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public GetPatientDayRecordsServlet() {super();}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		APIResponse responseModel = new APIResponse();
		ControllerMySQL controllerMySQL = new ControllerMySQL();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		APIRequest requestAPI = new Gson().fromJson(request.getParameter("APIRequest"), APIRequest.class);
		
		if (requestAPI.getPatientId() != 0 && requestAPI.getDate() != null) {
			
			List<BitalinoPackage> dayRecords = controllerMySQL.getPatientDayRecords(requestAPI.getPatientId(), requestAPI.getDate());
			if(dayRecords != null) {
				
				if(dayRecords.size() != 0) {
					responseModel.setDayRecords(dayRecords);
					sendData(responseModel, response);	
				} else {
					sendMessage("No data recorded that day", true, response);
				}
			} else {
				sendMessage("Error searching the data", true, response);
			}	
		} else {
			sendMessage("Parameters Missing", true, response);
		}
	}
	
	private void sendMessage(String message, boolean error, HttpServletResponse response) throws ServletException, IOException {

		APIResponse responseModel = new APIResponse();
		responseModel.setError(error);
		responseModel.setAPImessage(message);
		response.getWriter().print(new Gson().toJson(responseModel));
		response.getWriter().flush();
	}
	
	private void sendData(APIResponse responseModel, HttpServletResponse response) throws  ServletException, IOException {
		
		responseModel.setError(false);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'").create();
		response.getWriter().print(gson.toJson(responseModel));
		response.getWriter().flush();
	}
}
