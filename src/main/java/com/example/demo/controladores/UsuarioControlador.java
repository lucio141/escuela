package com.example.demo.controladores;

import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.RolServicio;
import com.example.demo.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

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

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView obtenerPerfil(@PathVariable int id, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("perfil"); //FALTA HTML
        try{
            mav.addObject("categoria",usuarioServicio.obtenerPorId(id)) ;
        }catch( ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }
        mav.addObject("titulo", "Mi perfil");

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearUsuario(){
        ModelAndView mav = new ModelAndView("registerDonato");
        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(true));
        mav.addObject("titulo", "Crear Usuario");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarUsuario(@PathVariable Integer id, HttpSession sesion, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("usuario-editar");

        try {
            if((int)sesion.getAttribute("id") != id && !usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN") ){
                mav.setViewName("redirect:/");
                attributes.addFlashAttribute("error-autorización", "No se encuentra habilitado para realizar la modificación");
                return mav;
            }

            mav.addObject("roles", rolServicio.mostrarRoles());
            mav.addObject("rolUsuario", rolServicio.mostrarPorNombre("USUARIO"));
            mav.addObject("usuario", usuarioServicio.obtenerPorId(id));
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        mav.addObject("titulo", "Editar Usuario");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @GetMapping("/editarPass/{id}")
    public ModelAndView editarPass(@PathVariable Integer id, HttpSession sesion, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("usuario-contrasenia");

        try {
            if((int)sesion.getAttribute("id") != id && !usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN") ){
                mav.setViewName("redirect:/");
                attributes.addFlashAttribute("error-autorización", "No se encuentra habilitado para realizar la modificación");
                return mav;
            }

            mav.addObject("usuario", usuarioServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        mav.addObject("titulo", "Cambiar Contraseña");
        mav.addObject("accion", "modificarPass");
        return mav;
    }

    @GetMapping("/confirmarUsuario")
    public ModelAndView editarPass(RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("usuario-nuevaContrasenia");
        mav.addObject("titulo", "Recuperar Contraseña");
        mav.addObject("accion", "generarPass");
        return mav;
    }

    @PostMapping("/generarPass")
    public RedirectView generarPass(@RequestParam(name = "mail") String mail, RedirectAttributes attributes){
        try {
            usuarioServicio.generarPass(mail);
            attributes.addFlashAttribute("exito", "Se envío un mail a su casilla de correo");
        } catch (ObjetoNulloExcepcion e) {
            attributes.addFlashAttribute("errorNulo", "No se encontró un usuario con los datos provistos");
        }

        return new RedirectView("/login");
    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String nombreUsuario, @RequestParam String contrasenia, @RequestParam Integer edad, @RequestParam String mail, @RequestParam String telefono, @RequestParam(name = "rol") Rol rol) {
        usuarioServicio.crearUsuario(nombre, apellido, nombreUsuario, contrasenia, edad, mail, telefono, rol);
        return new RedirectView("/usuario");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam(name = "usuarioId") Integer id, @RequestParam String nombre, @RequestParam String apellido,  @RequestParam String nombreUsuario, @RequestParam Integer edad, @RequestParam String telefono, @RequestParam String mail, @RequestParam Rol rol, HttpSession sesion, RedirectAttributes attributes){
        try{
            usuarioServicio.modificarUsuario(id, nombre, apellido, nombreUsuario, edad, mail, telefono, rol);

            if((int)sesion.getAttribute("id") == id){
                return new RedirectView("/usuario/"+id);
            }else if(usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN")){
                return new RedirectView("/usuario");
            }
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }
        return new RedirectView("/usuario");
    }

    @PostMapping("/modificarPass")
    public RedirectView modificarPass(@RequestParam(name = "usuarioId") Integer id, @RequestParam String contraseniaAnterior, @RequestParam String contrasenia1, @RequestParam String contrasenia2, HttpSession sesion, RedirectAttributes attributes){
        try{
            usuarioServicio.modificarPass(id,contraseniaAnterior,contrasenia1,contrasenia2);
                if((int)sesion.getAttribute("id") == id){
                    return new RedirectView("/usuario/"+id);
                }else if(usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN")){
                    return new RedirectView("/usuario");
                }
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }
        return new RedirectView("/usuario");
    }

    @PostMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarUsuario(@PathVariable Integer id, RedirectAttributes attributes){
        try {
            usuarioServicio.eliminar(id);
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return new RedirectView("/usuario");
    }

    @PostMapping("/registrar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView darAltaUsuario (@PathVariable Integer id, RedirectAttributes attributes){
        try {
            usuarioServicio.darAlta(id);
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }
        return new RedirectView("/usuario");
    }
}
