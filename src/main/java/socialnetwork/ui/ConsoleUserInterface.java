package socialnetwork.ui;


import socialnetwork.domain.*;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.RepoException;
import socialnetwork.service.*;

import java.util.*;

public class ConsoleUserInterface {
    UtilizatorService utilizatorService;
    PrietenieService prietenieService;
    PrieteniiService prieteniiService;
    MessageService messageService;
    CererePrietenieService cererePrietenieService;
    Map<String, Runnable> commands = new HashMap<>();


    Scanner scanner = new Scanner(System.in);


    public ConsoleUserInterface(UtilizatorService utilizatorService, PrietenieService prietenieService, PrieteniiService prieteniiService, MessageService messageService, CererePrietenieService cererePrietenieService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.prieteniiService = prieteniiService;
        this.messageService = messageService;
        this.cererePrietenieService = cererePrietenieService;
    }

    private void addCommands() {
        commands.put("add", this::uiAddUser);
        commands.put("delete", this::uiDeleteUser);
        commands.put("add_f", this::uiAddFriendship);
        commands.put("delete_f", this::uiDeleteFriendship);
        commands.put("nr_com", this::uiNumarComunitati);
        commands.put("com_soc", this::uiComunitateSociabila);
        commands.put("prieteni_user", this::uiPrieteniUtilizator);
        commands.put("prieteni_data", this::uiPrieteniiUtilizatorDupaLuna);
        commands.put("send", this::uiSendMessage);
        commands.put("reply", this::uiReplyMessage);
        commands.put("show_msg", this::uiShowMessages);
        commands.put("send_f", this::uiSendFriendRequest);
        commands.put("confirm_f", this::uiConfirmFriendRequest);

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
        prietenieService.deletePrietenie(id);
        cererePrietenieService.deleteCereri(id);
        messageService.deleteMesaje(id);

    }

//    private void uiAfisareUseri() {
////        for (Utilizator utilizator : utilizatorService.getAll()) {
////            prieteniiService.incarcaPrieteniiLaUser(utilizator);
////        }
//        utilizatorService.getAll().forEach(System.out::println);
//    }

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

        Optional<Prietenie> rez = prieteniiService.addPrietenie(id1, id2);
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

        Optional<Prietenie> rez = prieteniiService.deletePrietenie(id1, id2);
        if (rez.isPresent()) {
            System.out.println("Am sters: " + rez.get().toString());
        } else {
            System.out.println("Nu se poate efectua operatia!");
        }

    }

//    private void uiAfisarePrietenii() {
//        prietenieService.getAll().forEach(System.out::println);
//    }

    private void uiNumarComunitati() {
        int nr = prieteniiService.numarComunitati();
        System.out.println("Numarul de comunitati este: " + nr);
    }

    private void uiComunitateSociabila() {
        System.out.println("Cea mai sociabila comunitate este: " + prieteniiService.comunitateSociabila());
    }

    private void uiPrieteniUtilizator() {
        System.out.println("Introduceti id-ul utilizatorului: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        List<DtoPrieten> lista = prieteniiService.prieteniUtilizator(id);
        if (lista.isEmpty()) {
            System.out.println("Utilizatorul nu are prieteni!\n");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void uiPrieteniiUtilizatorDupaLuna() {
        System.out.println("Introduceti id-ul utilizatorului: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti luna dupa care filtrati: ");

        int luna;
        try {
            luna = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        List<DtoPrieten> lista = prieteniiService.prieteniiUtilizatorDupaLuna(id, luna);
        if (lista.isEmpty()) {
            System.out.println("Nu exista prietenii care sa indeplineasca aceste criterii!\n");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void uiSendMessage() {
        System.out.println("Introduceti id-ul utilizatorului care trimite mesajul: ");
        int id1;

        try {
            id1 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti numarul de utilizatori care primesc masajul: ");
        int nr;

        try {
            nr = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }


        List<Integer> iduri = new ArrayList<>();
        for (int i = 0; i < nr; i++) {
            System.out.println("Introduceti id-ul utilizatorului care primeste mesajul: ");
            int id;

            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                throw new UiException("Introduceti un numar intreg!\n");
            }
            iduri.add(id);
        }

        System.out.println("Introduceti mesajul: ");
        String text = scanner.nextLine();

        messageService.sendMessage(id1, iduri, text);
    }

    private void uiReplyMessage() {
        System.out.println("Introduceti id-ul utilizatorului care raspunde: ");
        int idUtilizator;

        try {
            idUtilizator = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti id-ul mesajului la care raspundeti: ");
        int idMesaj;

        try {
            idMesaj = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti mesajul: ");
        String text = scanner.nextLine();

        messageService.replyMessage(idUtilizator, idMesaj, text);
    }

    private void uiSendFriendRequest() {
        System.out.println("Introduceti id-ul care trimite cererea: ");
        int id1;

        try {
            id1 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti id-ul utilizatorului la care se trimite cererea: ");
        int id2;

        try {
            id2 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        cererePrietenieService.sendFriendRequest(id1, id2);
    }

    private void uiConfirmFriendRequest() {
        System.out.println("Introduceti id-ul cererii: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti 1 daca acceptati sau 0 daca refuzati cererea: ");
        int raspuns;

        try {
            raspuns = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        cererePrietenieService.confirmFriendRequest(id, raspuns);
    }

    private void uiShowMessages() {
        System.out.println("Introduceti id-ul primului utilizator: ");
        int id1;

        try {
            id1 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        System.out.println("Introduceti id-ul celui de-al doilea utilizator: ");
        int id2;

        try {
            id2 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new UiException("Introduceti un numar intreg!\n");
        }

        messageService.showMessages(id1, id2).forEach(System.out::println);
    }

    public void run() {
        addCommands();

        while (true) {
            try {
                System.out.println("Opreste: quit");
                System.out.println("Comenzi user: add, delete");
                System.out.println("Comenzi prietenie: send_f, confirm_f, add_f, delete_f");
                System.out.println("Comenzi statistica: nr_com, com_soc");
                System.out.println("Comenzi afisare: prieteni_user, prieteni_data, show_msg");
                System.out.println("Comenzi mesaj: send, reply");
                System.out.println("Introduceti o comanda: ");

                String cmd;
                cmd = scanner.nextLine();

                if (cmd.equals("quit")) {
                    scanner.close();
                    return;
                }
                if (commands.containsKey(cmd)) {
                    commands.get(cmd).run();
                } else {
                    System.out.println("Nu exista aceasta comanda!\n");
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
