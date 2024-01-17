package it.Pigiatasti.SeVedemo.service;

import it.Pigiatasti.SeVedemo.auth.AuthenticatioRequest;
import it.Pigiatasti.SeVedemo.auth.AuthenticationResponse;
import it.Pigiatasti.SeVedemo.auth.RegisterRequest;
import it.Pigiatasti.SeVedemo.entity.FileData;
import it.Pigiatasti.SeVedemo.entity.Ruolo;
import it.Pigiatasti.SeVedemo.entity.Utente;
import it.Pigiatasti.SeVedemo.repository.FileDataRepository;
import it.Pigiatasti.SeVedemo.repository.UtenteRepository;
import it.Pigiatasti.SeVedemo.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UtenteRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StorageService storageService;
    public AuthenticationResponse register(RegisterRequest request) throws IOException {
        var user = Utente.builder()
                .email(request.getEmail())
                .nome(request.getNome())
                .cognome(request.getCognome())
                .telefono(request.getTelefono())
                .password(passwordEncoder.encode(request.getPassword()))
                .id_gender(request.getId_gender())
                .presentazione(request.getPresentazione())
                .ruolo(Ruolo.utente)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticatioRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
