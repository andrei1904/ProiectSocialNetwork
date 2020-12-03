package socialnetwork.service;

import socialnetwork.utils.events.PrietenChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;


public class AllService implements Observable<PrietenChangeEvent> {
    public final UtilizatorService utilizatorService;
    public final PrietenieService prietenieService;
    public final CererePrietenieService cererePrietenieService;
    public final PrieteniiService prieteniiService;

    public AllService(UtilizatorService utilizatorService, PrietenieService prietenieService, CererePrietenieService cererePrietenieService, PrieteniiService prieteniiService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.cererePrietenieService = cererePrietenieService;
        this.prieteniiService = prieteniiService;
    }

    public void deletePrietenie(int id1, int id2) {
        prieteniiService.deletePrietenie(id1, id2);
        cererePrietenieService.deleteCerere(id1, id2);
        notifyObservers();
    }

    public void addPrietenie(int id1, int id2) {
        cererePrietenieService.sendFriendRequest(id1, id2);
        notifyObservers();
    }

    public void acceptPrietenie(int id) {
        cererePrietenieService.confirmFriendRequest(id, 1);
        notifyObservers();
    }

    public void declinePrietenie(int id) {
        cererePrietenieService.confirmFriendRequest(id, 0);
        notifyObservers();
    }

    private final List<Observer<PrietenChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<PrietenChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<PrietenChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

}
