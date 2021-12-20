package com.example.demo.servicios;

import com.example.demo.dto.ExamenDTO;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.repositorios.ExamenRepositorio;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ResultadoRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
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
    public void crearResultado(ExamenDTO examenDTO, Integer id) throws ObjetoNulloExcepcion{
        Resultado resultado = new Resultado();
        resultado.setExamen(Mapper.examenDTOAEntidad(examenDTO));
        resultado.setUsuario(Mapper.usuarioDTOAEntidad(usuarioServicio.obtenerPorId(id)));
        resultado.setRespuestasCorrectas((short)0);
        resultado.setRespuestasIncorrectas((short)(examenDTO.getPreguntas().size()));
        resultado.setPuntajeFinal(null);
        resultadoRepositorio.save(resultado);
    }

    @Transactional
    public void modificarResultado(Integer id, List<String> respuestas, Integer examenId) throws ObjetoNulloExcepcion {
        short contadorRespuestasCorrectas = 0;
        short contadorRespuestasInorrectas = 0;
        int puntajeAcumulado = 0;
        int puntajeTotal = 0;

        ExamenDTO examenDTO = examenServicio.obtenerPorId(examenId);
        List<Pregunta> preguntas = examenDTO.getPreguntas();

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

        if (resultado.getPuntajeFinal() == null) {
            resultado.setRespuestasCorrectas(contadorRespuestasCorrectas);
            resultado.setRespuestasIncorrectas(contadorRespuestasInorrectas);
            resultado.setPuntajeFinal(puntajeFinal);
            resultado.setAprobado(puntajeFinal>examenDTO.getNotaRequerida());
            resultado.setDuracion(diferenciaTiempo(resultado.getTiempoInicio(), LocalDateTime.now()));
            resultadoRepositorio.save(resultado);
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

    public static String diferenciaTiempo(LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        String diferencia;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        Long minutos = Math.abs(duracion.toMinutes());
        Long segundos = Math.abs(duracion.minusMinutes(duracion.toMinutes()).getSeconds());

        //System.out.printf("%d:%02d%n", minutos , segundos);
        diferencia = String.format("%d:%02d%n", minutos , segundos);

        if (Integer.parseInt(diferencia.substring(0, 1)) > 59){
            diferencia = "1hr +";
        }

        return diferencia;
    }
}



