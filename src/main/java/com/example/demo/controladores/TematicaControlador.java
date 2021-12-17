package com.example.demo.controladores;

import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.TematicaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@RequestMapping("/tematica")
public class TematicaControlador {

    private final TematicaServicio tematicaServicio;

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

    @GetMapping("/crear")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearTematica(){
        ModelAndView mav = new ModelAndView("tematica-formulario"); //FALTA HTML
        mav.addObject("tematica",new Tematica() );
        mav.addObject("titulo", "Crear Tematica");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarTematica(@PathVariable int id){
        ModelAndView mav = new ModelAndView("tematica-formulario"); //FALTA HTML
        try{
            mav.addObject("tematica",tematicaServicio.obtenerPorId(id)) ;
        }
        catch( ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }
        mav.addObject("titulo", "Editar Tematica");
        mav.addObject("accion", "modificar");
        return mav;
    }


    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView ingresarTematica(@PathVariable int id){
        ModelAndView mav = new ModelAndView("tematica"); //FALTA HTML
        try{
            mav.addObject("tematica",tematicaServicio.obtenerPorId(id)) ;

        }
        catch( ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }

        return mav;
    }



    @PostMapping("/guardar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardarTematicas(@RequestParam String nombre){
        tematicaServicio.crearTematica(nombre);

        return new RedirectView("/tematica");
    }

    @PostMapping("/modificar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar( @RequestParam String nombre,@RequestParam int id){

        try{
            tematicaServicio.modificarTematica(nombre,id);
        }
        catch( ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }

        return new RedirectView("/tematica");
    }

    @GetMapping("/registrar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperar( @PathVariable int id){
        try {
            tematicaServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }

        return new RedirectView("/tematica");
    }

    @PostMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id){
        try {
            tematicaServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }

        return  new RedirectView("/tematica");
    }
}
