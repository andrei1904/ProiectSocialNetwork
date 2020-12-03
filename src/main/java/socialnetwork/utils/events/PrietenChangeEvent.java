package socialnetwork.utils.events;

import socialnetwork.domain.DtoPrieten;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;

public class PrietenChangeEvent implements Event {
    private ChangeEventType type;
    private Prietenie data, oldData;

    public PrietenChangeEvent(ChangeEventType type, Prietenie data) {
        this.type = type;
        this.data = data;
    }

    public PrietenChangeEvent(ChangeEventType type, Prietenie data, Prietenie oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Prietenie getData() {
        return data;
    }

    public Prietenie getOldData() {
        return oldData;
    }
}