package socialnetwork.service;

import socialnetwork.domain.Entity;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.util.Optional;
import java.util.Random;

public class UtilizatorService {
    private final Repository<Long, Utilizator> repo;

    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public Optional<Utilizator> addUtilizator(Utilizator utilizator) {
        Random random = new Random();
        do {
            utilizator.setId((long)(random.nextInt(Integer.MAX_VALUE)));
        } while(repo.findOne(utilizator.getId()).isPresent());

        return repo.save(utilizator);
    }

    public Optional<Utilizator> deleteUtilizator(Utilizator utilizator) {
        return repo.delete(utilizator.getId());
    }

    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    ///TO DO: add other methods
}
