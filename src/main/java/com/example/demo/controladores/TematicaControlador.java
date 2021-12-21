package com.example.demo.controladores;

import com.example.demo.entidades.Tematica;
import com.example.demo.entidades.Resultado;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ValidacionCampExcepcion;
import com.example.demo.servicios.TematicaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/tematica")
public class TematicaControlador {

    private final TematicaServicio tematicaServicio;
/*
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarTematicas(){
        ModelAndView mav = new ModelAndView("tematicas"); //Falta HTML
        mav.addObject("tematicas",tematicaServicio.mostrarTematicasPorAlta(true));
        mav.addObject("titulo", "Tabla de Tematicas");
        return mav;
    }

    @GetMapping("/baja")
    //@PreAuthorize("hasRole('ADMIN')")
    public  ModelAndView mostrarTematicasBaja(){
        ModelAndView mav = new ModelAndView("tematicas"); //Falta HTML
        mav.addObject("tematicas",tematicaServicio.mostrarTematicasPorAlta(false));
        mav.addObject("titulo", "Tabla de Tematicas Baja");
        return mav;
    }
*/
    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView listarTematicas(HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("tematica-administrador");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null){
            mav.addObject("errorNulo", map.get("errorNulo"));
            mav.addObject("padreNulo", map.get("padreNulo"));
            mav.addObject("errorRepetido", map.get("errorRepetido"));
            mav.addObject("errorEliminado", map.get("errorEliminado"));
            //mav.addObject("exito", map.get("success"));
        }

        mav.addObject("titulo", "Tematicas");
        mav.addObject("tematicasBaja", tematicaServicio.mostrarTematicasPorAlta(false));
        mav.addObject("tematicasAlta", tematicaServicio.mostrarTematicasPorAlta(true));

        return mav;
    }

    @GetMapping("/crear")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearTematica(){
        ModelAndView mav = new ModelAndView("tematica-formulario");
        mav.addObject("tematica",new Tematica() );
        mav.addObject("titulo", "Crear Tematica");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarTematica(@PathVariable int id, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("tematica-formulario");

        try{
            mav.addObject("tematica",tematicaServicio.obtenerPorId(id));
            mav.addObject("titulo", "Editar Tematica");
            mav.addObject("accion", "modificar");
        }catch(ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return mav;
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView ingresarTematica(@PathVariable int id, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("tematica");

        try{
            mav.addObject("tematica",tematicaServicio.obtenerPorId(id));
            mav.addObject("resultado", new Resultado());
        }catch( ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return mav;
    }

    @PostMapping("/guardar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardarTematicas(@RequestParam String nombre, RedirectAttributes attributes){
        try {
            tematicaServicio.crearTematica(nombre);
        }catch (ValidacionCampExcepcion validacion){
            attributes.addFlashAttribute("errorValidacion", validacion.getMessage());
        }

        return new RedirectView("/tematica/admin");
    }

    @PostMapping("/modificar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar( @RequestParam String nombre,@RequestParam int id, RedirectAttributes attributes){

        try{
            tematicaServicio.modificarTematica(nombre,id);
        }catch(ObjetoNulloExcepcion | ValidacionCampExcepcion validacion){
            attributes.addFlashAttribute("errorValidacion", validacion.getMessage());
        }
        return new RedirectView("/tematica/admin");
    }

    @PostMapping("/darAlta/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView darAlta( @PathVariable int id, RedirectAttributes attributes){

        try {
            tematicaServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return new RedirectView("/tematica/admin");
    }

    @PostMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id, RedirectAttributes attributes){

        try {
            tematicaServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return new RedirectView("/tematica/admin");
    }
}
