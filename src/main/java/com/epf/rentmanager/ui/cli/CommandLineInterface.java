package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Reservation;
import com.epf.rentmanager.models.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {

    static ApplicationContext context = new
            AnnotationConfigApplicationContext(AppConfiguration.class);
    static ClientService clientService = context.getBean(ClientService.class);
    static VehiculeService vehicleService = context.getBean(VehiculeService.class);
    static ReservationService reservationService = context.getBean(ReservationService.class);

    private Scanner scanner;
    public CommandLineInterface(ClientService clientService, VehiculeService vehicleService, ReservationService reservationService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choisissez une option:");
            System.out.println("1. Créer un client");
            System.out.println("2. Lister les clients");
            System.out.println("3. Créer un véhicule");
            System.out.println("4. Lister tous les véhicules");
            System.out.println("5. Supprimer un client");
            System.out.println("6. Supprimer un véhicule");
            System.out.println("7. Créer une réservation");
            System.out.println("8. Lister toutes les réservations");
            System.out.println("9. Lister toutes les réservations d'un client");
            System.out.println("10. Lister toutes les réservations d'un véhicule");
            System.out.println("11. Supprimer une réservation");
            System.out.println("0. Sortir");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listAllClients();
                    break;
                case 3:
                    createVehicle();
                    break;
                case 4:
                    listAllVehicles();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 6:
                    deleteVehicle();
                    break;
                case 7:
                    createReservation();
                    break;
                case 8:
                    listAllReservations();
                    break;
                case 9:
                    listReservationsByClientId();
                    break;
                case 10:
                    listReservationsByVehicleId();
                    break;
                case 11:
                    deleteReservation();
                    break;
                case 0:
                    System.out.println("Interruption...");
                    running = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option invalide, réessayez...");
                    break;
            }
        }
    }

    private void createClient() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez le nom du client:");
        String prénom = scanner.nextLine();

        System.out.println("Entrez le prénom du client:");
        String nom = scanner.nextLine();

        System.out.println("Entrez le mail du client:");
        String email = scanner.nextLine();

        System.out.println("Entrez la date de naissance du client (AAAA-MM-JJ):");
        String naissanceString = scanner.nextLine();
        LocalDate naissance = LocalDate.parse(naissanceString);

        try {
            Client newClient = new Client(nom, prénom, email, naissance);
            clientService.create(newClient);
            System.out.println("Client créé avec succès!");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création du client: " + e.getMessage());
        }
    }

    private void listAllClients() {
        try {
            List<Client> clients = clientService.findAll();
            System.out.println("Liste des Clients:");
            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors du listage des clients: " + e.getMessage());
        }
    }

    private void createVehicle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez le constructeur du vehicule:");
        String manufacturer = scanner.nextLine();

        System.out.println("Entrez le modele du vehicule:");
        String modele = scanner.nextLine();

        System.out.println("Entrez le nombre de sièges dans le véhicule:");
        int seats = scanner.nextInt();

        try {
            Vehicule newVehicle = new Vehicule(manufacturer, modele, seats);
            vehicleService.create(newVehicle);
            System.out.println("Vehicule créé avec succès!");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création des vehicules: " + e.getMessage());
        }
    }

    private void listAllVehicles() {
        try {
            List<Vehicule> vehicles = vehicleService.findAll();
            System.out.println("Liste des véhicules:");
            for (Vehicule vehicle : vehicles) {
                System.out.println(vehicle);
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors du listage des vehicules: " + e.getMessage());
        }
    }

    private void deleteClient() {
        long id = IOUtils.readInt("ID du client à supprimer : ");
        try {
            Client client = clientService.findById(id);
            clientService.delete(client);
            System.out.println("Le client a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    private void deleteVehicle() {
        long id = IOUtils.readInt("ID du véhicule à supprimer : ");
        try {
            Vehicule vehicle = vehicleService.findById(id);
            vehicleService.delete(vehicle);
            System.out.println("Le véhicule a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression du véhicule : " + e.getMessage());
        }
    }

    public void createReservation() {
        try {
            System.out.println("Entrez l'ID client:");
            int clientId = scanner.nextInt();
            System.out.println("Entrez l'ID vehicule:");
            int vehicleId = scanner.nextInt();
            System.out.println("Entrez la date de début (YYYY-MM-DD):");
            LocalDate startDate = LocalDate.parse(scanner.next());
            System.out.println("Entrez la date de fin (YYYY-MM-DD):");
            LocalDate endDate = LocalDate.parse(scanner.next());

            Reservation reservation = new Reservation(clientId, vehicleId, startDate, endDate);
            long reservationId = reservationService.createReservation(reservation);

            System.out.println("Reservation created with ID: " + reservationId);
        } catch (Exception e) {
            System.out.println("Error creating reservation: " + e.getMessage());
        }
    }

    public void listAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            if (reservations.isEmpty()) {
                System.out.println("Pas de réservation trouvée");
            } else {
                System.out.println("Toutes les réservations:");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du listage de toutes les réservations: " + e.getMessage());
        }
    }

    public void listReservationsByClientId() {
        try {
            System.out.println("Entrez l'ID du client:");
            int clientId = scanner.nextInt();
            List<Reservation> reservations = reservationService.getReservationsByClientId(clientId);
            if (reservations.isEmpty()) {
                System.out.println("Pas de réservation pour le client d'ID " + clientId);
            } else {
                System.out.println("Réservation pour le client d'ID: " + clientId + ":");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du listage des réservations pour le client d'ID: " + e.getMessage());
        }
    }

    public void listReservationsByVehicleId() {
        try {
            System.out.println("Entrez l'ID du vehicule: ");
            int vehicleId = scanner.nextInt();
            List<Reservation> reservations = reservationService.getReservationsByVehicleId(vehicleId);
            if (reservations.isEmpty()) {
                System.out.println("Pas de réservation pour le véhicule avec ID " + vehicleId);
            } else {
                System.out.println("Reservations pour le vehicule avec ID " + vehicleId + ":");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du listing des réservations pour le véhicule dont l'ID est: " + e.getMessage());
        }
    }

    public void deleteReservation() {
        try {
            System.out.println("Entrez l'ID de la réservation à supprimer:");
            int reservationId = scanner.nextInt();
            Reservation reservation = new Reservation();
            reservation.setId(reservationId);
            reservationService.deleteReservation(reservation);
            System.out.println("Reservation avec ID " + reservationId + " supprimée avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }

}
