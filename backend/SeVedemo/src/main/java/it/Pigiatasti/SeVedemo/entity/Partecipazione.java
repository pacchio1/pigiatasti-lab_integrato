package it.Pigiatasti.SeVedemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partecipazione")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@AllArgsConstructor
public class Partecipazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_partecipazione;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente id_utente;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento; // Aggiunta della proprietà "evento"
}
