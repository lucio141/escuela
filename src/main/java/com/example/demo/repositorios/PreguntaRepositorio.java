package com.example.demo.repositorios;

import com.example.demo.entidades.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRepositorio extends JpaRepository<Pregunta, Integer>{
}