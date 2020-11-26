package socialnetwork.service;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.util.Random;

public class CererePrietenieService {
    private final PrieteniiService prieteniiService;
    private final UtilizatorService utilizatorService;
    private final Repository<Integer, CererePrietenie> repo;

    public CererePrietenieService(PrieteniiService prieteniiService, UtilizatorService utilizatorService, Repository<Integer, CererePrietenie> repo) {
        this.prieteniiService = prieteniiService;
        this.utilizatorService = utilizatorService;
        this.repo = repo;
    }

    private int genereazaId() {
        Random random = new Random();
        int idMessage = random.nextInt(Integer.MAX_VALUE);
        while (repo.findOne(idMessage).isPresent()) {
            idMessage = random.nextInt(Integer.MAX_VALUE);
        }
        return idMessage;
    }

    public void sendFriendRequest(int id1, int id2) {
        CererePrietenie cererePrietenie = new CererePrietenie(id1, id2);
        cererePrietenie.setStatus("pending");
        cererePrietenie.setId(genereazaId());

        if (prieteniiService.existaPrietenie(id1, id2)) {
            throw new RepoException("Exista deja aceasta prietenie!\n");
        }

        if (!utilizatorService.existaUtilizator(id1) ||
                !utilizatorService.existaUtilizator(id2)) {
            throw new RepoException("Nu exista unul dintre utilizatori!\n");
        }


        for (CererePrietenie cp : repo.findAll()) {
            if ((cp.getId1() == id1 && cp.getId2() == id2) ||
            (cp.getId1() == id2 && cp.getId2() == id1)) {
                throw new RepoException("Exista deja o cerere de prietenie trimisa!\n");
            }
        }

        repo.save(cererePrietenie);
    }

    public void confirmFriendRequest(int id, int raspuns) {
        for (CererePrietenie cererePrietenie : repo.findAll()) {
            if (cererePrietenie.getId() == id &&
                cererePrietenie.getStatus().equals("pending")) {
                if (raspuns == 1) {
                    prieteniiService.addPrietenie(cererePrietenie.getId1(),
                            cererePrietenie.getId2());
                    cererePrietenie.setStatus("approved");
                } else {
                    cererePrietenie.setStatus("rejected");
                }
                repo.update(cererePrietenie);
                break;
            }
        }
    }

    public void deleteCereri(int id) {
        for (CererePrietenie cp : repo.findAll()) {
            if (cp.getId2() == id || cp.getId1() == id) {
                repo.delete(cp.getId());
            }
        }
    }
}
