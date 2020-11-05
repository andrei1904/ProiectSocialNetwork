package socialnetwork.service;

import socialnetwork.domain.GrafPrietenii;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.util.*;

public class PrieteniiService {
    private final Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private final Repository<Long, Utilizator> repoUtilizator;

    public PrieteniiService(Repository<Tuple<Long, Long>, Prietenie> repoPrietenie, Repository<Long, Utilizator> repoUtilizator) {
        this.repoPrietenie = repoPrietenie;
        this.repoUtilizator = repoUtilizator;
    }

    private List<Prietenie> getPrieteniiUtilizator(Utilizator utilizator) {
        List<Prietenie> rez = new ArrayList<Prietenie>() {};
        for (Prietenie p : repoPrietenie.findAll()) {
            if (p.getIdPrieten1() == utilizator.getId()) {
                rez.add(p);
            }
        }
        return rez;
    }

    public void incarcaPrieteniiLaUser(Utilizator utilizator) {
        List<Utilizator> rez = new ArrayList<Utilizator>() {};

        for (Prietenie p : getPrieteniiUtilizator(utilizator)) {
            Optional<Utilizator> user = repoUtilizator.findOne((long)p.getIdPrieten2());
            user.ifPresent(rez::add);
        }
        utilizator.setFriends(rez);
    }

    public int numarComunitati() {
        GrafPrietenii graf = new GrafPrietenii(repoUtilizator.size(), repoPrietenie.size());
        graf.populateGraf(repoPrietenie.findAll(), repoUtilizator.findAll());

        return graf.componenteConexe();
    }

    public HashSet<Integer> comunitateSociabila() {
        GrafPrietenii graf = new GrafPrietenii(repoUtilizator.size(), repoPrietenie.size());
        graf.populateGraf(repoPrietenie.findAll(), repoUtilizator.findAll());

        return graf.ceaMaiLungaComunitate();
    }
}
