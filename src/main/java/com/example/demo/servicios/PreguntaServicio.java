package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.PreguntaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PreguntaServicio {

    @Autowired
    private PreguntaRepositorio preguntaRepositorio;

    @Transactional
    public List<Pregunta> mostrarPreguntas() {
        return preguntaRepositorio.findAll();
    }

    @Transactional
    public List<Pregunta> mostrarPreguntasFiltradas(Boolean alta) {
        return  preguntaRepositorio.mostrarPreguntasFiltradas(alta);
    }

    @Transactional
    public Pregunta buscarPregunta(Integer id) {
        return preguntaRepositorio.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarPregunta(Integer id) {
        preguntaRepositorio.deleteById(id);
    }

    @Transactional
    public void modificarPregunta(String dificultad, String enunciado, List<String> respuestas, String respuestaCorrecta, Integer puntaje, Examen examen, Integer id) throws ObjetoNulloExcepcion {
        Pregunta pregunta = preguntaRepositorio.findById(id).orElse(null);
        if (pregunta == null) {
            throw new ObjetoNulloExcepcion("");
        }
        pregunta.setDificultad(dificultad);
        pregunta.setEnunciado(enunciado);
        pregunta.setResupestas(respuestas);
        pregunta.setRespuestaCorrecta(respuestaCorrecta);
        pregunta.setPuntaje(puntaje);
        pregunta.setExamen(examen);
        preguntaRepositorio.save(pregunta);
    }

    @Transactional
    public void crearPregunta(String dificultad, String enunciado, List<String> respuestas, String respuestaCorrecta, Integer puntaje, Examen examen) {
        Pregunta pregunta = new Pregunta();
        pregunta.setDificultad(dificultad);
        pregunta.setEnunciado(enunciado);
        pregunta.setResupestas(respuestas);
        pregunta.setRespuestaCorrecta(respuestaCorrecta);
        pregunta.setPuntaje(puntaje);
        pregunta.setExamen(examen);
        preguntaRepositorio.save(pregunta);
    }
}
