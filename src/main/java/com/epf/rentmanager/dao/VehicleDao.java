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

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";

	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";

	private static final String FIND_ALL_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";


	public long create(Vehicule vehicule) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY,
					 Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, vehicule.getConstructeur());
			preparedStatement.setString(2, vehicule.getModele());
			preparedStatement.setInt(3, vehicule.getNb_places());

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


	public static long delete(Vehicule vehicule) throws DaoException {
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
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_VEHICLES_QUERY)) {

			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return extractVehicleFromResultSet(resultSet);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de véhicule par id");
		}

		throw new DaoException("Véhicule non trouvé a pour id " + id);
	}

	public List<Vehicule> findAll() throws DaoException {
		List<Vehicule> vehicles = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(FIND_ALL_VEHICLES_QUERY);
			 ResultSet resultSet = ps.executeQuery()) {

			while (resultSet.next()) {
				Vehicule vehicle = extractVehicleFromResultSet(resultSet);
				vehicles.add(vehicle);
			}
		} catch (SQLException e) {
			throw new DaoException("Une erreur est survenue lors de la récupération de tous les véhicules.", e);
		}
		return vehicles;
	}

	private Vehicule extractVehicleFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String constructeur = resultSet.getString("constructeur");
		String modele = resultSet.getString("modele");
		int nb_places = resultSet.getInt("nb_places");

		return new Vehicule(id, constructeur, modele, nb_places );
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM Vehicle");
			if (resultSet.next()) {
				return resultSet.getInt("count");
			}
			return 0; // Si aucun véhicule n'est trouvé, retourne 0
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du comptage des véhicules dans la base de données", e);
		}
	}

}
