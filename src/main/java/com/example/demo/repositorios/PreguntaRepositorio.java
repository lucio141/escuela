package com.example.demo.repositorios;

import com.example.demo.entidades.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepositorio extends JpaRepository<Pregunta, Integer>{

    @Query("SELECT p FROM Pregunta p WHERE alta = true")
    List<Pregunta> mostrarPreguntas();

    @Query("SELECT p FROM Pregunta p WHERE alta = false")
    List<Pregunta> mostrarPreguntasEliminadas();

    @Modifying
    @Query("UPDATE Pregunta p SET p.dificultad = :dificultad , p.enunciado = :enunciado , p.respuestas = :respuestas , p.respuestaCorrecta = :respuestaCorrecta , p.puntaje = :puntaje WHERE p.id = :id")
    void modificar(@Param("dificultad") Byte dificultad, @Param("enunciado") String enunciado, @Param("respuestas") List<String> respuestas, @Param("respuestaCorrecta") String respuestaCorrecta, @Param("puntaje") Integer puntaje, @Param("id") Integer id );

}