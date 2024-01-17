package it.Pigiatasti.SeVedemo.payload.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreaEventoRequest {
    private int id_utente;
    private LocalDateTime data_inizio;
    private LocalDateTime data_fine;
    private String titolo;
    private String descrizione;
    private int n_max_partecipanti;
    private String posizione;
    private int id_categoria;
}
