package com.example.demo.controladores;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.PreguntaServicio;
import com.example.demo.utilidades.Dificultad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/pregunta")
public class PreguntaControlador{

    @Autowired
    PreguntaServicio preguntaServicio = new PreguntaServicio();

    @GetMapping
    public ModelAndView mostrarPreguntas() {
        ModelAndView mav = new ModelAndView("pregunta");
        mav.addObject("preguntasValidas", preguntaServicio.mostrarPreguntasPorAlta(true));
        mav.addObject("preguntasEliminadas", preguntaServicio.mostrarPreguntasPorAlta(false));
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearPregunta() {
        ModelAndView mav = new ModelAndView("pregunta-formulario");
        mav.addObject("pregunta", new Pregunta());
        //mav.addObject("examenes" , examenServicio.mostrarExamenes()); POR AGREGAR
        mav.addObject("titulo", "Crear Pregunta");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarPregunta(@RequestParam Dificultad dificultad, @RequestParam String enunciado, @RequestParam List<String> respuestas, @RequestParam String respuestaCorrecta, @RequestParam Integer puntaje, @RequestParam Examen examen) {
        preguntaServicio.crearPregunta(dificultad, enunciado, respuestas, respuestaCorrecta, puntaje, examen);
        return new RedirectView("/pregunta");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarPregunta(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("pregunta-formulario");
        try {
            mav.addObject("pregunta", preguntaServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        mav.addObject("titulo", "Editar Pregunta");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificarPregunta(@RequestParam Dificultad dificultad, @RequestParam String enunciado, @RequestParam List<String> respuestas, @RequestParam String respuestaCorrecta, @RequestParam Integer puntaje, @RequestParam Examen examen, @RequestParam Integer id) {
        try{
            preguntaServicio.modificarPregunta(dificultad, enunciado, respuestas, respuestaCorrecta, puntaje, examen, id);
        }catch(ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/pregunta");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarPregunta(@PathVariable Integer id) {
        preguntaServicio.eliminar(id);
        return new RedirectView("/pregunta");
    }
}
