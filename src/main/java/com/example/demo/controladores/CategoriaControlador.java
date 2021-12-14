package com.example.demo.controladores;

import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Examen;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.CategoriaServicio;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.TematicaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/categoria")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;
    private final TematicaServicio tematicaServicio;
    private final ExamenServicio examenServicio;

    @GetMapping()
    public ModelAndView mostrarCategorias() {
        ModelAndView mav = new ModelAndView("categoria"); //Falta crear
        List<Examen> examenes = examenServicio.mostrarExamenesPorAlta(true);
        mav.addObject("examenes", examenes);
        mav.addObject("titulo", "Tabla de examenes");
        mav.addObject("categorias", categoriaServicio.mostrarCategorias());
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
        return mav;
    }

    @GetMapping("/baja")
    public ModelAndView mostrarCategoriasBaja() {
        ModelAndView mav = new ModelAndView(""); //Falta crear
        mav.addObject("categorias", categoriaServicio.mostrarCategoriasPorAlta(false));
        mav.addObject("titulo", "Tabla de categorias baja");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearCategoria() {
        ModelAndView mav = new ModelAndView("");//Falta crear
        mav.addObject("categoria", new Categoria());
        mav.addObject("tematicas", tematicaServicio.mostrarTematicasPorAlta(true));
        mav.addObject("titulo", "Crear Categoria");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarCategoria(@PathVariable int id) {
        ModelAndView mav = new ModelAndView(""); // Falta crear
        try {
            mav.addObject("categoria", categoriaServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        mav.addObject("titulo", "editar Examen");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) {
        categoriaServicio.crearCategoria(nombre);
        return new RedirectView("/categoria");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam int id, @RequestParam String nombre) {
        try {
            categoriaServicio.modificarCategoria(id,nombre);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        return new RedirectView("/categoria");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable int id) {
        try {
            categoriaServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        return new RedirectView("/categoria");
    }

    @PostMapping("/recuperar/{id}")
    public RedirectView recuperar(@PathVariable int id) {
        try {
            categoriaServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/categoria");
    }

}
