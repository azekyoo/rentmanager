package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.models.Vehicule;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {

	private static VehicleDao instance = null;

	private VehicleDao() {
	}

	public static VehicleDao getInstance() {
		if (instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";

	public long create(Vehicule vehicule) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY,
					 Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, vehicule.getConstructeur());
			preparedStatement.setInt(2, vehicule.getNb_places());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException("Erreur lors de la création de véhicule, pas de rows affectées");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("Erreur lors de la création de véhicule, pas d'id obtenu");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de véhicule");
		}
	}

	public long delete(Vehicule vehicule) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, vehicule.getId());
			return preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de véhicule");
		}
	}

	public Vehicule findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return extractVehiculeFromResultSet(resultSet);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de véhicule par id");
		}

		throw new DaoException("Véhicule non trouvé a pour id " + id);
	}

	public List<Vehicule> findAll() throws DaoException {
		List<Vehicule> vehicles = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(FIND_VEHICLES_QUERY);

			while (resultSet.next()) {
				vehicles.add(extractVehiculeFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du listage de l'ensemble des véhicules");
		}

		return vehicles;
	}

	private Vehicule extractVehiculeFromResultSet(ResultSet resultSet) throws SQLException {
		Vehicule vehicule = new Vehicule();
		vehicule.setId((int) resultSet.getLong("id"));
		vehicule.setConstructeur(resultSet.getString("constructeur"));
		vehicule.setNb_places(resultSet.getInt("nb_places"));
		return vehicule;
	}
}
