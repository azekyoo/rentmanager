package com.epf.rentmanager.servlet;

import com.epf.rentmanager.models.Vehicule;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("constructeur");
        String modele = request.getParameter("modele");
        String nbPlacesStr = request.getParameter("nbPlaces");

        if (nbPlacesStr == null || nbPlacesStr.isEmpty()) {
            nbPlacesStr = "0";
        }

        int nbPlaces = Integer.parseInt(nbPlacesStr);

        Vehicule vehicle = new Vehicule(constructeur, modele, nbPlaces);

        try {
            VehiculeService.getInstance().create(vehicle);
            response.sendRedirect(request.getContextPath() + "/success.jsp");
        } catch (ServiceException e) {
            // Log the error for debugging purposes
            e.printStackTrace();

            // Set an attribute with the error message
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la création du véhicule");

            // Forward the request to a generic error page
            request.getRequestDispatcher("/WEB-INF/views/errorPage.jsp").forward(request, response);
        }
    }
}
