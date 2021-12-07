package com.example.demo.controladores;


import com.example.demo.entidades.Rol;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/rol")
public class RolControlador {

    @Autowired
    private RolServicio rolServicio;

    @GetMapping()
    public ModelAndView mostrarExamenes() {
        ModelAndView mav = new ModelAndView(""); //Falta crear
        List<Rol> roles = rolServicio.mostrarRolesPorAlta(true);

        mav.addObject("roles", roles);
        mav.addObject("title", "Tabla de roles");

        return mav;
    }

    @GetMapping("/baja")
    public ModelAndView mostrarRolesBaja() {
        ModelAndView mav = new ModelAndView(""); //Falta crear

        List<Rol> roles = rolServicio.mostrarRolesPorAlta(false);
        mav.addObject("roles", roles);
        mav.addObject("title", "Tabla de examenes baja");

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("");//Falta crear

        mav.addObject("rol", new Rol());
        mav.addObject("title", "Crear Rol");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarExamen(@PathVariable int id) {
        ModelAndView mav = new ModelAndView(""); // Falta crear

        try {
            mav.addObject("rol", rolServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        mav.addObject("title", "editar Examen");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) {
        rolServicio.crearRol(nombre);

        return new RedirectView("/roles");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombre) {
        try {
            rolServicio.modificarRol(id,nombre);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        return new RedirectView("/roles");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable int id) {
        rolServicio.eliminar(id);
        return new RedirectView("/roles");
    }

    @PostMapping("/recuperar/{id}")
    public RedirectView recuperar(@PathVariable int id) {
        try {
            rolServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/roles");
    }

}
