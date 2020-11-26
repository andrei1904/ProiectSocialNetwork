package socialnetwork.repository.file;

import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MessageFile extends AbstractFileRepository<Integer, Message> {

    public MessageFile(String fileName, Validator<Message> validator) {
        super(fileName, validator);
    }


    @Override
    public Message extractEntity(List<String> attributes) {
        Utilizator utilizator = new Utilizator(attributes.get(2), attributes.get(3));
        utilizator.setId(Long.parseLong(attributes.get(1)));

        int nrUtilizatori = Integer.parseInt(attributes.get(4));

        List<Utilizator> to = new ArrayList<>();
        int poz = 5;
        for (int i = 0; i < nrUtilizatori; i++, poz += 3) {
            Utilizator u = new Utilizator(attributes.get(poz + 1),
                                            attributes.get(poz + 2));
            u.setId(Long.parseLong(attributes.get(poz)));
            to.add(u);
        }

        Message message = new Message(utilizator, to, attributes.get(poz),
                LocalDateTime.parse(attributes.get(poz + 1)));
        message.setId(Integer.parseInt(attributes.get(0)));

        if (attributes.get(poz + 2).equals("-1")) {
            message.setReply(-1);
        } else {
            message.setReply(Integer.parseInt(attributes.get(poz + 2)));
        }

        return message;
    }

    private String writeUtilizator(Utilizator utilizator) {
        return utilizator.getId() + ";" + utilizator.getFirstName() + ";" +
                utilizator.getLastName();
    }

    private String writeListUtilizator(List<Utilizator> list) {
        String rez = "";
        for (Utilizator utilizator : list) {
            rez += writeUtilizator(utilizator);
            rez += ";";
        }
        return rez;
    }

    @Override
    protected String createEntityAsString(Message entity) {
        Utilizator utilizator = entity.getFrom();
        List<Utilizator> utilizatori = entity.getTo();
        int size = utilizatori.size();

        if (entity.getReply() == -1)
            return entity.getId() + ";" +
                    writeUtilizator(utilizator) + ";" + size + ";" +
                    writeListUtilizator(utilizatori) +
                    entity.getMessage() + ";" + entity.getDate() + ";" + "-1";
        return entity.getId() + ";" +
                writeUtilizator(utilizator) + ";" + size + ";" +
                writeListUtilizator(utilizatori) +
                entity.getMessage() + ";" + entity.getDate() + ";" +
                entity.getReply();
    }
}
