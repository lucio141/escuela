package com.example.demo.controladores;

import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.seguridad.servicios.RolServicio;
import com.example.demo.seguridad.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarUsuarios(){
        ModelAndView mav = new ModelAndView("usuario");//
        mav.addObject("usuario", usuarioServicio.mostrarUsuariosPorAlta(true));
        mav.addObject("titulo", "Tabla de Usuarios");
        return mav;
    }

    @GetMapping("/baja")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarUsuariosBaja(){
        ModelAndView mav = new ModelAndView("");// Vista de Usuarios FALTA
        mav.addObject("usuario", usuarioServicio.mostrarUsuariosPorAlta(false));
        mav.addObject("titulo", "Tabla de Usuarios Baja");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearUsuario(){
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(true));
        mav.addObject("titulo", "Crear Usuario");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarUsuario(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("usuario-formulario");
        try {
            mav.addObject("usuario", usuarioServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        mav.addObject("titulo", "Editar Usuario");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String nombreUsuario, @RequestParam String contrasenia, @RequestParam Integer edad, @RequestParam String mail, @RequestParam String telefono, @RequestParam Rol rol) {
        usuarioServicio.crearUsuario(nombre, apellido, nombreUsuario, contrasenia, edad, mail, telefono, rol);
        return new RedirectView("/usuario");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Integer id, @RequestParam String nombreUsuario, @RequestParam String contrasenia, @RequestParam String mail, @RequestParam Rol rol){

        try{
            usuarioServicio.modificarUsuario(id, nombreUsuario, contrasenia, mail, rol);
        }catch (ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }
        return new RedirectView("/usuario");
    }

    @PostMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarUsuario(@PathVariable Integer id){
        usuarioServicio.eliminar(id);
        return new RedirectView("/usuario");
    }

    @PostMapping("/registrar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView darAltaUsuario (@PathVariable Integer id){
        usuarioServicio.darAlta(id);
        return new RedirectView("/usuario");
    }
}
