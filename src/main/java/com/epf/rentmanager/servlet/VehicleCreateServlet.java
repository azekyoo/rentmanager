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
        int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));


        Vehicule vehicule = new Vehicule(constructeur, modele, nbPlaces);


        try {
            VehiculeService.getInstance().create(vehicule);

            response.sendRedirect(request.getContextPath() + "/success.jsp");
        } catch (ServiceException e) {

            request.setAttribute("errorMessage", "Une erreur est survenue lors de la création du véhicule");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
