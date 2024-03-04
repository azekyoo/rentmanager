package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.models.Vehicule;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;

@WebServlet("/vehicles")
public class VehicleListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VehiculeService vehicleService = VehiculeService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Vehicule> vehicles = vehicleService.findAll();
            request.setAttribute("vehicles", vehicles);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicle/list.jsp")
                    .forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("Error while fetching vehicles", e);
        }
    }
}