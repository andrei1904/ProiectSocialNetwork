package socialnetwork.ui;


import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;

import java.util.Random;
import java.util.Scanner;

public class ConsoleUserInterface {
    UtilizatorService utilizatorService;

    public ConsoleUserInterface(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    private void uiAddUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti prenumele: ");
        String firstName = scanner.nextLine();

        System.out.println("Introduceti numele: ");
        String lastName = scanner.nextLine();

        Utilizator utilizator = new Utilizator(firstName, lastName);
        Random rand = new Random();
        rand.setSeed(3);
        utilizator.setId(rand.nextLong());

        System.out.println("Am adaugat: " + utilizatorService.addUtilizator(utilizator));
    }

    public void run() {
        String cmd = "";

        while (!cmd.equals("quit")) {
            System.out.println("Comenzi: add, delete, quit");
            System.out.println("Introduceti o comanda: ");

            Scanner scanner = new Scanner(System.in);
            cmd = scanner.nextLine();

            if (cmd.equals("add")) {
                uiAddUser();
            }

            if (cmd.equals("delete")) {
                uiAddUser();
            }
        }
    }
}
