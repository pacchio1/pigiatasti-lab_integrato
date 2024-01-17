package it.Pigiatasti.SeVedemo.repository;

import it.Pigiatasti.SeVedemo.entity.Recensione;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecensioneRepository extends JpaRepository<Recensione, Integer> {

    @Transactional
    @Modifying
    @Query("INSERT INTO Recensione (id_utente, id_evento, voto, descrizione) VALUES (:idUtente, :idEvento, :voto, :descrizione)")
    void creaRecensione(@Param("idUtente") Integer idUtente, @Param("idEvento") Integer idEvento, @Param("voto") Integer voto, @Param("descrizione") String descrizione);

    @Query("SELECT r FROM Recensione r WHERE r.id_utente = :idUtente AND r.id_evento = :idEvento")
    Recensione isPresent(@Param("idEvento") int idEvento,@Param("idUtente") int idUtente);

}
