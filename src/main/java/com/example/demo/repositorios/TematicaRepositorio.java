package com.example.demo.repositorios;

import com.example.demo.entidades.Tematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
Esta clase es la conexion a la base de datos.
 */


@Repository
public interface TematicaRepositorio extends JpaRepository<Tematica, Integer> {
    @Query("SELECT t FROM Tematica t WHERE t.alta = :alta")
    List<Tematica> mostrarPorAlta(@Param("alta") Boolean alta);

    @Query("UPDATE Tematica t SET t.alta = true WHERE t.id = :id")
    void darAlta(@Param("id") Integer id);
}
