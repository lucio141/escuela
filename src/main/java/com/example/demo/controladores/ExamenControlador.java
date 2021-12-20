package com.example.demo.controladores;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.servicios.CategoriaServicio;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.ResultadoServicio;
import com.example.demo.servicios.TematicaServicio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/examen")
@AllArgsConstructor
public class ExamenControlador {

    private final ExamenServicio examenServicio;
    private final CategoriaServicio categoriaServicio;
    private final TematicaServicio tematicaServicio;
    private final ResultadoServicio resultadoServicio;
 /*
    @GetMapping()
    public ModelAndView mostrarExamenes(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("examen");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null){
            mav.addObject("errorNulo", map.get("errorNulo"));
            mav.addObject("padreNulo", map.get("padreNulo"));
            //mav.addObject("exito", map.get("success"));
        }

        mav.addObject("titulo", "Examenes");
        mav.addObject("examenes", examenServicio.mostrarExamenesPorAlta(true));
        mav.addObject("categorias", categoriaServicio.mostrarCategorias());
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
        return mav;
    }


    @GetMapping("/{id}")
    public ModelAndView mostrarExamen(@PathVariable int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null){
            mav.addObject("errorNulo", map.get("errorNulo"));
            mav.addObject("padreNulo", map.get("padreNulo"));
            mav.addObject("errorEliminado", map.get("errorEliminado"));
            mav.addObject("errorRepetido", map.get("errorRepetido"));
            //mav.addObject("exito", map.get("success"));
        }

        try{
            mav.addObject("titulo", examenServicio.obtenerPorId(id).getNombre());
            mav.addObject("resultados", resultadoServicio.top5(id));
        }catch (ObjetoNulloExcepcion e){
            System.out.println(e.getMessage());
        }


        return mav;
    }

    @GetMapping("/baja")
   // @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarExamenesBaja() {
        ModelAndView mav = new ModelAndView(""); //DECIDE DONATO
        mav.addObject("examenes", examenServicio.mostrarExamenesPorAlta(false));
        mav.addObject("titulo", "Tabla de examenes baja");
        return mav;
    }
*/
    @GetMapping("/crear")
   // @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearExamen() {
        ModelAndView mav = new ModelAndView("examen-formulario");
        mav.addObject("examen", new Examen());
        mav.addObject("dificultades", Arrays.asList(Dificultad.values()));
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
        mav.addObject("titulo", "Crear Examen");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarExamen(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("examen-formulario"); // Falta crear examen formulario que mande el ID

        try {
            mav.addObject("examen", examenServicio.obtenerPorId(id));
            mav.addObject("dificultades", Dificultad.values());
            mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
            mav.addObject("titulo", "Editar Examen");
            mav.addObject("accion", "modificar");
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo",nulo.getMessage());
        }

        return mav;
    }

    @GetMapping("/realizar/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView realizarExamen(@PathVariable int id, RedirectAttributes attributes , HttpSession session) {
        ModelAndView mav = new ModelAndView("hacer-examen"); // Falta crear

        try {
            Examen examen = examenServicio.obtenerPorId(id);
            Collections.shuffle(examen.getPreguntas());

            for (Pregunta pregunta : examen.getPreguntas()) {
                Collections.shuffle(pregunta.getRespuestas());
            }

            resultadoServicio.crearResultado(examen, (Integer)session.getAttribute("id"));
            mav.addObject("resultado", resultadoServicio.ObtenerUltimoResultado() );
            mav.addObject("examen", examen);
            mav.addObject("dificultades", Dificultad.values());

        } catch (ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("errorNulo", "No se encontro el Examen");
        }

        mav.addObject("titulo", "Realizar Examen");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam double notaRequerida, @RequestParam String nombre, RedirectAttributes attributes) {

        try {
            examenServicio.crearExamen(dificultad,tematica,notaRequerida, nombre);
            attributes.addFlashAttribute("examen", examenServicio.ObtenerUltimoExamen());
        }catch (ObjetoNulloExcepcion nulo){
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("errorNulo", "No se encontro el Examen");
        }

            return new RedirectView("/pregunta/crear");// HABLAR CON LUCIO



    }

    @PostMapping("/modificar")
   // @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam int id, @RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam double notaRequerida, @RequestParam String nombre, RedirectAttributes attributes) {

        try {
            examenServicio.modificarExamen(id, dificultad, tematica, notaRequerida, nombre);
            attributes.addFlashAttribute("examen", examenServicio.obtenerPorId(id));
            return new RedirectView("/examen/{" + id + "}");
        } catch(ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
            return new RedirectView("/examen");
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("errorRepetido", repetido.getMessage());
            System.out.println(repetido.getMessage());
            return new RedirectView("/examen");
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("errorEliminado", eliminado.getMessage());
            System.out.println(eliminado.getMessage());
            return new RedirectView("/examen");
        }

    }

    @PostMapping("/eliminar/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            examenServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return new RedirectView("/examen");
    }

    @PostMapping("/recuperar/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            examenServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("errorNulo", nulo.getMessage());
        }

        return new RedirectView("/examen");
    }
}
