package com.example.demo.entidades;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@SQLDelete(sql = "UPDATE Tematica t SET t.alta = false WHERE t.id = ?"
)
public class Tematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "tematica")
    private List<Examen> examen;

// CONSTRUCTORS


    public Tematica() {
        this.alta = true;
    }

    public Tematica(Integer id, String nombre, Boolean alta, List<Examen> examen) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
        this.examen = examen;
    }

    // GETTERS & SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public List<Examen> getExamen() {
        return examen;
    }

    public void setExamen(List<Examen> examen) {
        this.examen = examen;
    }
}
