package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the JSP for creating reservations
        request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve parameters from the request
            int clientId = Integer.parseInt(request.getParameter("clientId"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            LocalDate debut = LocalDate.parse(request.getParameter("debut"));
            LocalDate fin = LocalDate.parse(request.getParameter("fin"));

            Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);

            reservationService.create(reservation);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (NumberFormatException | ServiceException e) {

            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating reservation");
        }
    }
}
