package socialnetwork.ui;


import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.RepoException;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.PrieteniiService;
import socialnetwork.service.UtilizatorService;

import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUserInterface {
    UtilizatorService utilizatorService;
    PrietenieService prietenieService;
    PrieteniiService prieteniiService;

    Scanner scanner = new Scanner(System.in);

    public ConsoleUserInterface(UtilizatorService utilizatorService, PrietenieService prietenieService,
                                PrieteniiService prieteniiService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.prieteniiService = prieteniiService;
    }

    private void uiAddUser() {
        System.out.println("Introduceti prenumele: ");
        String firstName = scanner.nextLine();

        System.out.println("Introduceti numele: ");
        String lastName = scanner.nextLine();

        Utilizator utilizator = new Utilizator(firstName, lastName);

        Optional<Utilizator> rez = utilizatorService.addUtilizator(utilizator);
        if (rez.isPresent()) {
            System.out.println("Am adaugat: " + rez.get().toString());
        } else {
            System.out.println("Nu se poate efectua operatia!");
        }
    }

    private void uiDeleteUser() {
        int id;
        System.out.println("Introduceti id-ul: ");
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        Utilizator utilizator = new Utilizator("", "");
        utilizator.setId((long) id);

        Optional<Utilizator> rez = utilizatorService.deleteUtilizator(utilizator);
        if (rez.isPresent()) {
            System.out.println("Am sters: " + rez.get().toString());
        } else {
            System.out.println("Nu se poate efectua operatia!");
        }
    }

    private void uiAfisareUseri() {
        for (Utilizator utilizator : utilizatorService.getAll()) {
            prieteniiService.incarcaPrieteniiLaUser(utilizator);
        }
        utilizatorService.getAll().forEach(System.out::println);
    }

    private void uiAddFriendship() {
        System.out.println("Introduceti id-ul primului utilizator: ");
        int id1, id2;

        try {
            id1 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti id-ul celui de-al doilea utilizator: ");

        try {
            id2 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        Prietenie prt = new Prietenie(id1, id2);
        Optional<Prietenie> rez = prietenieService.addPrietenie(prt);
        if (rez.isPresent()) {
            System.out.println("Am adaugat: " + rez.get().toString());
        } else {
            System.out.println("Nu se poate efectua operatia!");
        }
    }

    private void uiDeleteFriendship() {

        System.out.println("Introduceti id-ul primului utilizator: ");
        int id1, id2;

        try {
            id1 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti id-ul celui de-al doilea utilizator: ");

        try {
            id2 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        Prietenie prt = new Prietenie(id1, id2);
        Optional<Prietenie> rez = prietenieService.deletePrietenie(prt);
        if (rez.isPresent()) {
            System.out.println("Am sters: " + rez.get().toString());
        } else {
            System.out.println("Nu se poate efectua operatia!");
        }

    }

    private void uiAfisarePrietenii(){
        prietenieService.getAll().forEach(System.out::println);
    }

    private void numarComunitati() {
        int nr = prieteniiService.numarComunitati();
        System.out.println("Numarul de comunitati este: " + nr);
    }

    private void comunitateSociabila() {
        System.out.println("Cea mai sociabila comunitate este: " + prieteniiService.comunitateSociabila());
    }


    public void run() {
        while (true) {
            try {
                System.out.println("Opreste: quit");
                System.out.println("Comenzi user: add, delete, afisare");
                System.out.println("Comenzi prietenie: add_f, delete_f, afisare_f");
                System.out.println("Comenzi statistica: nr_com, com_soc");
                System.out.println("Introduceti o comanda: ");

                String cmd;
                Scanner scanner = new Scanner(System.in);
                cmd = scanner.nextLine();

                switch (cmd) {
                    case "add":
                        uiAddUser();
                        break;
                    case "delete":
                        uiDeleteUser();
                        break;
                    case "add_f":
                        uiAddFriendship();
                        break;
                    case "delete_f":
                        uiDeleteFriendship();
                        break;
                    case "nr_com":
                        numarComunitati();
                        break;
                    case "com_soc":
                        comunitateSociabila();
                        break;
                    case "afisare":
                        uiAfisareUseri();
                        break;
                    case "afisare_f":
                        uiAfisarePrietenii();
                        break;
                    case "quit":
                        scanner.close();
                        return;
                    default:
                        System.out.println("Nu exista aceasta comanda!");
                        break;
                }
            } catch (UiException exception) {
                System.out.println("UiException: " + exception.getMessage());
            } catch (RepoException exception) {
                System.out.println("RepoException: " + exception.getMessage());
            } catch (ValidationException exception) {
                System.out.println("ValidationException: " + exception.getMessage());
            }
        }
    }
}
