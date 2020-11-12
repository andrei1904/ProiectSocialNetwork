package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public class PrietenieService {
    private final Repository<Tuple<Long, Long>, Prietenie> repo;

    public PrietenieService(Repository<Tuple<Long, Long>, Prietenie> repo) {
        this.repo = repo;
    }

    public Optional<Prietenie> addPrietenie(Prietenie prietenie) {

        // prietenie id1 id2
        prietenie.setId(new Tuple<>((long)prietenie.getIdPrieten1(),
                (long)prietenie.getIdPrieten2()));

        prietenie.setDate(LocalDateTime.now());
        return repo.save(prietenie);
    }

    public Optional<Prietenie> deletePrietenie(Prietenie prietenie) {
        prietenie.setId(new Tuple<>((long)prietenie.getIdPrieten1(),
                (long)prietenie.getIdPrieten2()));
        return repo.delete(prietenie.getId());
    }

    public void deletePrietenie(int id) {
        for (Prietenie p : repo.findAll()) {
            if (p.getIdPrieten1() == id || p.getIdPrieten2() == id) {
                repo.delete(p.getId());
            }
        }
    }

    public List<Prietenie> getAll() {
        return repo.findAll();
    }

    public int size() {
        return repo.size();
    }

    public boolean existaPrietenie(int id1, int id2) {
        for (Prietenie prietenie : getAll()) {
            if (prietenie.getIdPrieten1() == id1 && prietenie.getIdPrieten2() == id2 ||
                prietenie.getIdPrieten1() == id2 && prietenie.getIdPrieten2() == id1) {
                return true;
            }
        }
        return false;
    }


}
