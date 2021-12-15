package com.example.demo.controladores;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.PreguntaServicio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("examen/{examenId}/pregunta")
public class PreguntaControlador{

    private final PreguntaServicio preguntaServicio;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarPreguntas(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("pregunta");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);

        try{
            mav.addObject("examen" , map.get("examen"));
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        mav.addObject("preguntasValidas", preguntaServicio.mostrarPreguntasPorAlta(true));
        mav.addObject("preguntasEliminadas", preguntaServicio.mostrarPreguntasPorAlta(false));
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearPregunta(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("pregunta-formulario");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        try{
            mav.addObject("examen" , map.get("examen"));
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        mav.addObject("pregunta", new Pregunta());
        mav.addObject("titulo", "Crear Pregunta");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardarPregunta(@RequestParam Dificultad dificultad, @RequestParam String enunciado, @RequestParam List<String> respuestas, @RequestParam String respuestaCorrecta, @RequestParam int puntaje, @RequestParam Examen examen, HttpServletRequest request) {
        preguntaServicio.crearPregunta(dificultad, enunciado, respuestas, respuestaCorrecta, puntaje, examen);
        return new RedirectView("/pregunta");
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarPregunta(@PathVariable int id) {
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
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam Dificultad dificultad, @RequestParam String enunciado, @RequestParam List<String> respuestas, @RequestParam String respuestaCorrecta, @RequestParam int puntaje, @RequestParam Examen examen, @RequestParam int id) {
        try{
            preguntaServicio.modificarPregunta(dificultad, enunciado, respuestas, respuestaCorrecta, puntaje, examen, id);
        }catch(ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/pregunta");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarPregunta(@PathVariable int id) {
        preguntaServicio.eliminar(id);
        return new RedirectView("/pregunta");
    }
}
