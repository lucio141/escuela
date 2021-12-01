package com.example.demo.repositorios;

import com.example.demo.entidades.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepositorio extends JpaRepository<Resultado, Integer> {
}
