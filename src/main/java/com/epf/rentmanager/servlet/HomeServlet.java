package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private VehiculeService vehiculeService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int vehicleCount = vehiculeService.countVehicles();
			request.setAttribute("vehicleCount", vehicleCount);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (ServiceException e) {
			// Handle errors
			request.setAttribute("errorMessage", "Une erreur est survenue lors de la récupération du nombre de véhicules");
			request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
		}
	}
}
