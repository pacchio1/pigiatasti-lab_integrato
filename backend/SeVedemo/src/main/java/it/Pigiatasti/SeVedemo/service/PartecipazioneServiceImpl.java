package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.entity.Partecipazione;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.execption.EventoException;
import it.Pigiatasti.SeVedemo.execption.UtenteException;
import it.Pigiatasti.SeVedemo.repository.EventoRepository;
import it.Pigiatasti.SeVedemo.repository.PartecipazioneRepository;
import it.Pigiatasti.SeVedemo.repository.UtenteRepository;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartecipazioneServiceImpl implements PartecipazioneService{
    private final PartecipazioneRepository partecipazioneRepository;
    private final UtenteRepository utenteRepository;
    private final EventoRepository eventoRepository;

    @Autowired
    public PartecipazioneServiceImpl(PartecipazioneRepository partecipazioneRepository, UtenteRepository utenteRepository, EventoRepository eventoRepository) {
        this.partecipazioneRepository = partecipazioneRepository;
        this.utenteRepository = utenteRepository;
        this.eventoRepository = eventoRepository;
    }
    @Override
    public List<Partecipazione> getPartecipazioniPassate(int id) {
        return partecipazioneRepository.partecipazioniPassate(id);
    }
    @Override
    public List<Partecipazione> getPartecipazioniFuture(int id) {
        return partecipazioneRepository.partecipazioniFuture(id);
    }

    @Override
    public Partecipazione creaPartecipazione(Integer idUtente, Integer idEvento) {
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new UtenteException(idUtente, "Utente non trovato con ID: " + idUtente));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new EventoException(idEvento, "Evento non trovato con ID: " + idEvento));

        LocalDateTime dataInizio = evento.getData_inizio();
        LocalDateTime dataCorrente = LocalDateTime.now();

        if (dataInizio.isBefore(dataCorrente)) {
            throw new IllegalArgumentException("Impossibile iscriversi a eventi passati");
        }

        Partecipazione partecipazione = new Partecipazione();
        partecipazione.setId_utente(utente);
        partecipazione.setEvento(evento);

        try {
            return partecipazioneRepository.save(partecipazione);
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore durante la creazione della partecipazione");
        }
    }


    @Override
    public void eliminaPartecipazione(int idPartecipazione) {
        Partecipazione partecipazione = partecipazioneRepository.findById(idPartecipazione)
                .orElseThrow(() -> new IllegalArgumentException("Partecipazione non trovata con ID: " + idPartecipazione));

        LocalDateTime dataInizio = partecipazione.getEvento().getData_inizio();
        LocalDateTime dataCorrente = LocalDateTime.now();

        if (dataInizio.isBefore(dataCorrente)) {
            throw new IllegalArgumentException("Impossibile eliminare partecipazioni passate");
        }

        partecipazioneRepository.delete(partecipazione);
    }

    @Override
    public Partecipazione PartecipazionePerId(Integer idUtente, Integer idEvento) {
        return partecipazioneRepository.PartecipazionePerId(idUtente, idEvento);
    }


}
