package it.Pigiatasti.SeVedemo.repository;

import it.Pigiatasti.SeVedemo.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FileDataRepository extends JpaRepository<FileData, Integer> {
    Optional<FileData> findByName(String fileName);

    @Query("SELECT d FROM FileData d WHERE d.id_utente = :idUtente")
    Optional<FileData> findByIdUtente(@Param("idUtente") Integer idUtente);

}
