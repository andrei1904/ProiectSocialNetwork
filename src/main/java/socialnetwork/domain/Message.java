package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public class Message extends Entity<Integer> {
    Utilizator from;
    List<Utilizator> to;
    String message;
    LocalDateTime date;
    int reply;

    public Message(Utilizator from, List<Utilizator> to, String message, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
        this.reply = -1;
    }

    public Utilizator getFrom() {
        return from;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public void setTo(List<Utilizator> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(getFrom(), message1.getFrom()) &&
                Objects.equals(getTo(), message1.getTo()) &&
                Objects.equals(getMessage(), message1.getMessage()) &&
                Objects.equals(getDate(), message1.getDate()) &&
                Objects.equals(getReply(), message1.getReply());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo(), getMessage(), getDate(), getReply());
    }

    @Override
    public String toString() {
        StringBuilder to = new StringBuilder();
        int i = 0;
        for (i = 0; i < this.to.size() - 1; i++) {
            to.append(this.to.get(i));
            to.append(", ");
        }
        to.append(this.to.get(i));


        return "De la: " + from.toString() +
                ", pentru: " + to + "; la data: " + date.getYear() + " " + date.getMonth() +
                " " + date.getDayOfMonth() + ", ora: " + date.getHour() + ":" + date.getMinute() +
                "\n" + message;
    }
}
