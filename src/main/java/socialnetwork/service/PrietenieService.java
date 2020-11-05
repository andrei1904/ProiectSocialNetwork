package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

public class PrietenieService {
    private final Repository<Tuple<Long, Long>, Prietenie> repo;

    public PrietenieService(Repository<Tuple<Long, Long>, Prietenie> repo) {
        this.repo = repo;
    }

    public Optional<Prietenie> addPrietenie(Prietenie prietenie) {
        prietenie.setId(new Tuple<Long, Long>((long)prietenie.getIdPrieten1(),
                (long)prietenie.getIdPrieten2()));
        prietenie.setDate(LocalDateTime.now());
        return repo.save(prietenie);
    }

    public Optional<Prietenie> deletePrietenie(Prietenie prietenie) {
        prietenie.setId(new Tuple<Long, Long>((long)prietenie.getIdPrieten1(),
                (long)prietenie.getIdPrieten2()));
        return repo.delete(prietenie.getId());
    }

    public Iterable<Prietenie> getAll() {
        return repo.findAll();
    }

}
