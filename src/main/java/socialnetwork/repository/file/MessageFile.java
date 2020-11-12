package socialnetwork.repository.file;

import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageFile extends AbstractFileRepository<Integer, Message> {

    public MessageFile(String fileName, Validator<Message> validator) {
        super(fileName, validator);
    }


    @Override
    public Message extractEntity(List<String> attributes) {
        Utilizator utilizator = new Utilizator(attributes.get(2), attributes.get(3));
        utilizator.setId(Long.parseLong(attributes.get(1)));

        Utilizator utilizator2 = new Utilizator(attributes.get(5), attributes.get(6));
        utilizator2.setId(Long.parseLong(attributes.get(4)));

        Message message = new Message(utilizator, utilizator2, attributes.get(7),
                LocalDateTime.parse(attributes.get(8)));
        message.setId(Integer.parseInt(attributes.get(0)));

        if (attributes.get(9).equals("null")) {
            message.setReply(-1);
        } else {
            message.setReply(Integer.parseInt(attributes.get(9)));
        }

        return message;
    }

    private String writeUtilizator(Utilizator utilizator) {
        return utilizator.getId() + ";" + utilizator.getFirstName() + ";" +
                utilizator.getLastName();
    }

    @Override
    protected String createEntityAsString(Message entity) {
        Utilizator utilizator = entity.getFrom();
        Utilizator utilizator2 = entity.getTo();

        if (entity.getReply() == -1)
            return entity.getId() + ";" +
                    writeUtilizator(utilizator) + ";" +
                    writeUtilizator(utilizator2) + ";" +
                    entity.getMessage() + ";" + entity.getDate() + ";" + "-1";
        return entity.getId() + ";" +
                writeUtilizator(utilizator) + ";" +
                writeUtilizator(utilizator2) + ";" +
                entity.getMessage() + ";" + entity.getDate() + ";" +
                entity.getReply();
    }
}
