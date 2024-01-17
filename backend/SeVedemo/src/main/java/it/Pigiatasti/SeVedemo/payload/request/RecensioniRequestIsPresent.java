package it.Pigiatasti.SeVedemo.payload.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecensioniRequestIsPresent {
    private int id_utente;
    private int id_evento;
}
