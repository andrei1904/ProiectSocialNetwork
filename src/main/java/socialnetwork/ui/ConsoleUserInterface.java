package socialnetwork.ui;


import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.RepoException;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.PrieteniiService;
import socialnetwork.service.UtilizatorService;

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
        if (!scanner.hasNextLine()) {
            throw new UiException("Introduceti datele!\n");
        }
        String firstName = scanner.nextLine();

        System.out.println("Introduceti numele: ");
        if (!scanner.hasNextLine()) {
            throw new UiException("Introduceti datele!\n");
        }
        String lastName = scanner.nextLine();

        Utilizator utilizator = new Utilizator(firstName, lastName);

        System.out.println("Am adaugat: " + utilizatorService.addUtilizator(utilizator));
    }

    private void uiDeleteUser() {
        System.out.println("Introduceti id-ul: ");
        if (!scanner.hasNextInt()) {
            throw new UiException("Introduceti un numar intreg!\n");
        }
        int id = scanner.nextInt();

        Utilizator utilizator = new Utilizator("", "");
        utilizator.setId((long) id);

        System.out.println("A fost sters: " + utilizatorService.deleteUtilizator(utilizator));
    }

    private void uiAddFriendship() {
        System.out.println("Introduceti id-ul primului utilizator: ");
        if (!scanner.hasNextInt()) {
            throw new UiException("Introduceti un numar intreg!\n");
        }
        int id1 = scanner.nextInt();


        System.out.println("Introduceti id-ul celui de-al doilea utilizator: ");
        if (!scanner.hasNextInt()) {
            throw new UiException("Introduceti un numar intreg!\n");
        }
        int id2 = scanner.nextInt();

        Prietenie prt = new Prietenie(id1, id2);
        prietenieService.addPrietenie(prt);

    }

    private void uiDeleteFriendship() {

        System.out.println("Introduceti id-ul primului utilizator: ");
        if (!scanner.hasNextInt()) {
            throw new UiException("Introduceti un numar intreg!\n");
        }
        int id1 = scanner.nextInt();

        System.out.println("Introduceti id-ul celui de-al doilea utilizator: ");
        if (!scanner.hasNextInt()) {
            throw new UiException("Introduceti un numar intreg!\n");
        }
        int id2 = scanner.nextInt();

        Prietenie prt = new Prietenie(id1, id2);
        prietenieService.deletePrietenie(prt);

    }

    private void numarComunitati() {
        int nr = prieteniiService.numarComunitati();
        System.out.println("Numarul de comunitati este: " + nr);
    }

    private void comunitateSociabila() {
        System.out.println("Cea mai sociabila comunitate este: " + prieteniiService.comunitateSociabila());
    }



    public void run() {
        String cmd = "";

        while (true) {
            try {
                System.out.println("Opreste: quit");
                System.out.println("Comenzi user: add, delete");
                System.out.println("Comenzi prietenie: add_f, delete_f");
                System.out.println("Comenzi statistica: nr_com, com_soc");
                System.out.println("Introduceti o comanda: ");


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
