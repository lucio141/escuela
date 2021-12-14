package com.example.demo.entidades;

import com.example.demo.utilidades.Dificultad;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Examen e SET e.alta = false WHERE e.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dificultad;
    private String nombre;
    @ManyToOne
    private Tematica tematica;
    @OneToMany(mappedBy = "examen")
    private List<Resultado> resultados;
    @OneToMany(mappedBy = "examen")
    private List<Pregunta> preguntas;
    private Double notaRequerida;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date fechaCreacion;
    @LastModifiedDate
    private Date fechaModificacion;
    private Boolean alta;

    public Examen(Integer id, String dificultad, Tematica tematica, Double notaRequerida) {
        this.id = id;
        this.dificultad = dificultad;
        this.tematica = tematica;
        this.notaRequerida = notaRequerida;
        this.alta = true;
    }

    public Examen() {
        this.alta = true;
    }
}
