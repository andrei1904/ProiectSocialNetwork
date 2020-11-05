package socialnetwork.domain.validators;

import socialnetwork.domain.Prietenie;

public class PrietenieValidator implements Validator<Prietenie>{
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if (entity.getIdPrieten1() < 0 || entity.getIdPrieten2() < 0) {
            throw new ValidationException("Id-ul nu poate sa fie negativ!\n");
        }

        if (entity.getId() == null) {
            throw new ValidationException("Id-ul nu poate sa fie vid!\n");
        }

        if (entity.getDate() == null) {
            throw new ValidationException("Data nu poate sa fie vida!\n");
        }
    }
}
