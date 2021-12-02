package com.example.demo.repositorios;

import com.example.demo.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {

    @Modifying
    @Query("UPDATE Rol r SET r.alta= true WHERE r.id = :id")
    void recuperarRol(@Param("id") Integer id);

    @Query("from Rol r WHERE r.alta = :alta")
    List<Rol> mostrarRoles(@Param("alta")boolean Alta);
}
