package socialnetwork.repository.file;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class CererePrietenieFile extends AbstractFileRepository<Integer, CererePrietenie> {
    public CererePrietenieFile(String fileName, Validator<CererePrietenie> validator) {
        super(fileName, validator);
    }

    @Override
    public CererePrietenie extractEntity(List<String> attributes) {
        CererePrietenie cp =  new CererePrietenie(Integer.parseInt(attributes.get(1)),
                Integer.parseInt(attributes.get(2)), LocalDateTime.parse(attributes.get(4)));
        cp.setId(Integer.parseInt(attributes.get(0)));
        cp.setStatus(attributes.get(3));
        return cp;
    }

    @Override
    protected String createEntityAsString(CererePrietenie entity) {
        return entity.getId() + ";" + entity.getId1() + ";" + entity.getId2() + ";" +
                entity.getStatus() + ";" + entity.getDate();
    }
}
