package socialnetwork.domain.validators;

import socialnetwork.domain.CererePrietenie;

public class CerereValidator implements Validator<CererePrietenie> {
    @Override
    public void validate(CererePrietenie entity) throws ValidationException {
        if (entity.getId1() < 0 || entity.getId2() < 0) {
            throw new ValidationException("Id-ul nu poate sa fie negativ!\n");
        }

        if (entity.getId() == null) {
            throw new ValidationException("Id-ul nu poate sa fie vid!\n");
        }

        if (!entity.getStatus().equals("pending") &&
                !entity.getStatus().equals("approved") &&
                !entity.getStatus().equals("rejected")) {
            throw new ValidationException("Statusul nu are o valoare corespunzatoare!\n");
        }
    }
}
