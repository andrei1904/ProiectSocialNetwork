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
        return (message.getTo().equals(utilizator1) && message.getFrom().equals(utilizator2)) ||
                (message.getTo().equals(utilizator2) && message.getFrom().equals(utilizator1));
    }

    public void sendMessage(int id1, int id2, String text) {
        Utilizator utilizator1 = utilizatorService.getOne(id1);
        Utilizator utilizator2 = utilizatorService.getOne(id2);

        for (Message message : repo.findAll()) {
            if (suntEgali(message, utilizator1, utilizator2)) {
                throw new RepoException("Exista deja o conversatie intre acesti useri, " +
                        "folositi replay!\n");
            }
        }


        Message message = new Message(utilizator1, utilizator2, text, LocalDateTime.now());
        message.setId(genereazaId());

        repo.save(message);
    }

    public void replyMessage(int idUtilizator, int idMesaj, String text) {
        Utilizator utilizator = utilizatorService.getOne(idUtilizator);
        Optional<Message> mesajInitial = repo.findOne(idMesaj);

        if (mesajInitial.isPresent()) {
            Utilizator utilizator2 = mesajInitial.get().getFrom();

            Message message = new Message(utilizator, utilizator2, text, LocalDateTime.now());
            message.setId(genereazaId());
            mesajInitial.get().setReply(message.getId());

            repo.update(mesajInitial.get());
            repo.save(message);
        } else {
            throw new RepoException("Nu exista mesajul la care doriti sa raspundeti!\n");
        }
    }

    public List<Message> showMessages(int id1, int id2) {
        Utilizator utilizator1 = utilizatorService.getOne(id1);
        Utilizator utilizator2 = utilizatorService.getOne(id2);

        Message mesajInitial = null;
        boolean estePrimul = false;

        List<Message> m = repo.findAll().stream()
                .sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());

        for (Message message : m) {
            if (suntEgali(message, utilizator1, utilizator2)) {
                mesajInitial = message;
                break;
            }
        }

        if (mesajInitial == null) {
            return null;
        }

        List<Message> mesaje = new ArrayList<>();
        mesaje.add(mesajInitial);

        boolean sfarsit = false;
        while (!sfarsit) {
            for (Message message : repo.findAll()) {
                if (mesajInitial.getReply() == message.getId()) {
                    mesaje.add(message);
                    mesajInitial = message;
                    break;
                }
            }

            if (mesajInitial.getReply() == -1)
                sfarsit = true;
        }

        return mesaje;
    }
}
