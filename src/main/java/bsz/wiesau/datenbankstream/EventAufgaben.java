package bsz.wiesau.datenbankstream;

import bsz.wiesau.datenbankstream.db.Event.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventAufgaben {
    public static void starteAufgaben(List<Event> alleEvents) {
        aufgabe1_EventsInOrt(alleEvents);
        aufgabe2_DurchschnittTeilnehmerBarrierefrei(alleEvents);
        aufgabe3_EventsNaechste30Tage(alleEvents);
        aufgabe4_ChronologischSortiert(alleEvents);
        aufgabe5_EindeutigeOrte(alleEvents);
        aufgabe6_ProzentAnteilBarrierefrei(alleEvents);
    }

    /**
     * AUFGABE 1:
     * Geben Sie alle Events aus, die an einem bestimmten Ort (z.B. "München") stattfinden.
     */
    public static void aufgabe1_EventsInOrt(List<Event> events) {
        // TODO: Stream implementieren
        List<Event> ergebnis = new ArrayList<>();

        DatenbankStreamApplication.anzeigen("Aufgabe 1: Events in München", ergebnis);

    }

    /**
     * AUFGABE 2:
     * Ermitteln Sie die durchschnittliche maximale Teilnehmerzahl aller barrierefreien Events.
     */
    public static void aufgabe2_DurchschnittTeilnehmerBarrierefrei(List<Event> events) {
        // TODO: Stream implementieren

        //TODO Zusatz: Nutzer über Ergebnis informieren
    }

    /**
     * AUFGABE 3:
     * Zählen Sie, wie viele Events in den nächsten 30 Tagen stattfinden.
     */
    public static void aufgabe3_EventsNaechste30Tage(List<Event> events) {
        List<Event> ergebnis = new ArrayList<>();
        // TODO: Stream implementieren

        DatenbankStreamApplication.anzeigen("Aufgabe 3: Events in den nächsten 30 Tagen", ergebnis);
    }

    /**
     * AUFGABE 4:
     * Sortieren Sie alle Events chronologisch nach ihrem Zeitpunkt.
     */
    public static void aufgabe4_ChronologischSortiert(List<Event> events) {
        // TODO: Stream implementieren
        List<Event> ergebnis = new ArrayList<>();

        DatenbankStreamApplication.anzeigen("Aufgabe 4: Events sortiert", ergebnis);
    }

    /**
     * AUFGABE 5:
     * Erstellen Sie eine Liste, die nur die eindeutigen Namen aller Veranstaltungsorte enthält.
     */
    public static void aufgabe5_EindeutigeOrte(List<Event> events) {
        // TODO: Stream implementieren
        List<Event> ergebnis = new ArrayList<>();

        DatenbankStreamApplication.anzeigen("Aufgabe 5: Orte", ergebnis);
    }

    /**
     * AUFGABE 6:
     * Berechnen Sie den prozentualen Anteil der barrierefreien Events an der Gesamtzahl.
     */
    public static void aufgabe6_ProzentAnteilBarrierefrei(List<Event> events) {
        // TODO: Stream implementieren

        //TODO Zusatz: Nutzer über Ergebnis informieren
    }
}