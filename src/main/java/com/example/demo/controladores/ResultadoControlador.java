package com.example.demo.controladores;

import com.example.demo.entidades.Resultado;
import com.example.demo.repositorios.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.ResultadoServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/resultado")
public class ResultadoControlador {

    private final ResultadoServicio resultadoServicio;
    private final ExamenServicio examenServicio;

    @GetMapping
    public ModelAndView mostrarResultado() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultados", resultadoServicio.mostrarResultadosPorAlta(true));
        mav.addObject("titulo", "Tabla de Resultados");
        return mav;
    }

    @GetMapping("/eliminados")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarResultadoeliminados() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultados", resultadoServicio.mostrarResultadosPorAlta(false));
        mav.addObject("titulo", "Tabla de Resultados dados de baja");
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearResultado() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultado", new Resultado());
        //mav.addObject("examenes" , examenServicio.mostrarExamenes()); POR AGREGAR
        //mav.addObject("usuario" , resultadoServicio.mostrarResultado()); POR AGREGAR
        mav.addObject("titulo", "Crear Resultado");
        mav.addObject("accion", "guardar");
        return mav;
    }

/*
    @PostMapping("/guardar")
    public RedirectView guardarResultado(HttpSession session,@RequestParam(value="examen") Integer examenId) {
        try {
            resultadoServicio.crearResultado(examenId,  (Integer)session.getAttribute("id"));
            return new RedirectView("/tematica");
        }catch(ObjetoNulloExcepcion e){

        }
        return new RedirectView("/tematica");
    }

 */


    @GetMapping("/editar/{id}")
    public ModelAndView editarResultado(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("");
        try {
            mav.addObject("Resultado", resultadoServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        mav.addObject("titulo", "Editar Resultado");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @GetMapping("/mostrar/{id}")
    public ModelAndView mostrarResultado(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("resultados-top");
        try {

            Resultado resultado = resultadoServicio.obtenerPorId(id);
            mav.addObject("Resultado", resultado);
            mav.addObject("resultado", resultado);
            mav.addObject("duracion", resultado.getDuracion() );
            mav.addObject("top", resultadoServicio.top5(resultado.getExamen().getId()) );
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        mav.addObject("titulo", "Editar Resultado");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam(name="resultadoId") int resultadoId, @RequestParam("respuestas") List<String> respuestas,@RequestParam(name="examenId") int examenId) {

        try {
            resultadoServicio.modificarResultado(resultadoId,respuestas,examenId);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }

        return new RedirectView("/resultado/mostrar/"+resultadoId);
    }

    @PostMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarResultado(@PathVariable int id) {
        resultadoServicio.eliminar(id);
        return new RedirectView("/resultado");
    }

    @PostMapping("/recuperar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperarResultado(@PathVariable int id) {
        try {
            resultadoServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion e) {
            e.printStackTrace();
        }
        return new RedirectView("/resultado");
    }

}
