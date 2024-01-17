package it.Pigiatasti.SeVedemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento")
@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_evento;

    @Column(name = "id_utente")
    private int id_utente;

    @Column(name = "data_inizio")
    private LocalDateTime data_inizio;

    @Column(name = "data_fine")
    private LocalDateTime data_fine;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "n_max_partecipanti")
    private int n_max_partecipanti;

    @Column(name = "posizione")
    private String posizione;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}

