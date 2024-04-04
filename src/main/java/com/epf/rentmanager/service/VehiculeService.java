package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.models.Vehicule;
import org.springframework.stereotype.Service;

@Service
public class VehiculeService {

	private final VehicleDao vehiculeDao;

	public VehiculeService(VehicleDao vehiculeDao) {
		this.vehiculeDao = vehiculeDao;
	}



	public long create(Vehicule vehicule) throws ServiceException {
		validateVehicule(vehicule);
		try {
			return vehiculeDao.create(vehicule);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du véhicule");
		}
	}

	public static void delete(Vehicule vehicle) throws ServiceException {
		try {
			VehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la suppression du véhicule");
		}
	}
	public Vehicule findById(long id) throws ServiceException {
		try {
			return vehiculeDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la recherche du véhicule par id");
		}
	}

	public List<Vehicule> findAll() throws ServiceException {
		try {
			return vehiculeDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du listage de l'ensemble des véhicules");
		}
	}

	private void validateVehicule(Vehicule vehicule) throws ServiceException {
		if (vehicule.getConstructeur().isEmpty() || vehicule.getNb_places() <= 0) {
			throw new ServiceException("Impossible de vérifier le constructeur et il faut un nombre de places strictement supérieur à 0");
		}
	}

	public int countVehicles() throws ServiceException {
		try {
			return VehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du nombre de véhicules");
		}
	}

}
