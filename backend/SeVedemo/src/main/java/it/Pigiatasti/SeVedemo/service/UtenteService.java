package it.Pigiatasti.SeVedemo.service;


import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponseFuturi;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponsePassati;
import it.Pigiatasti.SeVedemo.payload.response.EventiUtenteResponse;
import it.Pigiatasti.SeVedemo.payload.response.RecensioniUtenteResponse;

import java.util.List;
import java.util.Optional;

public interface UtenteService {

    List<Utente> selezionaTuttiUtenti();
    Optional<Utente> trovaUtentePerId(int id);
    Utente inserisciUtente(Utente utente);
    void cancellaUtentePerId(int id);
    Utente aggiornaUtente(Utente utente, int id);
    List<RecensioniUtenteResponse> recensioniUtente(int id);
    List<EventiUtenteResponse> eventiUtente(int id);
    boolean cancellaEvento(int id);
    void creaRecensione(Recensione recensione);
    Optional<Utente> ottieniUtentePerId(int id);
    Optional<Utente> utentePerEmail(String email);
    List<EventiResponsePassati> eventiPartecipatiUtente(int id);
    List<EventiResponsePassati> eventiOrganizzatiPassati(int id);
    List<EventiResponseFuturi> eventiOrganizzatiFuturi(int id);
}
