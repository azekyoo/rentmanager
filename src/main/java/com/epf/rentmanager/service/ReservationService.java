package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.models.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;

    private static ReservationService instance;

    public ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }

    public long createReservation(Reservation reservation) throws DaoException {
        return reservationDao.create(reservation);
    }

    public List<Reservation> getAllReservations() throws DaoException {
        return reservationDao.findAll();
    }

    public List<Reservation> getReservationsByClientId(long clientId) throws DaoException {
        return reservationDao.findResaByClientId(clientId);
    }

    public List<Reservation> getReservationsByVehicleId(long vehicleId) throws DaoException {
        return reservationDao.findResaByVehicleId(vehicleId);
    }

    public void deleteReservation(Reservation reservation) throws DaoException {
        reservationDao.delete(reservation);
    }
}
