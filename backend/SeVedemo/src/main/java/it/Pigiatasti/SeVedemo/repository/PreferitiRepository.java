package it.Pigiatasti.SeVedemo.repository;


import it.Pigiatasti.SeVedemo.entity.Preferiti;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferitiRepository extends JpaRepository<Preferiti, Integer> {

    @Transactional
    @Modifying
    @Query("INSERT INTO Preferiti (idUtente, idUtentePreferito) VALUES (:idUtente, :UtentePreferito)")
    void salvaPreferito(@Param("idUtente") Integer idUtente, @Param("UtentePreferito") Integer UtentePreferito);

    @Query("SELECT p FROM Preferiti p WHERE p.idUtente = :id")
    List<Preferiti> getTuttiPreferiti(@Param("id") int id);

    @Query("SELECT p FROM Preferiti p WHERE p.idUtente = :idUtente AND p.idUtentePreferito = :idFollower")
    Preferiti isFollowing(@Param("idUtente") int idUtente,@Param("idFollower") int idFollower);
}
