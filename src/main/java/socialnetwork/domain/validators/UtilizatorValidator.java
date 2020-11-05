package socialnetwork.domain.validators;

import socialnetwork.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        if (entity.getFirstName().equals("") || entity.getFirstName() == null) {
            throw new ValidationException("Prenumele nu poate sa fie vid!\n");
        }

        if (entity.getLastName().equals("") || entity.getLastName() == null) {
            throw new ValidationException("Numele nu poate sa fie vid!\n");
        }

        if (entity.getId() == null) {
            throw new ValidationException("Id-ul nu poate sa fie vid!\n");
        }
    }
}
