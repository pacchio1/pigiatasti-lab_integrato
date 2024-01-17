package it.Pigiatasti.SeVedemo.repository;


import it.Pigiatasti.SeVedemo.entity.Partecipazione;
import it.Pigiatasti.SeVedemo.entity.Recensione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PartecipazioneRepository extends JpaRepository<Partecipazione, Integer> {
    Partecipazione save(Recensione recensione); //Crea e salva nuova partecipazione

    @Query("SELECT p FROM Partecipazione p " +
            "JOIN Evento e ON e.id_evento = p.evento.id_evento " +
            "WHERE e.data_fine < CURRENT_DATE AND p.id_utente.id_utente = :idUtente")
    List<Partecipazione> partecipazioniPassate(@Param("idUtente")int id);

    @Query("SELECT p FROM Partecipazione p " +
            "JOIN Evento e ON e.id_evento = p.evento.id_evento " +
            "WHERE e.data_fine > CURRENT_DATE AND p.id_utente.id_utente = :idUtente")
    List<Partecipazione> partecipazioniFuture(@Param("idUtente")int id);

    @Modifying
    @Query("INSERT INTO Partecipazione (id_utente, evento) VALUES (:idUtente, :idEvento)")
    void salva(@Param("idUtente") int idUtente, @Param("idEvento") int idEvento);


    @Query("SELECT p FROM Partecipazione p WHERE p.id_utente.id_utente = :idUtente AND p.evento.id_evento = :idEvento")
    Partecipazione PartecipazionePerId(@Param("idUtente") Integer idUtente,@Param("idEvento") Integer idEvento);
}
