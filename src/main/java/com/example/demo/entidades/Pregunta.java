package com.example.demo.entidades;

import com.example.demo.utilidades.Dificultad;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql="UPDATE Pregunta p SET p.alta = false WHERE p.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Pregunta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Examen examen;
    @Column(nullable = false)
    private Dificultad dificultad;
    @Column(nullable = false)
    private String enunciado;
    @ElementCollection(targetClass=String.class)
    private List<String> resupestas;
    @Column(nullable = false)
    private String respuestaCorrecta;
    private Boolean alta;
    private Integer puntaje;

    public Pregunta(Integer id, Examen examen, Dificultad dificultad, String enunciado, String respuestaCorrecta, Integer puntaje) {
        this.id = id;
        this.examen = examen;
        this.dificultad = dificultad;
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.alta = true;
        this.puntaje = puntaje;
    }

    public Pregunta() {
        this.alta = true;
    }
}
