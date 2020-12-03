package socialnetwork.domain;

import java.time.LocalDateTime;

public class CererePrietenie extends Entity<Integer> {
    int id1;
    int id2;
    String status;
    LocalDateTime date;

    public CererePrietenie(int id1, int id2, LocalDateTime date) {
        this.id1 = id1;
        this.id2 = id2;
        this.date = date;
    }

    public int getId1() {
        return id1;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
