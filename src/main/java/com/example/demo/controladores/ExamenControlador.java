package com.example.demo.controladores;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.CategoriaServicio;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.TematicaServicio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/examen")
@AllArgsConstructor
public class ExamenControlador {

    private final ExamenServicio examenServicio;
    private final CategoriaServicio categoriaServicio;
    private final TematicaServicio tematicaServicio;

    @GetMapping()
    public ModelAndView mostrarExamenes() {
        ModelAndView mav = new ModelAndView("examen"); //Falta crear
        List<Examen> examenes = examenServicio.mostrarExamenesPorAlta(true);
        mav.addObject("examenes", examenes);
        mav.addObject("titulo", "Tabla de examenes");
        mav.addObject("categorias", categoriaServicio.mostrarCategorias());
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
        return mav;
    }

    @GetMapping("/baja")
    public ModelAndView mostrarExamenesBaja() {
        ModelAndView mav = new ModelAndView(""); //Falta crear

        List<Examen> examenes = examenServicio.mostrarExamenesPorAlta(false);
        mav.addObject("examenes", examenes);
        mav.addObject("titulo", "Tabla de examenes baja");

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearExamen() {
        ModelAndView mav = new ModelAndView("");//Falta crear

        mav.addObject("examen", new Examen());
        mav.addObject("titulo", "Crear Examen");
        mav.addObject("accion", "guardar");
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

        mav.addObject("titulo", "editar Examen");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam double notaRequerida, RedirectAttributes atributosFash) {
        examenServicio.crearExamen(dificultad,tematica,notaRequerida);

        atributosFash.addFlashAttribute("examen", examenServicio.ObtenerUltimoExamen());
        return new RedirectView("/../pregunta");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam int id, @RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam double notaRequerida, RedirectAttributes atributosFlash) {
        try {
            examenServicio.modificarExamen(id, dificultad, tematica, notaRequerida);
            atributosFlash.addFlashAttribute("examen", examenServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }


        return new RedirectView("/../pregunta");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable int id) {
        try {
            examenServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
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
