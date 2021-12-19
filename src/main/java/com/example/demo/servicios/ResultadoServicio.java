package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.repositorios.ExamenRepositorio;
import com.example.demo.repositorios.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ResultadoRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultadoServicio {

    private final ResultadoRepositorio resultadoRepositorio;
      private final UsuarioServicio usuarioServicio;
      private final ExamenServicio examenServicio;
      private final ExamenRepositorio examenRepositorio;

    @Transactional
    public void crearResultado(Examen examen, Integer id) throws ObjetoNulloExcepcion{

        Resultado resultado = new Resultado();
        resultado.setExamen(examen);
        resultado.setUsuario(Mapper.usuarioDTOAEntidad(usuarioServicio.obtenerPorId(id)));
        resultado.setRespuestasCorrectas((short)0);
        resultado.setRespuestasIncorrectas((short)(examen.getPreguntas().size()));
        resultado.setPuntajeFinal(null);
        resultadoRepositorio.save(resultado);
        System.out.println(resultado.getTiempoFinalizacion());
        System.out.println(resultado.getTiempoInicio());
    }

    @Transactional
    public void modificarResultado(Integer id, List<String> respuestas, Integer examenId) throws ObjetoNulloExcepcion {
       short contadorRespuestasCorrectas = 0;
       short contadorRespuestasInorrectas = 0;
        /*
        List<String> respuestasCorrectas = new ArrayList<>();
        List<String> respuestasCorrectas = new ArrayList<>();
        */
        int puntajeAcumulado = 0;
        int puntajeTotal = 0;
        Examen examen = examenServicio.obtenerPorId(examenId);
        List<Pregunta> preguntas = examen.getPreguntas();
        for (int i=0; i< preguntas.size(); i++ ){
            puntajeTotal += preguntas.get(i).getPuntaje();
            if ( preguntas.get(i).getRespuestaCorrecta().equalsIgnoreCase(respuestas.get(i))){
                contadorRespuestasCorrectas++;
                puntajeAcumulado += preguntas.get(i).getPuntaje();



            }else{
                contadorRespuestasInorrectas++;
            }


        }
        int puntajeFinal = Math.round(puntajeAcumulado*100/puntajeTotal);

        Resultado resultado = obtenerPorId(id);
        resultadoRepositorio.save(resultado);
        if (resultado.getPuntajeFinal() == null) {
            System.out.println(resultado.getTiempoInicio());
            System.out.println(resultado.getTiempoFinalizacion());
        //    System.out.println(resultado.getTiempoFinalizacion() - resultado.getTiempoInicio());

            resultado.setRespuestasCorrectas(contadorRespuestasCorrectas);
            resultado.setRespuestasIncorrectas(contadorRespuestasInorrectas);
            resultado.setPuntajeFinal(puntajeFinal);
            resultado.setAprobado(puntajeFinal>examen.getNotaRequerida());

            resultadoRepositorio.save(resultado);

            Resultado resultadoV2 = obtenerPorId(id);

            System.out.println(resultadoV2.getTiempoInicio());
            System.out.println(resultadoV2.getTiempoFinalizacion());
            //System.out.println(resultadoV2.getTiempoFinalizacion().getTime() - resultadoV2.getTiempoInicio().getTime());

            //resultadoV2.setDuracion(resultadoV2.getTiempoFinalizacion().getTime() - resultadoV2.getTiempoInicio().getTime());
            resultadoRepositorio.save(resultado);

            resultadoV2 = obtenerPorId(id);

            System.out.println(resultadoV2.getTiempoInicio());
            System.out.println(resultadoV2.getTiempoFinalizacion());
            //System.out.println(resultadoV2.getTiempoFinalizacion().getTime() - resultadoV2.getTiempoInicio().getTime());
        }
    }

    @Transactional
    public List<Resultado> mostrarResultados() {
        return resultadoRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Resultado> mostrarResultadosPorAlta(Boolean alta) {
        return resultadoRepositorio.mostrarPorAlta(alta);
    }

    @Transactional
    public Resultado obtenerPorId(int id) throws ObjetoNulloExcepcion {
        Resultado resultado = resultadoRepositorio.findById(id).orElse(null);

        if (resultado == null) {
            throw new ObjetoNulloExcepcion("");
        }

        return resultado;
    }

    @Transactional
    public void eliminar(int id) {
        resultadoRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        Resultado resultado = obtenerPorId(id);
        resultadoRepositorio.darAlta(id);
    }

    @Transactional
    public Resultado ObtenerUltimoResultado() throws ObjetoNulloExcepcion {
        Resultado resultado = resultadoRepositorio.mostrarUltimoResultado();

        if(resultado== null){
            throw new ObjetoNulloExcepcion("No se encontro el resultado");
        }

        return resultado;
    }

    @Transactional(readOnly = true)
    public List<Resultado> top5 (int id) throws ObjetoNulloExcepcion {
        List<Resultado> resultados = new ArrayList<>();
        for (Integer idResultado : examenRepositorio.top5(id)) {
            resultados.add(obtenerPorId(idResultado));
        }
        return resultados;
    }
}

/*
    public static String diferenciaTiempo(LocalDateTime fechaInicio, LocalDateTime fechaFinal){

        String diferencia = "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss a");

        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = LocalDateTime.now().plusMinutes(70).plusSeconds(29);

        Duration duracion = Duration.between(date2, date1);

        Long minutos = Math.abs(duracion.toMinutes());

        Long segundos = Math.abs(duracion.minusMinutes(duracion.toMinutes()).getSeconds());

        System.out.printf("%d:%02d%n", minutos , segundos);

        diferencia = " " + duracion.toString() + " ";

        return diferencia;
    }
 */