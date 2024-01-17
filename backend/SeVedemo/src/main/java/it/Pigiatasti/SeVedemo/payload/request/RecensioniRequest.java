package it.Pigiatasti.SeVedemo.payload.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecensioniRequest {
    private int idUtente;
    private int idEvento;
    private int voto;
    private String descrizione;
}
