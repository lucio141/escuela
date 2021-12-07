package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql="UPDATE Rol r SET r.alta = false WHERE r.id = ?")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    public Rol(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.alta = true;
    }

    public Rol() {
        this.alta = true;
    }
}
