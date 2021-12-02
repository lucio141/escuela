package com.example.demo.repositorios;

import com.example.demo.entidades.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepositorio extends JpaRepository<Examen, Integer>{

    @Modifying
    @Query("UPDATE Examen e SET e.alta= true WHERE e.id = :id")
    void recuperarExamen(@Param("id") Integer id);

    @Query("from Examen e WHERE e.alta = :alta")
    List<Examen> mostrarExamenes(@Param("alta")boolean Alta);
}
