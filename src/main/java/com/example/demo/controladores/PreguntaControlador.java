package com.example.demo.controladores;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.excepciones.PadreNuloExcepcion;
import com.example.demo.servicios.PreguntaServicio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
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
@RequestMapping("examen/{examenId}/pregunta")
public class PreguntaControlador{

    private final PreguntaServicio preguntaServicio;

    @GetMapping("/crear")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearPregunta(HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("pregunta-formulario");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        try{
            mav.addObject("examen" , map.get("examen"));
            mav.addObject("pregunta", new Pregunta());
            mav.addObject("titulo", "Crear Pregunta");
            mav.addObject("accion", "guardar");
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            attributes.addFlashAttribute("errorNulo", "No se encontro el Examen");
            mav.setViewName("redirect:/examen");
        }

        return mav;
    }

    @GetMapping("/editar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarPregunta(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("pregunta-formulario");
        try {
            mav.addObject("pregunta", preguntaServicio.obtenerPorId(id));
            mav.addObject("titulo", "Editar Pregunta");
            mav.addObject("accion", "modificar");
        } catch (ObjetoNulloExcepcion nulo) {
            nulo.printStackTrace();
            attributes.addFlashAttribute("errorNulo", "No se encontro el Examen");
            mav.setViewName("redirect:/examen");
        }
        return mav;
    }

    @PostMapping("/guardar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardarPregunta(@RequestParam Dificultad dificultad, @RequestParam String enunciado, @RequestParam List<String> respuestas, @RequestParam String respuestaCorrecta, @RequestParam int puntaje, @RequestParam Examen examen, HttpServletRequest request, RedirectAttributes attributes) {

        try {
            preguntaServicio.crearPregunta(dificultad, enunciado, respuestas, respuestaCorrecta, puntaje, examen);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("errorRepetido", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("errorEliminado", eliminado.getMessage());
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }catch (PadreNuloExcepcion padre){
            attributes.addFlashAttribute("padreNulo", padre.getMessage());
            return new RedirectView("/examen");
        }

        return new RedirectView("/examen/{"+ examen.getId() + "}");
    }

    @PostMapping("/modificar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam Dificultad dificultad, @RequestParam String enunciado, @RequestParam List<String> respuestas, @RequestParam String respuestaCorrecta, @RequestParam int puntaje, @RequestParam Examen examen, @RequestParam int id, RedirectAttributes attributes) {
        try{
            preguntaServicio.modificarPregunta(dificultad, enunciado, respuestas, respuestaCorrecta, puntaje, examen, id);
        }catch(ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("errorRepetido", repetido.getMessage());
            System.out.println(repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("errorEliminado", eliminado.getMessage());
            System.out.println(eliminado.getMessage());
        }
        return new RedirectView("/examen/{"+ examen.getId() + "}");
    }

    @PostMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarPregunta(@PathVariable int id, RedirectAttributes attributes) {

        try{
            preguntaServicio.eliminar(id);
            return new RedirectView("/../examen/{" + preguntaServicio.obtenerPorId(id).getExamen().getId() + "}");
        }catch (ObjetoNulloExcepcion nulo){
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
            return new RedirectView("/examen");
        }
    }
}
