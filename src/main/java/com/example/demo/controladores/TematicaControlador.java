package com.example.demo.controladores;

import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.TematicaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/*
Esta clase se encarga de gestionar la comunicacion entre el usuario y la
aplicacion
 */

@Controller
@RequestMapping("/tematica")
public class TematicaControlador {

    @Autowired
    private TematicaServicio tematicaServicio;

    @GetMapping
    public ModelAndView mostrarTematicas(){
        ModelAndView mav = new ModelAndView(); //Falta HTML

        mav.addObject("tematicas",tematicaServicio.mostrarTematica(true));
        return mav;
    }

    @GetMapping("/baja")
    public  ModelAndView mostrarTematicasBaja(){
        ModelAndView mav = new ModelAndView(); //Falta HTML

        mav.addObject("tematicas",tematicaServicio.mostrarTematica(false));

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearTematica(){
        ModelAndView mav = new ModelAndView(); //FALTA HTML
        mav.addObject("tematica",new Tematica() );
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarTematica(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView(); //FALTA HTML
        try{
            mav.addObject("tematica",tematicaServicio.buscarPorId(id)) ;
        }
        catch( ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarTematicas(@RequestParam String nombre){
        tematicaServicio.crearTematica(nombre);

        return new RedirectView("/tematica");
    }

    @PostMapping("/editar")
    public RedirectView editarTematicas( @RequestParam String nombre,@RequestParam Integer id){

        try{
            tematicaServicio.editarTematica(nombre,id);
        }
        catch( ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }

        return new RedirectView("/tematica");
    }

    @GetMapping("/registrar/{id}")
    public RedirectView darAlta( @PathVariable Integer id){
        tematicaServicio.darAlta(id);

        return new RedirectView("/tematica");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id){
        tematicaServicio.eliminarTematica(id);

        return  new RedirectView("/tematica");
    }


}
