package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.models.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private final ClientDao clientDao;

	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}



	public long create(Client client) throws ServiceException {
		validateClient(client);
		client.setNom(client.getNom().toUpperCase());
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création d'un client");
		}
	}

	public void delete(Client client) throws ServiceException {
		try {
			clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du client");
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche d'un client par id");
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Error lors du listage de l'ensemble des clients");
		}
	}

	private void validateClient(Client client) throws ServiceException {
		if (client.getNom().isEmpty() || client.getPrénom().isEmpty()) {
			throw new ServiceException("Le prénom ou nom du client ne peut être vide");
		}
	}
}
