package bsz.wiesau.datenbankstream.db.Event;

import java.lang.Record;
import java.time.LocalDateTime;

public record EventDTO(String titel, LocalDateTime zeitpunkt, String ort, String bildURL, boolean istBehindertengerecht, int maximaleTeilnehmer) {
}
