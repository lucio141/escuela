package com.example.demo.repositorios;

import com.example.demo.entidades.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenRepositorio extends JpaRepository<Examen, Integer>{

}
