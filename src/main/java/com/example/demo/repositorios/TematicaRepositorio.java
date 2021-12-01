package com.example.demo.repositorios;

import com.example.demo.entidades.Tematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TematicaRepositorio extends JpaRepository<Tematica, Integer> {
}
