package it.Pigiatasti.SeVedemo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventiUtenteResponse {
    private int id_evento;
    private int id_partecipazione;
    private String email;
    private String nome;
    private String cognome;
    private String evento;
    private String descrizione_evento;
    private int n_max_partecipanti;
    private String posizione;
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine;
    private String categoria_evento;
}
