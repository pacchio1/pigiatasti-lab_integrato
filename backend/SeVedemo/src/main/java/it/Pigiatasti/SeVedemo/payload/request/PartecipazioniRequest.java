package it.Pigiatasti.SeVedemo.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartecipazioniRequest {
    private Integer idUtente;
    private Integer idEvento;
}
