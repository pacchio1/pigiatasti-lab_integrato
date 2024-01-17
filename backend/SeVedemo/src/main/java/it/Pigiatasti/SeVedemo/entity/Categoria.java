package it.Pigiatasti.SeVedemo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria")
@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
public class Categoria {
    @Id
    @Column(name = "id_categoria")
    private int id_categoria;

    @Column(name = "categoria")
    private String categoria;

}
