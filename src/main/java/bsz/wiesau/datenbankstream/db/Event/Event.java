package bsz.wiesau.datenbankstream.db.Event;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String titel;
    protected LocalDateTime zeitpunkt;
    protected String ort;
    protected String bildURL;

    protected boolean istBehindertengerecht;

    protected  int maximaleTeilnehmer;





    public Event(){}

    public Event(String titel, LocalDateTime zeitpunkt, String ort, String bildURL, boolean istBehindertengerecht, int maximaleTeilnehmer) {
        this.titel = titel;
        this.zeitpunkt = zeitpunkt;
        this.ort = ort;
        this.bildURL = bildURL;
        this.istBehindertengerecht = istBehindertengerecht;
        this.maximaleTeilnehmer = maximaleTeilnehmer;
    }

    public boolean isIstBehindertengerecht() {
        return istBehindertengerecht;
    }

    public void setIstBehindertengerecht(boolean istBehindertengerecht) {
        this.istBehindertengerecht = istBehindertengerecht;
    }

    public int getMaximaleTeilnehmer() {
        return maximaleTeilnehmer;
    }

    public void setMaximaleTeilnehmer(int maximaleTeilnehmer) {
        this.maximaleTeilnehmer = maximaleTeilnehmer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }

    public void setZeitpunkt(LocalDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getBildURL() {
        return bildURL;
    }

    public void setBildURL(String bildURL) {
        this.bildURL = bildURL;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", titel='" + titel + '\'' +
                ", zeitpunkt=" + zeitpunkt +
                ", ort='" + ort + '\'' +
                ", bildURL='" + bildURL + '\'' +
                '}';
    }
    public Event convertToEvent(EventDTO eventDTO){
        Event event = new Event();
        event.bildURL = eventDTO.bildURL();
        event.ort = eventDTO.ort();
        event.zeitpunkt = eventDTO.zeitpunkt();
        event.titel = eventDTO.titel();
        event.maximaleTeilnehmer = this.maximaleTeilnehmer;
        event.istBehindertengerecht = this.istBehindertengerecht;
        return event;
    }
}
