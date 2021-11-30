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
    @Column(nullable = false)
    private Tematica tematica;
    @Column(nullable = false)
    private Byte dificultad;
    @Column(nullable = false)
    private String enunciado;
    @Column(nullable = false)
    private List<String> resupestas;
    @Column(nullable = false)
    private String respuestaCorrecta;
    private Boolean alta;

    public Pregunta(Integer id, Tematica tematica, Byte dificultad, String enunciado, List<String> resupestas, String respuestaCorrecta) {
        this.id = id;
        this.tematica = tematica;
        this.dificultad = dificultad;
        this.enunciado = enunciado;
        this.resupestas = resupestas;
        this.respuestaCorrecta = respuestaCorrecta;
        this.alta = true;
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

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }

    public Byte getDificultad() {
        return dificultad;
    }

    public void setDificultad(Byte dificultad) {
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
}
