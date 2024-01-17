package it.Pigiatasti.SeVedemo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventiResponseFuturi {
    private int id_evento;
    private String titolo;
    private String categoria;
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine;
    private String descrizione;
    private int n_max_partecipanti;
    private String nome_host;
    private String cognome_host;
    private int partecipanti;
    private String posizione;
}
