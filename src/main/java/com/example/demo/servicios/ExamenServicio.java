package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ExamenRepositorio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
public class ExamenServicio {

    private final ExamenRepositorio examenRepositorio;

    @Transactional
    public void crearExamen(Dificultad dificultad, Tematica tematica, Double notaRequerida) {

        Examen examen = new Examen();

        examen.setDificultad(dificultad);
        examen.setTematica(tematica);
        examen.setNotaRequerida(notaRequerida);

        examenRepositorio.save(examen);
    }

    @Transactional
    public void modificarExamen(Integer id, Dificultad dificultad, Tematica tematica, Double notaRequerida) throws ObjetoNulloExcepcion {

        Examen examen = obtenerPorId(id);

        examen.setDificultad(dificultad);
        examen.setTematica(tematica);
        examen.setNotaRequerida(notaRequerida);

        examenRepositorio.save(examen);
    }

    @Transactional
    public List<Examen> mostrarExamenes() {
        return examenRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Examen> mostrarExamenesPorAlta(Boolean alta) {
        return examenRepositorio.mostrarPorAlta(alta);
    }

    @Transactional
    public Examen obtenerPorId(int id) throws ObjetoNulloExcepcion {
        Examen examen = examenRepositorio.findById(id).orElse(null);

        if (examen == null) {
            throw new ObjetoNulloExcepcion("");
        }

        return examen;
    }

    @Transactional
    public void eliminar(int id) {
        examenRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        Examen examen = obtenerPorId(id);

        examenRepositorio.darAlta(id);
    }
}
