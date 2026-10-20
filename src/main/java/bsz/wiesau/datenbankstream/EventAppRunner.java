package bsz.wiesau.datenbankstream;

import bsz.wiesau.datenbankstream.db.Event.Event;
import bsz.wiesau.datenbankstream.db.Event.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class EventAppRunner implements CommandLineRunner {

    private final EventRepository eventRepository;

    // Dependency Injection des Repositories
    public EventAppRunner(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (eventRepository.count() == 0) {
            List<Event> neueEvents = new ArrayList<>();
            String[] themen = {"Java Workshop", "Spring Boot Meetup", "Cybersecurity Info", "Cloud Computing", "KI in der Praxis", "WebDev Bootcamp", "Datenbanken Tuning", "Agiles Projektmanagement", "Linux Admin", "UX/UI Design"};
            String[] orte = {"München", "Berlin", "Frankfurt", "Hamburg", "Wiesau", "Regensburg", "Nürnberg", "Stuttgart"};

            Random random = new Random();

            for (int i = 1; i <= 100; i++) {
                String titel = themen[random.nextInt(themen.length)] + " #" + i;
                LocalDateTime zeitpunkt = LocalDateTime.now().plusDays(random.nextInt(90)).plusHours(random.nextInt(10));
                String ort = orte[random.nextInt(orte.length)];
                String bildURL = "https://example.com/bilder/event" + i + ".jpg";
                boolean behindertengerecht = random.nextBoolean();
                int maxTeilnehmer = 20 + random.nextInt(180); // Zwischen 20 und 200 Teilnehmern

                Event event = new Event(titel, zeitpunkt, ort, bildURL, behindertengerecht, maxTeilnehmer);
                neueEvents.add(event);
            }

            // Alle 100 Events auf einmal in der Datenbank speichern
            eventRepository.saveAll(neueEvents);
            System.out.println(">> Erfolgreich 100 Events in der Datenbank gespeichert!");
        } else {
            System.out.println(">> Die Datenbank enthält bereits Events. Es wurden keine neuen generiert.");
        }
    }
}