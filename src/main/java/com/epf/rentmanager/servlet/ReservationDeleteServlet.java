package com.epf.rentmanager.servlet;

import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rent/delete")
public class ReservationDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long reservationId = Long.parseLong(request.getParameter("id"));

        try {

            reservationService.delete(reservationId);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting the reservation.");
        }
    }

}
