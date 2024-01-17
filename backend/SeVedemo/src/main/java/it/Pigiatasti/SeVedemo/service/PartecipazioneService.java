package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Partecipazione;

import java.util.List;

public interface PartecipazioneService {
    public List<Partecipazione> getPartecipazioniPassate(int id);
    public List<Partecipazione> getPartecipazioniFuture(int id);

    public Partecipazione creaPartecipazione(Integer idUtente, Integer idEvento);

    void eliminaPartecipazione(int id);

    Partecipazione PartecipazionePerId(Integer idUtente, Integer idEvento);
}
