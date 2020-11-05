package socialnetwork.service;

import socialnetwork.domain.GrafPrietenii;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.util.HashSet;

public class PrieteniiService {
    private final Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private final Repository<Long, Utilizator> repoUtilizator;

    public PrieteniiService(Repository<Tuple<Long, Long>, Prietenie> repoPrietenie, Repository<Long, Utilizator> repoUtilizator) {
        this.repoPrietenie = repoPrietenie;
        this.repoUtilizator = repoUtilizator;
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
