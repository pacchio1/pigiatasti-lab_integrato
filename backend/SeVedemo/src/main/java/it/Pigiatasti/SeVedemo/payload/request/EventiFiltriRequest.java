package it.Pigiatasti.SeVedemo.payload.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventiFiltriRequest {
    private String citta;
    private String categoria;
    private String host;
    private LocalDate data;
}
