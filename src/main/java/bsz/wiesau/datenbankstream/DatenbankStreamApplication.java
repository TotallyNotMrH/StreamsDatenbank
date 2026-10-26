package bsz.wiesau.datenbankstream;

import bsz.wiesau.datenbankstream.db.Event.Event;
import bsz.wiesau.datenbankstream.db.Event.EventRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

@SpringBootApplication
public class DatenbankStreamApplication {
    private static final Preferences PREFS = Preferences.userNodeForPackage(DatenbankStreamApplication.class);
    private static final String PREF_KEY_PASSWORD = "db_password";

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        // 1. Dialog anzeigen
        String[] optionen = {"Echte Datenbank", "Mock-Daten (Offline)"};
        int auswahl = JOptionPane.showOptionDialog(
                null,
                "<html><b>Datenbank-Modus wählen</b><br><br>Wie soll das Programm gestartet werden?</html>",
                "Modus-Auswahl",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionen,
                optionen[0]
        );

        if (auswahl == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        if (auswahl == 0) {
            // --- MODUS 1: ECHTE DATENBANK (Mit Spring Boot & Server) ---
            ConfigurableApplicationContext context = ladeDatenausEchterDatenbank(args);
            EventRepository eventRepository = context.getBean(EventRepository.class);
            List<Event> alleEvents = eventRepository.findAll();
            EventAufgaben.starteAufgaben(alleEvents);

        } else {
            // --- MODUS 2: MOCK-DATEN (Vollkommen ohne Spring Boot & ohne Datenbank) ---
            System.out.println(">> Starte im reinen Offline-Mock-Modus...");
            List<Event> alleEvents = generiereFesteMockEvents();
            System.out.println(">> Erfolgreich 100 deterministische Mock-Events geladen!");

            // Direkt ausführen – es wird kein Server, kein Treiber und kein Kontext benötigt!
            EventAufgaben.starteAufgaben(alleEvents);
        }
    }
    /**
     * Methode 1: Kümmert sich um den Passwort-Dialog, System-Properties und den Start der echten DB.
     */
    private static ConfigurableApplicationContext ladeDatenausEchterDatenbank(String[] args) {
        String password = PREFS.get(PREF_KEY_PASSWORD, null);

        if (password == null || password.isEmpty()) {
            JPasswordField passwordField = new JPasswordField();
            Object[] message = {
                    "<html><b>Datenbank-Login (bszw.ddns.net:3000)</b><br><br>Bitte Passwort eingeben (wird gespeichert):</html>",
                    passwordField
            };

            int option = JOptionPane.showConfirmDialog(
                    null, message, "Datenbank-Authentifizierung",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                password = new String(passwordField.getPassword());
                PREFS.put(PREF_KEY_PASSWORD, password);
            } else {
                System.exit(0);
            }
        }

        System.setProperty("DB_PASSWORD", password);
        return SpringApplication.run(DatenbankStreamApplication.class, args);
    }

    /**
     * Methode 2: Generiert exakt 100 immer gleiche (deterministische) Mock-Events
     * dank des festen Random-Seeds (42).
     */
    public static List<Event> generiereFesteMockEvents() {
        List<Event> festeEvents = new ArrayList<>();
        String[] themen = {"Java Workshop", "Spring Boot Meetup", "Cybersecurity Info", "Cloud Computing", "KI in der Praxis", "WebDev Bootcamp", "Datenbanken Tuning", "Agiles Projektmanagement", "Linux Admin", "UX/UI Design"};
        String[] orte = {"München", "Berlin", "Frankfurt", "Hamburg", "Wiesau", "Regensburg", "Nürnberg", "Stuttgart"};

        // Fester Seed (42) sorgt dafür, dass bei jedem Start exakt dieselben Zufallszahlen generiert werden
        Random random = new Random(42);
        // Ein fixer Startpunkt für das Datum, damit auch die Zeitpunkte identisch bleiben
        LocalDateTime basisDatum = LocalDateTime.of(2026, 6, 1, 10, 0);

        for (int i = 1; i <= 100; i++) {
            String titel = themen[random.nextInt(themen.length)] + " #" + i;
            LocalDateTime zeitpunkt = basisDatum.plusDays(random.nextInt(90)).plusHours(random.nextInt(10));
            String ort = orte[random.nextInt(orte.length)];
            String bildURL = "https://example.com/bilder/event" + i + ".jpg";
            boolean behindertengerecht = random.nextBoolean();
            int maxTeilnehmer = 20 + random.nextInt(180);

            Event event = new Event(titel, zeitpunkt, ort, bildURL, behindertengerecht, maxTeilnehmer);
            // ID manuell setzen, da JPA/DB-Autoincrement im Mock-Modus greift
            event.setId(i);
            festeEvents.add(event);
        }

        return festeEvents;
    }
    /**
     * Zentrale Anzeigemethode für die Schüler-Ergebnisse.
     */
    public static void anzeigen(String ueberschrift, List<Event> eventListe) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(ueberschrift);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(850, 400);
            frame.setLocationRelativeTo(null);

            String[] spaltenNamen = {"ID", "Titel", "Zeitpunkt", "Ort", "Max. Teilnehmer", "Behindertengerecht"};
            DefaultTableModel model = new DefaultTableModel(spaltenNamen, 0);

            for (Event e : eventListe) {
                Object[] zeile = {
                        e.getId(), e.getTitel(), e.getZeitpunkt(),
                        e.getOrt(), e.getMaximaleTeilnehmer(),
                        e.isIstBehindertengerecht() ? "Ja" : "Nein"
                };
                model.addRow(zeile);
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            JLabel statusLabel = new JLabel("  Gefundene Datensätze: " + eventListe.size());
            statusLabel.setBorder(BorderFactory.createEtchedBorder());
            frame.add(statusLabel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }
}
