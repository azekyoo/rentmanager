package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.models.Client;
import com.epf.rentmanager.models.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehiculeService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {

    private static ClientService clientService;
    private final VehiculeService vehiculeService;

    public CommandLineInterface(ClientService clientService, VehiculeService vehicleService) {
        this.clientService = clientService;
        this.vehiculeService = vehicleService;
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
            System.out.println("Client created successfully!");
        } catch (ServiceException e) {
            System.out.println("Error creating client: " + e.getMessage());
        }
    }

    private void listAllClients() {
        try {
            List<Client> clients = clientService.findAll();
            System.out.println("List of Clients:");
            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (ServiceException e) {
            System.out.println("Error listing clients: " + e.getMessage());
        }
    }

    private void createVehicle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the vehicle manufacturer:");
        String manufacturer = scanner.nextLine();

        System.out.println("Enter the modele of the vehicle:");
        String modele = scanner.nextLine();

        System.out.println("Enter the number of seats in the vehicle:");
        int seats = scanner.nextInt();

        try {
            Vehicule newVehicle = new Vehicule(manufacturer, modele, seats);
            vehiculeService.create(newVehicle);
            System.out.println("Vehicle created successfully!");
        } catch (ServiceException e) {
            System.out.println("Error creating vehicle: " + e.getMessage());
        }
    }

    private void listAllVehicles() {
        try {
            List<Vehicule> vehicles = vehiculeService.findAll();
            System.out.println("List of Vehicles:");
            for (Vehicule vehicle : vehicles) {
                System.out.println(vehicle);
            }
        } catch (ServiceException e) {
            System.out.println("Error listing vehicles: " + e.getMessage());
        }
    }

    private static void deleteClient() {
        long id = IOUtils.readInt("ID du client à supprimer : ");
        try {
            Client client = clientService.findById(id);
            clientService.delete(client);
            System.out.println("Le client a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    private static void deleteVehicle() {
        long id = IOUtils.readInt("ID du véhicule à supprimer : ");
        try {
            Vehicule vehicle = VehiculeService.getInstance().findById(id);
            VehiculeService.delete(vehicle);
            System.out.println("Le véhicule a été supprimé avec succès !");
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la suppression du véhicule : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClientService clientService = ClientService.getInstance();
        VehiculeService vehicleService = VehiculeService.getInstance();
        CommandLineInterface cli = new CommandLineInterface(clientService, vehicleService);
        cli.start();
    }
}
