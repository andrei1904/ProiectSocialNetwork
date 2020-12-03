package socialnetwork.service;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.DtoCererePrietenie;
import socialnetwork.repository.RepoException;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

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
        CererePrietenie cererePrietenie = new CererePrietenie(id1, id2, LocalDateTime.now());
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
            if (((cp.getId1() == id1 && cp.getId2() == id2) ||
                    (cp.getId1() == id2 && cp.getId2() == id1)) &&
                    !cp.getStatus().equals("rejected")) {
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

    public void deleteCerere(int id1, int id2) {
        for (CererePrietenie cp : repo.findAll()) {
            if ((cp.getId1() == id1 && cp.getId2() == id2) ||
                    (cp.getId2() == id1 && cp.getId1() == id2)) {
                repo.delete(cp.getId());
            }
        }
    }

    public List<CererePrietenie> getAll() {
        return repo.findAll();
    }

    public List<DtoCererePrietenie> getSentForUser(int id) {
        List<CererePrietenie> cereriPrietenie = getAll();

        return cereriPrietenie.stream()
                .filter(x -> x.getId1() == id || x.getId2() == id)
                .map(x -> {

                    if (x.getId1() == id)
                        return new DtoCererePrietenie(
                                x.getId(),
                                utilizatorService.getOne(x.getId2()).getFirstName(),
                                utilizatorService.getOne(x.getId2()).getLastName(),
                                x.getStatus(), x.getDate(), x.getId1(), x.getId2());
                    return null;
//                        return new DtoCererePrietenie(
//                                x.getId(),
//                                utilizatorService.getOne(x.getId2()).getFirstName(),
//                                utilizatorService.getOne(x.getId2()).getLastName(),
//                                x.getStatus(), x.getDate(), x.getId1(), x.getId2());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<DtoCererePrietenie> getRecivedForUser(int id) {
        List<CererePrietenie> cereriPrietenie = getAll();

        return cereriPrietenie.stream()
                .filter(x -> x.getId1() == id || x.getId2() == id)
                .map(x -> {

                    if (x.getId2() == id)
                        return new DtoCererePrietenie(
                                x.getId(),
                                utilizatorService.getOne(x.getId1()).getFirstName(),
                                utilizatorService.getOne(x.getId1()).getLastName(),
                                x.getStatus(), x.getDate(), x.getId1(), x.getId2());
                    else
                        return null;
//                        return new DtoCererePrietenie(
//                                x.getId(),
//                                utilizatorService.getOne(x.getId2()).getFirstName(),
//                                utilizatorService.getOne(x.getId2()).getLastName(),
//                                x.getStatus(), x.getDate(), x.getId1(), x.getId2());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
