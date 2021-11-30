package com.example.demo.entidades;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false , unique=true)
    private String nombreUsuario;

    @Column(nullable = false)
    private String contrasenia;

    private List<Resultado> resultados;

    @CreatedDate
    @Column(nullable = false , updatable = false)
    private Date fechaCreacion;

    @LastModifiedDate
    private Date fechaModificacion;

    private Boolean alta;

    public Integer getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public String getMail() {
        return mail;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public List<Resultado> getResultados() {
        return resultados;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Usuario() {
    }

    public Usuario(Integer id, Rol rol, String mail, String nombreUsuario, String contrasenia, List<Resultado> resultados, Date fechaCreacion, Date fechaModificacion) {
        this.id = id;
        this.rol = rol;
        this.mail = mail;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.resultados = resultados;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.alta = true;
    }

}
