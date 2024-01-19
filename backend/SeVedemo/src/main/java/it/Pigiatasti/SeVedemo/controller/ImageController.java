package it.Pigiatasti.SeVedemo.controller;

import it.Pigiatasti.SeVedemo.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/images")
@RestController
public class ImageController {

    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image") MultipartFile file,
            @RequestParam("id_utente") Integer id_utente) throws IOException {
        String uploadImage = service.uploadImageToFileSystem(file, id_utente);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);

    }

    @GetMapping("/download/{id_utente}")
    public ResponseEntity<?> downloadImageToFileSystem(@PathVariable Integer id_utente) throws IOException {
        byte[] imageData = service.downloadImageFromFileSystem(id_utente);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @DeleteMapping("delete/{id_utente}")
    public void deleteImagine(@PathVariable Integer id_utente) {
        service.eliminaFoto(id_utente);
    }
}
