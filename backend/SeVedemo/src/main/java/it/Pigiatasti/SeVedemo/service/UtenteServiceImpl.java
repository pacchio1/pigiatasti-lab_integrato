package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.execption.UtenteNotFoundException;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponseFuturi;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponsePassati;
import it.Pigiatasti.SeVedemo.payload.response.EventiUtenteResponse;
import it.Pigiatasti.SeVedemo.payload.response.RecensioniUtenteResponse;
import it.Pigiatasti.SeVedemo.repository.UtenteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private EntityManager entityManager;
    private UtenteRepository utenteRepository;

    @Autowired
    public UtenteServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public List<Utente> selezionaTuttiUtenti() {
        return utenteRepository.findAll();
    }
    @Override
    public Optional<Utente> trovaUtentePerId(int id) {
        return utenteRepository.findById(id);
    }
    @Override
    public Utente inserisciUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
    @Override
    public void cancellaUtentePerId(int id) {
        utenteRepository.deleteById(id);
    }

    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
    @Override
    public boolean cancellaEvento(int id) {
        return utenteRepository.eliminaEvento(id);
    }

    @Override
    public void creaRecensione(Recensione recensione) {
        utenteRepository.save(recensione);
    }

    @Override
    public Optional<Utente> ottieniUtentePerId(int id) {
        return utenteRepository.findByIdUtente(id);
    }
    @Override
    public Optional<Utente> utentePerEmail(String email) {
        return utenteRepository.findByEmail(email);
    }


    @Override
    public Utente aggiornaUtente(Utente utente, int IdUtente) {  //Update
        Optional<Utente> optionalUtente = utenteRepository.findByIdUtente(IdUtente);

        if (optionalUtente.isPresent()) {
            Utente utenteEsistente = optionalUtente.get();

            // Verifica se i campi di modifica sono presenti e, se sì, li aggiorna
            if (utente.getNome() != null) {
                utenteEsistente.setNome(utente.getNome());
            }
            if (utente.getCognome() != null) {
                utenteEsistente.setCognome(utente.getCognome());
            }
            if (utente.getEmail() != null) {
                utenteEsistente.setEmail(utente.getEmail());
            }
            if (utente.getPassword() != null) {
                utenteEsistente.setPassword(utente.getPassword());
            }
            if (utente.getPresentazione() != null) {
                utenteEsistente.setPresentazione(utente.getPresentazione());
            }
            if (utente.getTelefono() != null) {
                utenteEsistente.setTelefono(utente.getTelefono());
            }
            if (utente.getId_gender() != null) {
                utenteEsistente.setId_gender(utente.getId_gender());
            }
            if (utente.getRuolo() != null) {
                utenteEsistente.setRuolo(utente.getRuolo());
            }
            return utenteRepository.save(utenteEsistente);
        } else {
            throw new UtenteNotFoundException("Nessun utente trovato con quell'id");
        }
    }

    @Override
    public List<RecensioniUtenteResponse> recensioniUtente(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetRecensioniById"); // inizializzo stored procedure richiamando il nome della procedure
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN); // definisco il tipo di variabile nella stored procedure
        query.setParameter("p_id", id);  // setto il parametro passando la mail in argomento del metodo

        query.execute();

        List<Object[]> results = query.getResultList();   // Risultati della query
        List<RecensioniUtenteResponse> recensioni = new ArrayList<>();   // crea la variabile recensioni una lista che fa fede al payload RecensioniUtenteResponse

        for (Object[] row : results) {
            RecensioniUtenteResponse recensione = RecensioniUtenteResponse.builder() //buildo l oggetto di tipo RecensioniUtenteResponse
                    .id_recensione((int) row[0])
                    .recensione((String) row[1])
                    .voto((int) row[2])
                    .autoreRecensione((String) row[3])
                    .evento((String) row[4])
                    .categoria((String) row[5])
                    .data_inizio((Timestamp) row[6])
                    .build();
            recensioni.add(recensione);
        }
        return recensioni;
    }

    @Override
    public List<EventiResponsePassati> eventiPartecipatiUtente(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiPartecipati");
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.setParameter("p_id", id);
        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponsePassati> eventi = new ArrayList<>();

        for (Object[] row : risultati) {
            EventiResponsePassati evento = createEventiResponsePassati(row);
            eventi.add(evento);
        }

        return eventi;
    }
    @Override
    public List<EventiResponsePassati> eventiOrganizzatiPassati(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiOrganizzatiHostPassati");
        query.registerStoredProcedureParameter("sp_idHost", Integer.class, ParameterMode.IN);
        query.setParameter("sp_idHost", id);
        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponsePassati> eventi = new ArrayList<>();

        for (Object[] row : risultati) {
            EventiResponsePassati evento = createEventiResponsePassati(row);
            eventi.add(evento);
        }

        return eventi;
    }
    @Override
    public List<EventiResponseFuturi> eventiOrganizzatiFuturi(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiOrganizzatiHostFuturi");
        query.registerStoredProcedureParameter("sp_idHost", Integer.class, ParameterMode.IN);
        query.setParameter("sp_idHost", id);

        query.execute();

        List<Object[]> results = query.getResultList();
        List<EventiResponseFuturi> eventi = new ArrayList<>();

        for (Object[] row : results) {
            EventiResponseFuturi evento = createEventiResponseFuturi(row);
            eventi.add(evento);
        }

        return eventi;
    }
    @Override
    public List<EventiUtenteResponse> eventiUtente(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetListaEventiUtente");
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.setParameter("p_id", id);

        query.execute();

        List<Object[]> results = query.getResultList();
        List<EventiUtenteResponse> eventi = new ArrayList<>();

        for (Object[] row : results) {
            EventiUtenteResponse evento = createEventiUtenteResponse(row);
            eventi.add(evento);
        }

        return eventi;
    }

    private EventiResponsePassati createEventiResponsePassati(Object[] row) {
        Float mediaVoti = null;
        if (row[11] != null) {
            BigDecimal votiDecimal = (BigDecimal) row[11];
            mediaVoti = votiDecimal.floatValue();
        }

        return EventiResponsePassati.builder()
                .id_evento(((Long) row[0]).intValue())
                .id_utente(((Long) row[1]).intValue())
                .titolo((String) row[2])
                .categoria((String) row[3])
                .data_inizio(convertTimestampToLocalDateTime((Timestamp) row[4]))
                .data_fine(convertTimestampToLocalDateTime((Timestamp) row[5]))
                .descrizione((String) row[6])
                .n_max_partecipanti(((Long) row[7]).intValue())
                .nome_host((String) row[8])
                .cognome_host((String) row[9])
                .partecipanti(((Long) row[10]).intValue())
                .media_voti(mediaVoti)
                .posizione((String) row[12])
                .build();
    }

    private EventiResponseFuturi createEventiResponseFuturi(Object[] row) {
        Integer partecipanti = null;
        if (row[9] != null) {
            partecipanti = ((BigDecimal) row[9]).intValue();
        }

        return EventiResponseFuturi.builder()
                .id_evento(((Long) row[0]).intValue())
                .titolo((String) row[1])
                .categoria((String) row[2])
                .data_inizio(convertTimestampToLocalDateTime((Timestamp) row[3]))
                .data_fine(convertTimestampToLocalDateTime((Timestamp) row[4]))
                .descrizione((String) row[5])
                .n_max_partecipanti(((Long) row[6]).intValue())
                .nome_host((String) row[7])
                .cognome_host((String) row[8])
                .partecipanti(partecipanti != null ? partecipanti : 0) // Imposta a 0 se partecipanti è null
                .posizione((String) row[10])
                .build();
    }


    private EventiUtenteResponse createEventiUtenteResponse(Object[] row) {
        return EventiUtenteResponse.builder()
                .id_evento(((Long) row[0]).intValue())
                .id_partecipazione(((Long) row[1]).intValue())
                .email((String) row[2])
                .nome((String) row[3])
                .cognome((String) row[4])
                .evento((String) row[5])
                .descrizione_evento((String) row[6])
                .n_max_partecipanti(((Long) row[7]).intValue())
                .posizione((String) row[8])
                .data_inizio(convertTimestampToLocalDateTime((Timestamp) row[9]))
                .data_fine(convertTimestampToLocalDateTime((Timestamp) row[10]))
                .categoria_evento((String) row[11])
                .build();
    }

}
