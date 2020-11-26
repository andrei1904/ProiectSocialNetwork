package socialnetwork.service;

import socialnetwork.domain.DtoPrieten;
import socialnetwork.domain.GrafPrietenii;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepoException;

import java.util.*;
import java.util.stream.Collectors;


public class PrieteniiService {
    private final PrietenieService prietenieService;
    private final UtilizatorService utilizatorService;

    public PrieteniiService(PrietenieService prietenieService, UtilizatorService utilizatorService) {
        this.prietenieService = prietenieService;
        this.utilizatorService = utilizatorService;
    }

    public boolean existaPrietenie(int id1, int id2) {
        return prietenieService.existaPrietenie(id1, id2);
    }

    public Optional<Prietenie> addPrietenie(int id1, int id2) {
        if (!utilizatorService.existaUtilizator(id1)) {
            throw new RepoException("Nu exista primul utilizator!\n");
        }
        if (!utilizatorService.existaUtilizator(id2)) {
            throw new RepoException("Nu exista al doilea utilizator!\n");
        }
        if (prietenieService.existaPrietenie(id1, id2)) {
            throw new RepoException("Exista deja aceasta prietenie!\n");
        }

        Prietenie prietenie = new Prietenie(id1, id2);
        return prietenieService.addPrietenie(prietenie);
    }

    public Optional<Prietenie> deletePrietenie(int id1, int id2) {
        if (!prietenieService.existaPrietenie(id1, id2)) {
            throw new RepoException("Nu exista aceasta prietenie!\n");
        }

        Prietenie prietenie = new Prietenie(id1, id2);
        return prietenieService.deletePrietenie(prietenie);
    }

    private List<Prietenie> getPrieteniiUtilizator(Utilizator utilizator) {
        List<Prietenie> rez = new ArrayList<Prietenie>() {
        };
        for (Prietenie p : prietenieService.getAll()) {
            if (p.getIdPrieten1() == utilizator.getId()) {
                rez.add(p);
            }
        }
        return rez;
    }

    public void incarcaPrieteniiLaUser(Utilizator utilizator) {
        List<Utilizator> rez = new ArrayList<Utilizator>() {
        };

        for (Prietenie p : getPrieteniiUtilizator(utilizator)) {
            Utilizator user = utilizatorService.getOne(p.getIdPrieten2());
            rez.add(user);
        }

        utilizator.setFriends(rez);
    }

    public int numarComunitati() {
        GrafPrietenii graf = new GrafPrietenii(utilizatorService.size(), prietenieService.size());
        graf.populateGraf(prietenieService.getAll(), utilizatorService.getAll());

        return graf.componenteConexe();
    }

    public HashSet<Integer> comunitateSociabila() {
        GrafPrietenii graf = new GrafPrietenii(utilizatorService.size(), prietenieService.size());
        graf.populateGraf(prietenieService.getAll(), utilizatorService.getAll());

        return graf.ceaMaiLungaComunitate();
    }

    public List<DtoPrieten> prieteniUtilizator(int id) {
        List<Prietenie> prietenii = prietenieService.getAll();

        return prietenii.stream()
                .filter(x -> x.getIdPrieten1() == id || x.getIdPrieten2() == id)
                .map(x -> {

                    if (x.getIdPrieten1() == id)
                        return new DtoPrieten(
                                utilizatorService.getOne(x.getIdPrieten2()).getFirstName(),
                                utilizatorService.getOne(x.getIdPrieten2()).getLastName(),
                                x.getDate());
                    else
                        return new DtoPrieten(
                                utilizatorService.getOne(x.getIdPrieten1()).getFirstName(),
                                utilizatorService.getOne(x.getIdPrieten1()).getLastName(),
                                x.getDate());
                })
                .collect(Collectors.toList());
    }

    public List<DtoPrieten> prieteniiUtilizatorDupaLuna(int id, int luna) {
        List<Prietenie> prietenii = prietenieService.getAll();

        return prietenii.stream()
                .filter(x -> (x.getIdPrieten1() == id || x.getIdPrieten2() == id
                ) && x.getDate().getMonthValue() == luna)
                .map(x -> {

                    if (x.getIdPrieten1() == id)
                        return new DtoPrieten(
                                utilizatorService.getOne(x.getIdPrieten2()).getFirstName(),
                                utilizatorService.getOne(x.getIdPrieten2()).getLastName(),
                                x.getDate());
                    else
                        return new DtoPrieten(
                                utilizatorService.getOne(x.getIdPrieten1()).getFirstName(),
                                utilizatorService.getOne(x.getIdPrieten1()).getLastName(),
                                x.getDate());
                })
                .collect(Collectors.toList());
    }
}
