package socialnetwork.domain.validators;

import socialnetwork.domain.Message;

public class MessageValidator implements Validator<Message> {

    @Override
    public void validate(Message entity) throws ValidationException {
        if (entity.getFrom() == null || entity.getTo() == null) {
            throw new ValidationException("Mesaj invalid!\n");
        }

        if (entity.getId() == null) {
            throw new ValidationException("Id-ul nu poate sa fie vid!\n");
        }

        if (entity.getMessage().equals("")) {
            throw new ValidationException("Mesajul nu poate sa fie vid!\n");
        }

        if (entity.getDate() == null) {
            throw new ValidationException("Data nu poate sa fie vida!\n");
        }

        if (entity.getTo().isEmpty()) {
            throw new ValidationException("Mesaj invalid!\n");
        }
    }
}
