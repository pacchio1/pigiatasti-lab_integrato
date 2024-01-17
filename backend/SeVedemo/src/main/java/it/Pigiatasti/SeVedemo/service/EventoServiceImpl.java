package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.entity.Gender;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.execption.EventoException;
import it.Pigiatasti.SeVedemo.execption.EventoNonTrovatoException;
import it.Pigiatasti.SeVedemo.execption.UtenteNotFoundException;
import it.Pigiatasti.SeVedemo.payload.request.CreaEventoRequest;
import it.Pigiatasti.SeVedemo.payload.response.*;
import it.Pigiatasti.SeVedemo.repository.EventoRepository;
import it.Pigiatasti.SeVedemo.repository.UtenteRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {
    private EventoRepository eventoRepository;
    private JavaMailSender mailSender;
    private UtenteRepository utenteRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public EventoServiceImpl(EventoRepository eventoRepository, JavaMailSender mailSender, UtenteRepository utenteRepository) {
        this.eventoRepository = eventoRepository;
        this.mailSender = mailSender;
        this.utenteRepository = utenteRepository;
    }

    @Override
    public List<EventiResponsePassati> GetTuttiEventi() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiPassati");
        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponsePassati> eventi = new ArrayList<>();

        for (Object[] row : risultati) {
            Float mediaVoti = null;
            if (row[11] != null) {
                BigDecimal votiDecimal = (BigDecimal) row[11];
                mediaVoti = votiDecimal.floatValue();
            }

            EventiResponsePassati evento = EventiResponsePassati.builder()
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
                    .media_voti(mediaVoti)  // Utilizza il valore ottenuto o null se è null
                    .posizione((String) row[12])
                    .build();

            eventi.add(evento);
        }

        return eventi;
    }

    @Override
    public List<EventiResponse> GetTuttiEventiAttivi() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiAttivi");
        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }

    @Override
    public List<EventiResponse> GetTuttiEventiPerId(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventibyIdEvento");
        query.registerStoredProcedureParameter("sp_idCategoria", Integer.class, ParameterMode.IN);
        query.setParameter("sp_idCategoria", id);

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }


    public List<UtentePartecipantiResponse> buildUtentiRisultatiList(List<Object[]> risultati){
        List<UtentePartecipantiResponse> utenti = new ArrayList<>();

        for (Object[] row : risultati){
            UtentePartecipantiResponse utente = UtentePartecipantiResponse.builder()
                    .id_utente(((Long) row[0]).intValue())
                    .nome((String) row[1])
                    .cognome((String) row[2])
                    .build();
            utenti.add(utente);
        }
        return utenti;
    }

    @Override
    public List<EventiResponse> buildEventiResponseList(List<Object[]> risultati) {
        List<EventiResponse> eventi = new ArrayList<>();

        for (Object[] row : risultati) {
            EventiResponse evento = EventiResponse.builder()
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
                    .posizione((String) row[11])
                    .build();

            eventi.add(evento);
        }

        return eventi;
    }

    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    @Override
    public List<EventiResponse> GetTuttiEventiCategoria(String categoria) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiPerCategoria");
        query.registerStoredProcedureParameter("sp_categoria", String.class, ParameterMode.IN);
        query.setParameter("sp_categoria", categoria);

        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }

    @Override
    public List<EventiResponse> GetTuttiEventiHost(String host) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiPerHost");
        query.registerStoredProcedureParameter("sp_host", String.class, ParameterMode.IN);
        query.setParameter("sp_host", host);

        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }

    @Override
    public List<EventiResponse> GetTuttiEventiData(String data) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiPerData");
        query.registerStoredProcedureParameter("sp_data", String.class, ParameterMode.IN);
        query.setParameter("sp_data", data);

        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }

    @Override
    public List<EventiResponse> GetTuttiEventiCittà(String citta) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetEventiPerCitta");
        query.registerStoredProcedureParameter("sp_citta", String.class, ParameterMode.IN);
        query.setParameter("sp_citta", citta);

        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }

    @Override
    public Evento createEvento(CreaEventoRequest evento) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("salva_evento");

        query.registerStoredProcedureParameter("utente_id", Integer.class, ParameterMode.IN);
        query.setParameter("utente_id", evento.getId_utente());

        query.registerStoredProcedureParameter("data_inizio", LocalDateTime.class, ParameterMode.IN);
        query.setParameter("data_inizio", evento.getData_inizio());

        query.registerStoredProcedureParameter("data_fine", LocalDateTime.class, ParameterMode.IN);
        query.setParameter("data_fine", evento.getData_fine());

        query.registerStoredProcedureParameter("titolo", String.class, ParameterMode.IN);
        query.setParameter("titolo", evento.getTitolo().replace("'", "''"));

        query.registerStoredProcedureParameter("descrizione", String.class, ParameterMode.IN);
        query.setParameter("descrizione", evento.getDescrizione().replace("'", "''"));

        query.registerStoredProcedureParameter("max_partecipanti", Integer.class, ParameterMode.IN);
        query.setParameter("max_partecipanti", evento.getN_max_partecipanti());

        query.registerStoredProcedureParameter("posizione", String.class, ParameterMode.IN);
        query.setParameter("posizione", evento.getPosizione().replace("'", "''"));

        query.registerStoredProcedureParameter("categoria_id", Integer.class, ParameterMode.IN);
        query.setParameter("categoria_id", evento.getId_categoria());

        // Execute the stored procedure
        query.execute();

        // Retrieve the generated Evento object
        Query selectQuery = entityManager.createQuery("SELECT e FROM Evento e WHERE e.id_evento = (SELECT MAX(e2.id_evento) FROM Evento e2)");
        Evento createdEvento = (Evento) selectQuery.getSingleResult();

        return createdEvento;
    }



    @Override
    public Evento cancellaEvento(int id) throws MessagingException {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNonTrovatoException("Evento non trovato"));

        // Verifica se l'evento è nel futuro rispetto all'orario corrente
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = evento.getData_inizio();
        if (startTime.isBefore(now)) {
            throw new EventoException(evento.getId_evento(),"Non è possibile eliminare un evento passato");
        }

        // Verifica se sono passate meno di 5 ore dall'inizio dell'evento
        LocalDateTime fiveHoursBeforeStart = startTime.minusHours(5);
        if (now.isAfter(fiveHoursBeforeStart)) {
            throw new EventoException(evento.getId_evento(), "Non è possibile eliminare un evento meno di 5 ore dall'inizio");
        }

        //Invia un email a gli utenti iscritti all evento
        List<UtentiEmailResponse> utenti = eventoRepository.getPartecipanti(id);

        // Elimina l'evento dal repository
        eventoRepository.delete(evento);

        inviaEmailPartecipanti(id, evento, utenti);

        // Invia l'email di notifica
        inviaEmailEventoEliminatoUtente(evento);

        return evento;
    }

    @Override
    public List<UtentePartecipantiResponse> getPartecipantiEvento(int id){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_GetUtentiByIdEvento");
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.setParameter("p_id",id);

        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<UtentePartecipantiResponse> results = buildUtentiRisultatiList(risultati);
        return results;
    }

    private void inviaEmailEventoEliminatoUtente(Evento evento) throws MessagingException {
        int idUtente = evento.getId_utente();
        Optional<Utente> optionalUtente = utenteRepository.findById(idUtente);

        if (optionalUtente.isPresent()) {
            Utente utente = optionalUtente.get();
            String destinatario = utente.getEmail(); // Ottieni l'email dell'utente

            String oggetto = "Evento eliminato";
            String testo = "L'evento \"" + evento.getTitolo() + "\" è stato eliminato con successo.";

            // Creazione dell'email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            // Impostazione del mittente, destinatario, oggetto e testo dell'email
            mimeMessageHelper.setFrom("its.sevedemo@gmail.com");
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject(oggetto);

            // Creazione del corpo dell'email con le immagini incorporate
            String corpoEmail = "<html><body>";
            corpoEmail += "<div style='text-align: center;'>";
            corpoEmail += "<img src='cid:logo' style='width: 300px;'>";
            corpoEmail += "</div>";
            corpoEmail += "<h3>Questa è un'email autogenerata. Si prega di non rispondere.</h3>";
            corpoEmail += "<p style='font-size: 18px; font-weight: bold;'>L'evento \"" + evento.getTitolo() + "\" è stato eliminato con successo.</p>";
            corpoEmail += "<hr/>";
            corpoEmail += "<h3 style='text-align: center;'>Powered by Pigiatasti Srl</h3>";
            corpoEmail += "<p style='text-align: center;'>Via Paoloni, 12345 - Torino, Italia</p>";
            corpoEmail += "<p style='text-align: center;'>Telefono: 1234567890</p>";
            corpoEmail += "<p style='text-align: center;'>Sito web: <a href='http://www.pigiatasti.com'>www.pigiatasti.com</a></p>";
            corpoEmail += "<div style='text-align: center;'>";
            corpoEmail += "<img src='cid:companyLogo' style='width: 120px;'>";
            corpoEmail += "</div>";
            corpoEmail += "</body></html>";

            // Aggiunta delle immagini incorporate come allegati
            mimeMessageHelper.setText(corpoEmail, true);
            mimeMessageHelper.addInline("logo", new ClassPathResource("images/CompanyLogo.jpg"));
            mimeMessageHelper.addInline("companyLogo", new ClassPathResource("images/PigiaTastiLogo.jpg"));

            // Invio dell'email
            mailSender.send(mimeMessage);
        } else {
            throw new UtenteNotFoundException("Utente non trovato con ID: " + idUtente);
        }
    }



    public void inviaEmailPartecipanti(int id, Evento evento, List<UtentiEmailResponse> utenti) throws MessagingException {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataInizio = evento.getData_inizio();
        String dataFormattata = dataInizio.format(formatoData);

        for (UtentiEmailResponse utente : utenti) {
            String oggetto = "Evento eliminato";
            String destinatario = utente.getEmail();
            String nome = utente.getNome();
            String cognome = utente.getCognome();
            // Creazione dell'email
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            // Impostazione del mittente, destinatario, oggetto e testo dell'email
            mimeMessageHelper.setFrom("its.sevedemo@gmail.com");
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject(oggetto);

            // Creazione del corpo dell'email con le immagini incorporate
            String corpoEmail = "<html><body>";
            corpoEmail += "<div style='text-align: center;'>";
            corpoEmail += "<img src='cid:logo' style='width: 300px;'>";
            corpoEmail += "</div>";
            corpoEmail += "<h3>Questa è un'email autogenerata. Si prega di non rispondere.</h3>";
            corpoEmail += "<p style='font-size: 18px; font-weight: bold;'>"+  nome +" "+ cognome +" Ha eliminato l'evento <br />\n\"" + evento.getTitolo() + "\"\nprogrammato per "+ dataFormattata +"</p>";
            corpoEmail += "<hr/>";
            corpoEmail += "<h3 style='text-align: center;'>Powered by Pigiatasti Srl</h3>";
            corpoEmail += "<p style='text-align: center;'>Via Paoloni, 12345 - Torino, Italia</p>";
            corpoEmail += "<p style='text-align: center;'>Telefono: 1234567890</p>";
            corpoEmail += "<p style='text-align: center;'>Sito web: <a href='http://www.pigiatasti.com'>www.pigiatasti.com</a></p>";
            corpoEmail += "<div style='text-align: center;'>";
            corpoEmail += "<img src='cid:companyLogo' style='width: 120px;'>";
            corpoEmail += "</div>";
            corpoEmail += "</body></html>";

            // Aggiunta delle immagini incorporate come allegati
            mimeMessageHelper.setText(corpoEmail, true);
            mimeMessageHelper.addInline("logo", new ClassPathResource("images/CompanyLogo.jpg"));
            mimeMessageHelper.addInline("companyLogo", new ClassPathResource("images/PigiaTastiLogo.jpg"));

            // Invio dell'email
            mailSender.send(mimeMessage);
        }
    }


    public List<EventiResponse> GetTuttiEventiPiuFiltri(String citta, String filtroCategoria, String filtroHost, LocalDate filtroData) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetTuttiEventiFiltriDinamici");
        query.registerStoredProcedureParameter("sp_filtroCategoria", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("sp_filtroCitta", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("sp_filtroData", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("sp_filtroHost", String.class, ParameterMode.IN);

        query.setParameter("sp_filtroCategoria", filtroCategoria);
        query.setParameter("sp_filtroCitta", citta);
        query.setParameter("sp_filtroData", filtroData != null ? java.sql.Date.valueOf(filtroData) : null);
        query.setParameter("sp_filtroHost", filtroHost);

        query.execute();

        List<Object[]> risultati = query.getResultList();
        List<EventiResponse> results = buildEventiResponseList(risultati);
        return results;
    }

}
