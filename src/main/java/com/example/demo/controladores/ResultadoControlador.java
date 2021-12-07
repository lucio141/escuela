package com.example.demo.controladores;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.PreguntaServicio;
import com.example.demo.servicios.ResultadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/resultado")
public class ResultadoControlador {

    @Autowired
    ResultadoServicio resultadoServicio = new ResultadoServicio();

    @GetMapping
    public ModelAndView mostrarResultado() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultados", resultadoServicio.obtenerResultado(true));
        return mav;
    }

    @GetMapping("/eliminados")
    public ModelAndView mostrarResultadoeliminados() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultados", resultadoServicio.obtenerResultado(false));
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearResultado() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultado", new Resultado());
        //mav.addObject("examenes" , examenServicio.mostrarExamenes()); POR AGREGAR
        //mav.addObject("usuario" , resultadoServicio.mostrarResultado()); POR AGREGAR
        mav.addObject("titulo", "Crear Resultado");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarResultado(@RequestParam Examen examen, @RequestParam Usuario usuario, @RequestParam Short respuestasCorrectas, @RequestParam Short respuestasIncorrectas, @RequestParam Long duracion, @RequestParam Integer puntajeFinal) {
        resultadoServicio.crearResultado(examen,usuario,respuestasCorrectas,respuestasIncorrectas,duracion,puntajeFinal);
        return new RedirectView("/resultado");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarResultado(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("");
        try {
            mav.addObject("Resultado", resultadoServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        mav.addObject("titulo", "Editar Resultado");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificarPregunta(@RequestParam Integer id, @RequestParam Short respuestasCorrectas, @RequestParam Short respuestasIncorrectas, @RequestParam Long duracion, @RequestParam Integer puntajeFinal) {

        try {
            resultadoServicio.modificarResultado(id,respuestasCorrectas,respuestasIncorrectas,duracion,puntajeFinal);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }

        return new RedirectView("/resultado");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarResultado(@PathVariable Integer id) {
        resultadoServicio.eliminarResultado(id);
        return new RedirectView("/resultado");
    }

    @PostMapping("/recuperar/{id}")
    public RedirectView recuperarResultado(@PathVariable Integer id) {
        try {
            resultadoServicio.recuperarResultado(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        return new RedirectView("/resultado");
    }

}
