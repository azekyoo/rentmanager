package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;

	private ReservationDao() {
	}

	public static ReservationDao getInstance() {
		if (instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY,
					 Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setLong(1, reservation.getClient_id());
			preparedStatement.setLong(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException("Creating reservation failed, no rows affected.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("Creating reservation failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Error creating reservation", e);
		}
	}

	public long delete(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {

			preparedStatement.setLong(1, reservation.getId());
			return preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Error deleting reservation", e);
		}
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {

			preparedStatement.setLong(1, clientId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reservations.add(extractReservationFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			throw new DaoException("Error finding reservations by client ID", e);
		}

		return reservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, vehicleId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reservations.add(extractReservationFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			throw new DaoException("Error finding reservations by vehicle ID", e);
		}

		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (resultSet.next()) {
				reservations.add(extractReservationFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			throw new DaoException("Error finding all reservations", e);
		}

		return reservations;
	}

	private Reservation extractReservationFromResultSet(ResultSet resultSet) throws SQLException {
		Reservation reservation = new Reservation();
		reservation.setId((int) resultSet.getLong("id"));
		reservation.setClient_id((int) resultSet.getLong("client_id"));
		reservation.setVehicle_id((int) resultSet.getLong("vehicle_id"));
		reservation.setDebut(resultSet.getDate("debut").toLocalDate());
		reservation.setFin(resultSet.getDate("fin").toLocalDate());
		return reservation;
	}
}
