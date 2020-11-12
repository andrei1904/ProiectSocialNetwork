package socialnetwork.service;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.util.Random;

public class CererePrietenieService {
    private final PrieteniiService prieteniiService;
    private final Repository<Integer, CererePrietenie> repo;

    public CererePrietenieService(PrieteniiService prieteniiService, Repository<Integer, CererePrietenie> repo) {
        this.prieteniiService = prieteniiService;
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
}
