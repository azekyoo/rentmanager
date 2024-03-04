package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int vehicleCount = VehiculeService.getInstance().countVehicles();
			request.setAttribute("vehicleCount", vehicleCount);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (ServiceException e) {
			// Gestion des erreurs
			request.setAttribute("errorMessage", "Une erreur est survenue lors de la récupération du nombre de véhicules");
			request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
		}
	}




}
