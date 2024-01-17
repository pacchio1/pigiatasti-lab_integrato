package it.Pigiatasti.SeVedemo.auth;

import it.Pigiatasti.SeVedemo.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String ruolo;
    private String presentazione;
    private Gender id_gender;
    private String telefono;
}
