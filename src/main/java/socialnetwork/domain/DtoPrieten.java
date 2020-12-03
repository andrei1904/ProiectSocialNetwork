package socialnetwork.domain;


import socialnetwork.utils.Constants;
import socialnetwork.utils.events.Event;

import java.time.LocalDateTime;

public class DtoPrieten {
    int id;
    String firstName;
    String lastName;
    LocalDateTime date;

    public DtoPrieten(String firstName, String lastName, LocalDateTime date, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateString() {
        return date.format(Constants.DATE_TIME_FORMATTER);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DtoPrieten{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                '}';
    }
}
