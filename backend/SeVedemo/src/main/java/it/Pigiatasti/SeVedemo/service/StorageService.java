package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.entity.FileData;
import it.Pigiatasti.SeVedemo.repository.FileDataRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private EntityManager entityManager;

    //os.environ.get("SEVEDEMO_UPLOAD_PATH"); per variabili d'ambiente
    private final String Folder_Path = "C:/Foto-Profili/";
    public String uploadImageToFileSystem(MultipartFile file, Integer id_utente) throws IOException {
        String filePath = Folder_Path+file.getOriginalFilename();
        FileData  fileData = fileDataRepository.save(FileData.builder()
                .id_utente(id_utente)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));

        if(fileData != null){
            return "il file è stato salvato correttamente: " +file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(Integer id_utente) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByIdUtente(id_utente);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    public void eliminaFoto(Integer id_utente) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_EliminaFotoByIdUtente");
        query.registerStoredProcedureParameter("p_IdUtente", Integer.class, ParameterMode.IN);
        query.setParameter("p_IdUtente", id_utente);
        query.execute();
    }

}
