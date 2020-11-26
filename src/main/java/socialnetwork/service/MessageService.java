package socialnetwork.service;

import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;
import socialnetwork.ui.UiException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MessageService {
    private final PrietenieService prietenieService;
    private final UtilizatorService utilizatorService;
    private final Repository<Integer, Message> repo;

    public MessageService(PrietenieService prietenieService, UtilizatorService utilizatorService, Repository<Integer, Message> repo) {
        this.prietenieService = prietenieService;
        this.utilizatorService = utilizatorService;
        this.repo = repo;
    }

    private int genereazaId() {
        Random random = new Random();
        int idMessage = random.nextInt(Integer.MAX_VALUE);
        while (repo.findOne(idMessage).isPresent()) {
            idMessage = random.nextInt(Integer.MAX_VALUE);
        }
        return idMessage;
    }

    private boolean suntEgali(Message message, Utilizator utilizator1, Utilizator utilizator2) {
        for (Utilizator utilizator : message.getTo()) {
            if ((utilizator.equals(utilizator1) && message.getFrom().equals(utilizator2)) ||
                    (utilizator.equals(utilizator2) && message.getFrom().equals(utilizator1))) {
                return true;
            }
        }
        return false;
    }

    public void sendMessage(int id1, List<Integer> to, String text) {
        Utilizator utilizator1 = utilizatorService.getOne(id1);

        List<Utilizator> utilizatori = new ArrayList<>();
        for (Integer id : to) {
            Utilizator utilizator = utilizatorService.getOne(id);
            utilizatori.add(utilizator);
        }

        // daca isi da mesaj singur
        utilizatori.removeIf(utilizator -> utilizator.getId().equals(utilizator1.getId()));

        // daca exista deja o conversatie intre utilizatori
        for (Message message : repo.findAll()) {
            utilizatori.removeIf(utilizator -> suntEgali(message, utilizator1, utilizator));
        }

        Message message = new Message(utilizator1, utilizatori, text, LocalDateTime.now());
        message.setId(genereazaId());

        repo.save(message);
    }

    public void replyMessage(int idUtilizator, int idMesaj, String text) {
        Utilizator utilizator = utilizatorService.getOne(idUtilizator);
        Optional<Message> mesajInitial = repo.findOne(idMesaj);

        if (mesajInitial.isPresent()) {
            Utilizator utilizator2 = mesajInitial.get().getFrom();

            Message message;
            if (idUtilizator == utilizator2.getId()) {
                message = new Message(utilizator, mesajInitial.get().getTo(), text, LocalDateTime.now());
            } else {
                List<Utilizator> utilizatori = new ArrayList<>();
                utilizatori.add(utilizator2);
                message = new Message(utilizator, utilizatori, text, LocalDateTime.now());
            }

            message.setId(genereazaId());
            message.setReply(mesajInitial.get().getId());

//            repo.update(mesajInitial.get());
            repo.save(message);
        } else {
            throw new RepoException("Nu exista mesajul la care doriti sa raspundeti!\n");
        }
    }

    public List<Message> showMessages(int id1, int id2) {
        Utilizator utilizator1 = utilizatorService.getOne(id1);
        Utilizator utilizator2 = utilizatorService.getOne(id2);

        Message ultimulMesaj = null;
        boolean estePrimul = false;

        // sorteaza mesajele descrescator dupa data
        List<Message> m = repo.findAll().stream()
                .sorted(Comparator.comparing(Message::getDate).reversed())
                .collect(Collectors.toList());

        // gaseste ultimul mesaj
        for (Message message : m) {
            if (suntEgali(message, utilizator1, utilizator2)) {
                ultimulMesaj = message;
                break;
            }
        }

        // verifica daca mesajul exista
        if (ultimulMesaj == null) {
            throw new RepoException("Nu exista aceasta conversatie!\n");
        }

        List<Message> mesaje = new ArrayList<>();
        mesaje.add(ultimulMesaj);

        boolean sfarsit = false;
        while (!sfarsit) {
            for (Message message : m) {
                if (ultimulMesaj.getReply() == message.getId()) {
                    mesaje.add(message);
                    ultimulMesaj = message;
                    break;
                }
            }

            if (ultimulMesaj.getReply() == -1)
                sfarsit = true;
        }

        // le sortez in ordinea corecta
        Collections.reverse(mesaje);
        return mesaje;
    }

    public void deleteMesaje(int id) {
        for (Message mesaj : repo.findAll()) {
            for (Utilizator utilizator : mesaj.getTo()) {
                if (utilizator.getId() == id || mesaj.getFrom().getId() == id) {
                    repo.delete(mesaj.getId());
                }
            }
        }
    }
}
