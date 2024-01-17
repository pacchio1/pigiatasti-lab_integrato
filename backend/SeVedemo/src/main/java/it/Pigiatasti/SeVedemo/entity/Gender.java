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
@Table(name = "gender")
@Getter @Setter @EqualsAndHashCode @NoArgsConstructor
public class Gender {

    @Id
    @Column(name = "id_gender")
    @EqualsAndHashCode.Include
    private int id_gender;
    public Gender(int id) {
        this.id_gender = id;
    }
    @Column(name = "sesso")
    private String Sesso;
}
