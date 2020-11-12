package socialnetwork.repository.memory;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private final Validator<E> validator;
    protected Map<ID, E> entities;


    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<ID, E>();
    }


    @Override
    public Optional<E> findOne(ID id) {
        if (id == null) {
            throw new RepoException("Id must be not null");
        }
        return Optional.ofNullable(entities.get(id));
    }


    @Override
    public List<E> findAll() {
        return entities.
                entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }


    @Override
    public Optional<E> save(E entity) {
        if (entity == null)
            throw new RepoException("entity must be not null");

        validator.validate(entity);

        if (entities.get(entity.getId()) != null) { // daca exista
            return Optional.empty();
        } else { // daca nu exista
            entities.put(entity.getId(), entity);
        }
        return Optional.of(entity);
    }


    @Override
    public Optional<E> delete(ID id) {
        if (id == null)
            throw new RepoException("id must be not null");

        return Optional.ofNullable(entities.remove(id));
    }


    @Override
    public Optional<E> update(E entity) {

        if (entity == null)
            throw new RepoException("entity must be not null!");
        validator.validate(entity);

//        entities.put(entity.getId(), entity);

        if (entities.get(entity.getId()) != null) { // daca exista
            entities.put(entity.getId(), entity);
            return Optional.empty();
        }
        return Optional.of(entity); // nu exista
    }


    @Override
    public int size() {
        return entities.size();
    }

}
