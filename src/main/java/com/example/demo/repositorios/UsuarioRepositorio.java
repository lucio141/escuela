package com.example.demo.repositorios;

import com.example.demo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.alta = :alta")
    List<Usuario> mostrarUsuarios(@Param("alta") Boolean alta);

    @Modifying
    @Query("UPDATE Usuario u SET u.alta = true WHERE u.id = :id")
    void darAlta (@Param("id") Integer id);
}
