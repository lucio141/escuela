package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.PreguntaRepositorio;
import com.example.demo.utilidades.Dificultad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class PreguntaServicio {

    @Autowired
    private PreguntaRepositorio preguntaRepositorio;

    @Transactional
    public void crearPregunta(Dificultad dificultad, String enunciado, List<String> respuestas, String respuestaCorrecta, Integer puntaje, Examen examen) {
        Pregunta pregunta = new Pregunta();
        pregunta.setDificultad(dificultad);
        pregunta.setEnunciado(enunciado);
        pregunta.setResupestas(respuestas);
        pregunta.setRespuestaCorrecta(respuestaCorrecta);
        pregunta.setPuntaje(puntaje);
        pregunta.setExamen(examen);
        preguntaRepositorio.save(pregunta);
    }

    @Transactional
    public void modificarPregunta(Dificultad dificultad, String enunciado, List<String> respuestas, String respuestaCorrecta, Integer puntaje, Examen examen, Integer id) throws ObjetoNulloExcepcion {
        Pregunta pregunta = obtenerPorId(id);

        pregunta.setDificultad(dificultad);
        pregunta.setEnunciado(enunciado);
        pregunta.setResupestas(respuestas);
        pregunta.setRespuestaCorrecta(respuestaCorrecta);
        pregunta.setPuntaje(puntaje);
        pregunta.setExamen(examen);
        preguntaRepositorio.save(pregunta);
    }

    @Transactional
    public List<Pregunta> mostrarPreguntas() {
        return preguntaRepositorio.findAll();
    }

    @Transactional
    public List<Pregunta> mostrarPreguntasPorAlta(Boolean alta) {
        return  preguntaRepositorio.mostrarPorAlta(alta);
    }

    @Transactional
    public Pregunta obtenerPorId(Integer id) throws ObjetoNulloExcepcion {
        Pregunta pregunta = preguntaRepositorio.findById(id).orElse(null);

        if (pregunta == null) {
            throw new ObjetoNulloExcepcion("");
        }

        return pregunta;
    }

    @Transactional
    public void eliminar(Integer id) {
        preguntaRepositorio.deleteById(id);
    }




    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        Pregunta pregunta = obtenerPorId(id);

        preguntaRepositorio.darAlta(id);
    }
}
