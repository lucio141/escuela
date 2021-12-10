package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "categoria")
    private List<Tematica> tematicas;

    public Categoria(Integer id, String nombre, List<Tematica> tematicas) {
        this.id = id;
        this.nombre = nombre;
        this.alta = true;
        this.tematicas = tematicas;
    }

    public Categoria() {
        this.alta = true;
    }
}
