package com.example.demo.entidades;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE Examen e SET e.alta = false WHERE e.id = ?")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String dificultad;
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

    public Examen(Integer id, String dificultad, Tematica tematica, List<Resultado> resultados, List<Pregunta> preguntas, Double notaRequerida, Date fechaCreacion, Date fechaModificacion, Boolean alta) {
        this.id = id;
        this.dificultad = dificultad;
        this.tematica = tematica;
        this.resultados = resultados;
        this.preguntas = preguntas;
        this.notaRequerida = notaRequerida;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.alta = true;
    }

    public Examen() {
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

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Double getNotaRequerida() {
        return notaRequerida;
    }

    public void setNotaRequerida(Double notaRequerida) {
        this.notaRequerida = notaRequerida;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }
}
