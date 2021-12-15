package com.example.demo.controladores;


import com.example.demo.entidades.Rol;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.RolServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@RequestMapping("/rol")
public class RolControlador {

    private final RolServicio rolServicio;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarRoles() {
        ModelAndView mav = new ModelAndView("rol"); //Falta crear
        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(true));
        mav.addObject("titulo", "Tabla de roles");
        return mav;
    }

    @GetMapping("/baja")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarRolesBaja() {
        ModelAndView mav = new ModelAndView("rol"); //Falta crear
        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(false));
        mav.addObject("titulo", "Tabla de examenes baja");
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("rol-formulario");//Falta crear

        mav.addObject("rol", new Rol());
        mav.addObject("titulo", "Crear Rol");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarRol(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("rol-formulario"); // Falta crear
        try {
            mav.addObject("rol", rolServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        mav.addObject("titulo", "editar Examen");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre) {
        rolServicio.crearRol(nombre);

        return new RedirectView("/roles");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam int id, @RequestParam String nombre) {
        try {
            rolServicio.modificarRol(id,nombre);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }

        return new RedirectView("/roles");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id) {
        rolServicio.eliminar(id);
        return new RedirectView("/roles");
    }

    @PostMapping("/recuperar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperar(@PathVariable int id) {
        try {
            rolServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            System.out.println(e.getMessage());
        }
        return new RedirectView("/roles");
    }

}
