package socialnetwork.utils.events;

import socialnetwork.domain.CererePrietenie;
import socialnetwork.domain.Utilizator;

public class CererePrietenieChangeEvent implements Event {
    private ChangeEventType type;
    private CererePrietenie data, oldData;

    public CererePrietenieChangeEvent(ChangeEventType type, CererePrietenie data) {
        this.type = type;
        this.data = data;
    }

    public CererePrietenieChangeEvent(ChangeEventType type, CererePrietenie data, CererePrietenie oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public CererePrietenie getData() {
        return data;
    }

    public CererePrietenie getOldData() {
        return oldData;
    }
}