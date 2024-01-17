package it.Pigiatasti.SeVedemo.service;


import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.payload.request.CreaEventoRequest;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponse;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponsePassati;
import it.Pigiatasti.SeVedemo.payload.response.UtentePartecipantiResponse;
import jakarta.mail.MessagingException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EventoService {
    List<EventiResponsePassati> GetTuttiEventi();
    List<EventiResponse> GetTuttiEventiAttivi();
    List<EventiResponse> GetTuttiEventiPerId(int id);
    List<EventiResponse> buildEventiResponseList(List<Object[]> risultati);
    List<UtentePartecipantiResponse> buildUtentiRisultatiList(List<Object[]> risultati);
    List<EventiResponse> GetTuttiEventiCategoria(String categoria);
    List<EventiResponse> GetTuttiEventiHost(String host);
    List<EventiResponse> GetTuttiEventiData(String data);
    List<EventiResponse> GetTuttiEventiCittà(String citta);

    List<UtentePartecipantiResponse> getPartecipantiEvento(int id);

    Evento createEvento(CreaEventoRequest evento);
    Evento cancellaEvento(int idEvento) throws MessagingException;
}
