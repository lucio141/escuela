package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Usuario u SET u.alta = false WHERE u.id = ?")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Rol rol;
    @Column(nullable = false, unique = true)
    private String mail;
    @Column(nullable = false , unique=true)
    private String nombreUsuario;
    private String apellidoUsuario;
    private Integer edad;
    @Column(nullable = false)
    private String contrasenia;
    @OneToMany(mappedBy = "usuario")
    private List<Resultado> resultados;

    @CreatedDate
    @Column(nullable = false , updatable = false)
    private Date fechaCreacion;
    @LastModifiedDate
    private Date fechaModificacion;
    private Boolean alta;

    public Usuario() {
        this.alta = true;
    }

    public Usuario(Integer id, Rol rol, String mail, String nombreUsuario, String contrasenia, List<Resultado> resultados, Date fechaCreacion, Date fechaModificacion, String apellidoUsuario, Integer edad) {
        this.id = id;
        this.rol = rol;
        this.mail = mail;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.resultados = resultados;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.alta = true;
        this.apellidoUsuario = apellidoUsuario;
        this.edad = edad;
    }
}
