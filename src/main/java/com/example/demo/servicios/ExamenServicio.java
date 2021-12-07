package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ExamenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Service
public class ExamenServicio {

    @Autowired
    private ExamenRepositorio examenRepositorio;

    @Transactional
    public void crearExamen(String dificultad, Tematica tematica, List<Pregunta> preguntas, Double notaRequerida) {

        Examen examen = new Examen();

        examen.setDificultad(dificultad);
        examen.setTematica(tematica);
        examen.setPreguntas(preguntas);
        examen.setNotaRequerida(notaRequerida);

        examenRepositorio.save(examen);
    }

    @Transactional
    public void modificarExamen(Integer id, String dificultad, Tematica tematica, List<Pregunta> preguntas, Double notaRequerida) throws ObjetoNulloExcepcion {

        Examen examen = examenRepositorio.findById(id).orElse(null);

        if(examen == null){
            throw new ObjetoNulloExcepcion("");
        }

        examen.setDificultad(dificultad);
        examen.setTematica(tematica);
        examen.setPreguntas(preguntas);
        examen.setNotaRequerida(notaRequerida);

        examenRepositorio.save(examen);
    }

    @Transactional(readOnly = true)
    public List<Examen> obtenerExamenes(Boolean alta) {
        return examenRepositorio.mostrarExamenes(alta);
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
    public void eliminarExamen(int id) {
        examenRepositorio.deleteById(id);
    }

    @Transactional
    public void recuperarExamen(int id) throws ObjetoNulloExcepcion {
        Examen examen = examenRepositorio.findById(id).orElse(null);

        if(examen==null){
            throw new ObjetoNulloExcepcion("");
        }

        examenRepositorio.recuperarExamen(id);
    }
}
