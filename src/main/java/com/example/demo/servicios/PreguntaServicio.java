package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.repositorios.ExamenRepositorio;
import com.example.demo.repositorios.PreguntaRepositorio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
public class PreguntaServicio {

    private final PreguntaRepositorio preguntaRepositorio;

    private final ExamenServicio examenservicio;

    @Transactional
    public void crearPregunta(Dificultad dificultad, String enunciado, List<String> respuestas, String respuestaCorrecta, Integer puntaje, Examen examen) throws ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion, ObjetoNulloExcepcion {

        Pregunta pregunta = new Pregunta();

        pregunta.setDificultad(dificultad);
        pregunta.setEnunciado(enunciado);
        pregunta.setResupestas(respuestas);
        pregunta.setRespuestaCorrecta(respuestaCorrecta);
        pregunta.setPuntaje(puntaje);
        try {
            pregunta.setExamen(examenservicio.obtenerPorId(examen.getId()));
        } catch (ObjetoNulloExcepcion e) {
            throw new ObjetoNulloExcepcion("Error al buscar el Examen");
        }

        if(mostrarPreguntasPorAlta(true).contains(pregunta)) {
            throw new ObjetoRepetidoExcepcion("Se encontró una pregunta con el mismo enunciado");
        }else if(mostrarPreguntasPorAlta(false).contains(pregunta)) {
            throw new ObjetoEliminadoExcepcion("Se encontró una pregunta eliminada con el mismo enunciado");
        }

        preguntaRepositorio.save(pregunta);
    }

    @Transactional
    public void modificarPregunta(Dificultad dificultad, String enunciado, List<String> respuestas, String respuestaCorrecta, Integer puntaje, Examen examen, Integer id) throws ObjetoNulloExcepcion, ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        Pregunta pregunta = obtenerPorId(id);
        Pregunta preguntaAux = pregunta;

        pregunta.setDificultad(dificultad);
        pregunta.setEnunciado(enunciado);
        pregunta.setResupestas(respuestas);
        pregunta.setRespuestaCorrecta(respuestaCorrecta);
        pregunta.setPuntaje(puntaje);
        pregunta.setExamen(examen);

        if(!preguntaAux.equals(pregunta)){
            if(mostrarPreguntasPorAlta(true).contains(pregunta)) {
                throw new ObjetoRepetidoExcepcion("Se encontró una pregunta con el mismo enunciado");
            }else if(mostrarPreguntasPorAlta(false).contains(pregunta)) {
                throw new ObjetoEliminadoExcepcion("Se encontró una pregunta eliminada con el mismo enunciado");
            }
        }

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
