package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicule;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehiculeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    VehiculeService vehicleService;

    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,IOException {
        try {
            int vehicleId = Integer.parseInt(request.getParameter("id"));
            Vehicule vehicle = new Vehicule();
            vehicle.setId(vehicleId);
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);
            for (Reservation reservation : reservations){
                System.out.println(reservation);
                reservationService.delete(reservation);
            }
            vehicleService.delete(vehicle);
            response.sendRedirect("http://localhost:8080/rentmanager/cars");
        } catch(final Exception e) {
            request.setAttribute("ErrorMessage", e.getMessage());
            e.printStackTrace();
            System.out.println("Une erreur est survenue : " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}