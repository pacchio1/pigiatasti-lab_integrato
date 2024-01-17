package it.Pigiatasti.SeVedemo.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtentiEmailResponse {
    private String email;
    private String nome;
    private String cognome;
}
