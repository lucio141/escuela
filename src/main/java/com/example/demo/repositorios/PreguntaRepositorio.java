package com.example.demo.repositorios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepositorio extends JpaRepository<Pregunta, Integer>{

    @Query("SELECT p FROM Pregunta p WHERE p.alta = :alta")
    List<Pregunta> mostrarPreguntasFiltradas(@Param("alta") boolean alta);

    @Modifying
    @Query("UPDATE Pregunta p SET p.alta = true WHERE p.id = :id")
    void darAltaPregunta(@Param("id") Integer id);
}