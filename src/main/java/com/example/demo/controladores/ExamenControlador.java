package com.example.demo.controladores;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/examen")
@AllArgsConstructor
public class ExamenControlador {

    private final ExamenServicio examenServicio;

    @GetMapping()
    public ModelAndView mostrarExamenes() {
        ModelAndView mav = new ModelAndView(""); //Falta crear
        List<Examen> examenes = examenServicio.mostrarExamenesPorAlta(true);

        mav.addObject("examenes", examenes);
        mav.addObject("title", "Tabla de examenes");

        return mav;
    }

    @GetMapping("/baja")
    public ModelAndView mostrarExamenesBaja() {
        ModelAndView mav = new ModelAndView(""); //Falta crear

        List<Examen> examenes = examenServicio.mostrarExamenesPorAlta(false);
        mav.addObject("examenes", examenes);
        mav.addObject("title", "Tabla de examenes baja");

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearExamen() {
        ModelAndView mav = new ModelAndView("");//Falta crear

        mav.addObject("examen", new Examen());
        mav.addObject("title", "Crear Examen");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarExamen(@PathVariable int id) {
        ModelAndView mav = new ModelAndView(""); // Falta crear

        try {
            mav.addObject("examen", examenServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        mav.addObject("title", "editar Examen");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam Double notaRequerida) {
        examenServicio.crearExamen(dificultad,tematica,notaRequerida);

        return new RedirectView("/examen");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam Double notaRequerida) {
        try {
            examenServicio.modificarExamen(id, dificultad, tematica, notaRequerida);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        return new RedirectView("/examen");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable int id) {
        examenServicio.eliminar(id);
        return new RedirectView("/examen");
    }

    @PostMapping("/recuperar/{id}")
    public RedirectView recuperar(@PathVariable int id) {
        try {
            examenServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/examen");
    }

}
