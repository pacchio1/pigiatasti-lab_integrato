package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.entity.Partecipazione;
import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.execption.EventoException;
import it.Pigiatasti.SeVedemo.execption.UtenteException;
import it.Pigiatasti.SeVedemo.payload.request.RecensioniRequestIsPresent;
import it.Pigiatasti.SeVedemo.repository.EventoRepository;
import it.Pigiatasti.SeVedemo.repository.RecensioneRepository;
import it.Pigiatasti.SeVedemo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
public class RecensioneServiceImpl implements RecensioneService{

    private RecensioneRepository recensioneRepository;
    private UtenteRepository utenteRepository;
    private EventoRepository eventoRepository;
    private PartecipazioneServiceImpl partecipazioneService;

    @Autowired
    public RecensioneServiceImpl(RecensioneRepository recensioneRepository, UtenteRepository utenteRepository, EventoRepository eventoRepository, PartecipazioneServiceImpl partecipazioneService){
        this.recensioneRepository = recensioneRepository;
        this.utenteRepository = utenteRepository;
        this.eventoRepository = eventoRepository;
        this.partecipazioneService = partecipazioneService;
    }

    @Override
    public boolean creaRecensione(Integer idUtente, Integer idEvento, Integer voto, String descrizione){
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new UtenteException(idUtente, "Utente non trovato con ID: " + idUtente));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new EventoException(idEvento, "Evento non trovato con ID: " + idEvento));

        List<Partecipazione> partecipazioniPassate = partecipazioneService.getPartecipazioniPassate(idUtente);

        boolean hasParticipated = false;
        for (Partecipazione partecipazione : partecipazioniPassate) {
            if (partecipazione.getId_utente().getId_utente() == idUtente && partecipazione.getEvento().getId_evento() == idEvento) {
                hasParticipated = true;
                break;
            }
        }

        if (hasParticipated) {
            // L'utente ha partecipato all'evento, puoi scrivere la recensione
            recensioneRepository.creaRecensione(idUtente, idEvento, voto, descrizione);
            return true;
        }
        else {
            String errorMessage = "L'utente non ha partecipato all'evento.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }
    @Override
    public Recensione cancellaRecensione(int idRecensione) {
        Recensione recensione = recensioneRepository.findById(idRecensione)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recensione non trovata"));

        recensioneRepository.delete(recensione);
        return recensione;
    }

    @Override
    public Optional<Recensione> getRecensioneById(int id) {
        return recensioneRepository.findById(id);
    }

    @Override
    public Recensione isPresent(RecensioniRequestIsPresent recensione) {
        return recensioneRepository.isPresent(recensione.getId_evento(), recensione.getId_utente());
    }
}
