package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

public class UtilizatorService {
    private Repository<Long, Utilizator> repo;

    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public Utilizator addUtilizator(Utilizator messageTask) {
        return repo.save(messageTask);
    }

    public Utilizator deleteUtilizator(Utilizator user) {
        return repo.delete(user.getId());
    }

    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    ///TO DO: add other methods
}
