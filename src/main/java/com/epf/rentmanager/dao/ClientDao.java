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

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.models.Client;

public class ClientDao {

	private static ClientDao instance = null;

	private ClientDao() {
	}

	public static ClientDao getInstance() {
		if (instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY,
					 Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrénom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException("Erreur lors de la création de client, pas de rows affectées");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("Erreur lors de la création de client, pas d'id obtenu");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de client");
		}
	}

	public long delete(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY)) {

			preparedStatement.setLong(1, client.getId());
			return preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de client");
		}
	}

	public Client findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY)) {

			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return extractClientFromResultSet(resultSet);
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de client par id");
		}

		throw new DaoException("Client non trouvé a pour id " + id);
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();

		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(FIND_CLIENTS_QUERY);

			while (resultSet.next()) {
				clients.add(extractClientFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du listage de l'ensemble des clients");
		}

		return clients;
	}

	private Client extractClientFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String nom = resultSet.getString("nom");
		String prenom = resultSet.getString("prenom");
		String email = resultSet.getString("email");
		LocalDate birthdate = resultSet.getDate("naissance").toLocalDate();

		return new Client(id, nom, prenom, email, birthdate);

	}
}
