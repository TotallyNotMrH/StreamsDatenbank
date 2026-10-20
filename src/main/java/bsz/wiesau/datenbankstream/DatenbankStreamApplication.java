package bsz.wiesau.datenbankstream;

import bsz.wiesau.datenbankstream.db.Event.Event;
import bsz.wiesau.datenbankstream.db.Event.EventRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.prefs.Preferences;

@SpringBootApplication
public class DatenbankStreamApplication {
    private static final Preferences PREFS = Preferences.userNodeForPackage(DatenbankStreamApplication.class);
    private static final String PREF_KEY_PASSWORD = "db_password";

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        // 1. Versuchen, das gespeicherte Passwort zu laden
        String password = PREFS.get(PREF_KEY_PASSWORD, null);

        // 2. Wenn kein Passwort gespeichert ist, Dialog anzeigen
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
                // Passwort für zukünftige Starts persistent speichern
                PREFS.put(PREF_KEY_PASSWORD, password);
            } else {
                System.exit(0);
            }
        }

        // 3. Passwort an Spring übergeben
        System.setProperty("DB_PASSWORD", password);

        // 4. Spring Boot Kontext starten
        ConfigurableApplicationContext context = SpringApplication.run(DatenbankStreamApplication.class, args);
        EventRepository eventRepository = context.getBean(EventRepository.class);

        // 5. Events laden und an die Aufgabenklasse übergeben
        List<Event> alleEvents = eventRepository.findAll();
        EventAufgaben.starteAufgaben(alleEvents);
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
