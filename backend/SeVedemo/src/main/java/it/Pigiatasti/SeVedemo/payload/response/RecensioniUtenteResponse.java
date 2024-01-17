package it.Pigiatasti.SeVedemo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecensioniUtenteResponse {
    private int id_recensione;
    private String recensione;
    private int voto;
    private String autoreRecensione;
    private String evento;
    private String categoria;
    private Timestamp data_inizio;
}
