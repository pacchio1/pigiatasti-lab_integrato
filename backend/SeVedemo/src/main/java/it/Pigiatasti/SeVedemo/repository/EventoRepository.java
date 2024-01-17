package it.Pigiatasti.SeVedemo.repository;

import it.Pigiatasti.SeVedemo.entity.Evento;
import it.Pigiatasti.SeVedemo.payload.request.CreaEventoRequest;
import it.Pigiatasti.SeVedemo.payload.response.EventiResponse;
import it.Pigiatasti.SeVedemo.payload.response.UtentiEmailResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    @Procedure(name = "sp_GetEventi")  // visualizza tutti gli eventi senza filtri
    List<EventiResponse> visualizzaEventi();


    @Query("SELECT DISTINCT new it.Pigiatasti.SeVedemo.payload.response.UtentiEmailResponse(u.email, u.nome, u.cognome) FROM Utente u " +
            "JOIN Partecipazione p ON p.id_utente.id_utente = u.id_utente " +
            "JOIN Evento e ON p.evento.id_evento = e.id_evento " +
            "WHERE e.id_evento = :idEvento")
    List<UtentiEmailResponse> getPartecipanti(@Param("idEvento") int id_evento);


    Evento save(Evento evento);

}