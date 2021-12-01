package com.example.demo.entidades;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@SQLDelete(sql="UPDATE Pregunta p SET p.alta = false WHERE p.id = ?")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Examen examen;
    @Column(nullable = false)
    private String dificultad;
    @Column(nullable = false)
    private String enunciado;
    @ElementCollection(targetClass=String.class)
    private List<String> resupestas;
    @Column(nullable = false)
    private String respuestaCorrecta;
    private Boolean alta;
    private Integer puntaje;

    public Pregunta(Integer id, Examen examen, String dificultad, String enunciado, List<String> resupestas, String respuestaCorrecta, Boolean alta, Integer puntaje) {
        this.id = id;
        this.examen = examen;
        this.dificultad = dificultad;
        this.enunciado = enunciado;
        this.resupestas = resupestas;
        this.respuestaCorrecta = respuestaCorrecta;
        this.alta = true;
        this.puntaje = puntaje;
    }

    public Pregunta() {
        this.alta = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<String> getResupestas() {
        return resupestas;
    }

    public void setResupestas(List<String> resupestas) {
        this.resupestas = resupestas;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }
}
