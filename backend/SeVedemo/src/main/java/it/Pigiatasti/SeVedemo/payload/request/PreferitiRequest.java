package it.Pigiatasti.SeVedemo.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreferitiRequest {
    private int id_utente;
    private int utente_preferito;
}
