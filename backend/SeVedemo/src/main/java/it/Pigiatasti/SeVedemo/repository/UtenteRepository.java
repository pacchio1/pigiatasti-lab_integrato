package it.Pigiatasti.SeVedemo.repository;

import it.Pigiatasti.SeVedemo.entity.Recensione;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.payload.response.EventiUtenteResponse;
import it.Pigiatasti.SeVedemo.payload.response.RecensioniUtenteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    Utente save(Utente utente); // Salva un utente nel database Create/Update

    void deleteByEmail(String email); // Cancella un utente per mail Delete

    List<Utente> findAll(); // Restituisce tutti gli utenti nel database

    @Query("SELECT u FROM Utente u WHERE u.id_utente = :IdUtente")
    Optional<Utente> findByIdUtente(@Param("IdUtente") int IdUtente); // Trova un utente per email

    @Query(value = "CALL sp_GetListaEventiUtente(:email)", nativeQuery = true) // Sp per lista eventi di un utente
    List<EventiUtenteResponse> eventiUtente (@Param("email") String email);

    @Procedure(name = "sp_DeleteEventoByIdUtente", outputParameterName = "p_IsDeleted") // Sp per eliminare un evento
    boolean eliminaEvento(@Param("p_Id") int id);

    void save(Recensione recensione); // Sp per creare una recensione

    Optional<Utente> findByEmail(String email);


}
