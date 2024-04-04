package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	@Autowired
	VehiculeService vehicleService;

	@Autowired
	ClientService clientService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

	}
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int vcount = vehicleService.countVehicles();
			request.setAttribute("vehicleCount", vcount);
			int ccount = clientService.countClients();
			request.setAttribute("clientCount", ccount);
		} catch (ServiceException e) {
			throw new ServletException(e);
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

	}



}