package com.example.demo.entidades;


/*
    Clase que representa la plantilla de datos con que se va manejar
    toda esta la seccion
 */
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Tematica t SET t.alta = false WHERE t.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Tematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "tematica")
    private List<Examen> examen;

    public Tematica() {
        this.alta = true;
    }

    public Tematica(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.alta = true;
    }
}
