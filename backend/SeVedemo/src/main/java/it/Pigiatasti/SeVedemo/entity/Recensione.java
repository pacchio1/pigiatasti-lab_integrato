package it.Pigiatasti.SeVedemo.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recensione")
@NoArgsConstructor @Getter @Setter @EqualsAndHashCode
public class Recensione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recensione")
    private int id_recensione;

    @Column(name = "id_utente")
    private Integer id_utente;

    @Column(name = "id_evento")
    private int id_evento;

    @Column(name = "voto")
    private int voto;

    @Column(name = "descrizione")
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "id_utente", insertable = false, updatable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_evento", insertable = false, updatable = false)
    private Evento evento;

}
