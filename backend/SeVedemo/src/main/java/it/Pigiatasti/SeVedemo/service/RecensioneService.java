package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.payload.request.RecensioniRequest;
import it.Pigiatasti.SeVedemo.payload.request.RecensioniRequestIsPresent;

import java.util.Optional;

public interface RecensioneService {

    boolean creaRecensione(Integer idUtente, Integer idEvento, Integer voto, String descrizione);

    public Recensione cancellaRecensione(int idRecensione);

    public Optional<Recensione> getRecensioneById(int id);

    public Recensione isPresent(RecensioniRequestIsPresent recensione);
}

