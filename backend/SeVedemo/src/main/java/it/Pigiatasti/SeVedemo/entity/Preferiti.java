package it.Pigiatasti.SeVedemo.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "preferiti")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Preferiti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_utente")
    private Integer idUtente;

    @Column(name = "id_utente_preferito")
    private Integer idUtentePreferito;

    @ManyToOne
    @JoinColumn(name = "id_utente", insertable = false, updatable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_utente_preferito", insertable = false, updatable = false)
    private Utente utentePreferito;
}

