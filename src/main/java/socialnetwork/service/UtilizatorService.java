package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepoException;
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

    public Utilizator getOne(int id) {
        Optional<Utilizator> utilizator = repo.findOne((long)id);
        if (utilizator.isPresent()) {
            return utilizator.get();
        } else {
            throw new RepoException("Acest utilizator nu exista");
        }
    }

    public boolean existaUtilizator(int id) {
        return repo.findOne((long) id).isPresent();
    }

    public int size() {
        return repo.size();
    }

}
