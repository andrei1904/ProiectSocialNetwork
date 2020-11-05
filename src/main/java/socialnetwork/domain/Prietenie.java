package socialnetwork.domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;
    private int idPrieten1;
    private int idPrieten2;

    public Prietenie(int idPrieten1, int idPrieten2) {
        this.idPrieten1 = idPrieten1;
        this.idPrieten2 = idPrieten2;
    }


    public int getIdPrieten1() {
        return idPrieten1;
    }

    public void setIdPrieten1(int idPrieten1) {
        this.idPrieten1 = idPrieten1;
    }

    public int getIdPrieten2() {
        return idPrieten2;
    }

    public void setIdPrieten2(int idPrieten2) {
        this.idPrieten2 = idPrieten2;
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "idPrieten1=" + idPrieten1 +
                ", idPrieten2=" + idPrieten2 +
                '}';
    }
}
