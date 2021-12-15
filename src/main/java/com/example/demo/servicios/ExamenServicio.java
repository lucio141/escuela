package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
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
    private final PreguntaServicio preguntaServicio;

    @Transactional
    public void crearExamen(String dificultad, Tematica tematica, Double notaRequerida, String nombre) {

        Examen examen = new Examen();
        examen.setDificultad(dificultad);
        examen.setNombre(nombre);
        examen.setTematica(tematica);
        examen.setNotaRequerida(notaRequerida);
        examenRepositorio.save(examen);

    }

    @Transactional
    public void modificarExamen(Integer id, String dificultad, Tematica tematica, Double notaRequerida, String nombre) throws ObjetoNulloExcepcion, ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {

        Examen examen = obtenerPorId(id);
        Examen examenAux = examen;

        examen.setNombre(nombre);
        examen.setDificultad(dificultad);
        examen.setTematica(tematica);
        examen.setNotaRequerida(notaRequerida);

        if(!examenAux.equals(examen)){
            if(mostrarExamenesPorAlta(true).contains(examen)) {
                throw new ObjetoRepetidoExcepcion("Se encontró una pregunta con el mismo enunciado");
            }else if(mostrarExamenesPorAlta(false).contains(examen)) {
                throw new ObjetoEliminadoExcepcion("Se encontró una pregunta eliminada con el mismo enunciado");
            }
        }

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
            throw new ObjetoNulloExcepcion("No se encontro el examen");
        }

        return examen;
    }

    @Transactional
    public Examen ObtenerUltimoExamen() throws ObjetoNulloExcepcion {
        Examen examen = examenRepositorio.mostrarUltimoExamen();

        if(examen == null){
            throw new ObjetoNulloExcepcion("No se encontro el examen");
        }

        return examen;
    }

    @Transactional
    public void eliminar(int id) throws ObjetoNulloExcepcion {
        Examen examen = obtenerPorId(id);

        for (Pregunta pregunta: examen.getPreguntas()) {
            if(pregunta.getAlta()){
                preguntaServicio.eliminar(pregunta.getId());
            }
        }

        examenRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        Examen examen = obtenerPorId(id);

        examenRepositorio.darAlta(id);
    }

    @Transactional(readOnly = true)
    public List<Resultado> top5 (int id){
        return examenRepositorio.top5(id);
    }
}
