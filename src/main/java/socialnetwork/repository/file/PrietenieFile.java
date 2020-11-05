package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrietenieFile extends AbstractFileRepository<Tuple<Long, Long>, Prietenie> {

    public PrietenieFile(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(attributes.get(2), formatter);

        Prietenie prietenie = new Prietenie(Integer.parseInt(attributes.get(0)),
                Integer.parseInt(attributes.get(1)));
        prietenie.setId(new Tuple<>((long) prietenie.getIdPrieten1(),
                (long) prietenie.getIdPrieten2()));
        prietenie.setDate(dateTime);

//        validator.validate(prietenie);
        return prietenie;
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return entity.getId().getLeft() + ";" + entity.getId().getRight() + ";" +
                entity.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
