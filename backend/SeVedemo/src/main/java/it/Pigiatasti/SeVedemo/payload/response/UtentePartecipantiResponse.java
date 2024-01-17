package it.Pigiatasti.SeVedemo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtentePartecipantiResponse {

    public int id_utente;
    public String nome;
    public String cognome;
}
