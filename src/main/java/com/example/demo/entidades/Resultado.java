package com.example.demo.entidades;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@SQLDelete(sql="UPDATE Resultado r SET r.alta = false WHERE r.id = ?")
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    private Examen examen;
    @ManyToOne
    private Usuario usuario;
    private Short respuestasCorrectas;
    private Short respuestasIncorrectas;
    private Long duracion;
    private Integer puntajeFinal;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Date tiempoInicio;
    @LastModifiedDate
    private Date tiempoFinalizacion;
    private Boolean alta;
    private Boolean aprobado;

    public Resultado(Integer id, Examen examen, Usuario usuario, Short respuestasCorrectas, Short respuestasIncorrectas, Long duracion, Integer puntajeFinal, Date tiempoInicio, Date tiempoFinalizacion) {
        Id = id;
        this.examen = examen;
        this.usuario = usuario;
        this.respuestasCorrectas = respuestasCorrectas;
        this.respuestasIncorrectas = respuestasIncorrectas;
        this.duracion = duracion;
        this.puntajeFinal = puntajeFinal;
        this.tiempoInicio = tiempoInicio;
        this.tiempoFinalizacion = tiempoFinalizacion;
        this.alta = true;
        this.aprobado = false;
    }

    public Resultado() {
        this.alta = true;
        this.aprobado = false;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Short getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public void setRespuestasCorrectas(Short respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    public Short getRespuestasIncorrectas() {
        return respuestasIncorrectas;
    }

    public void setRespuestasIncorrectas(Short respuestasIncorrectas) {
        this.respuestasIncorrectas = respuestasIncorrectas;
    }

    public Long getDuracion() {
        return duracion;
    }

    public void setDuracion(Long duracion) {
        this.duracion = duracion;
    }

    public Integer getPuntajeFinal() {
        return puntajeFinal;
    }

    public void setPuntajeFinal(Integer puntajeFinal) {
        this.puntajeFinal = puntajeFinal;
    }

    public Date getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(Date tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public Date getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public void setTiempoFinalizacion(Date tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }
}
