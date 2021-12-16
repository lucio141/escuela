package com.example.demo.controladores;

import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Examen;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.excepciones.PadreNuloExcepcion;
import com.example.demo.servicios.CategoriaServicio;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.TematicaServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/categoria")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;
    private final TematicaServicio tematicaServicio;
    private final ExamenServicio examenServicio;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarCategorias(HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("categoria"); //Falta crear
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null){
            mav.addObject("errorNulo", map.get("errorNulo"));
            mav.addObject("padreNulo", map.get("padreNulo"));
            mav.addObject("errorRepetido", map.get("errorRepetido"));
            mav.addObject("errorEliminado", map.get("errorEliminado"));
            //mav.addObject("exito", map.get("success"));
        }

        mav.addObject("examenes", examenServicio.mostrarExamenesPorAlta(true));
        mav.addObject("titulo", "Tabla de examenes");
        mav.addObject("categorias", categoriaServicio.mostrarCategorias());
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
        return mav;
    }

    @GetMapping("/baja")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarCategoriasBaja() {
        ModelAndView mav = new ModelAndView(""); //Falta crear
        mav.addObject("categorias", categoriaServicio.mostrarCategoriasPorAlta(false));
        mav.addObject("titulo", "Tabla de categorias baja");
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearCategoria() {
        ModelAndView mav = new ModelAndView("");//Falta crear
        mav.addObject("categoria", new Categoria());
        mav.addObject("tematicas", tematicaServicio.mostrarTematicasPorAlta(true));
        mav.addObject("titulo", "Crear Categoria");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre, HttpServletRequest request, RedirectAttributes attributes) {

        try {
            categoriaServicio.crearCategoria(nombre);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("errorRepetido", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("errorEliminado", eliminado.getMessage());
        }

        return new RedirectView("/categoria");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam int id, @RequestParam String nombre, RedirectAttributes attributes) {
        try {
            categoriaServicio.modificarCategoria(id,nombre);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("errorRepetido", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("errorEliminado", eliminado.getMessage());
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }


        return new RedirectView("/categoria");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id) {
        try {
            categoriaServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        return new RedirectView("/categoria");
    }

    @PostMapping("/recuperar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperar(@PathVariable int id) {
        try {
            categoriaServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/categoria");
    }

}
