package com.example.demo.entidades;

import javax.persistence.*;

@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    private Examen examen;
    @ManyToOne
    private Usuario usuario;
}
