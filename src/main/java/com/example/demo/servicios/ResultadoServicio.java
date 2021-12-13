package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ResultadoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;

@Service
@AllArgsConstructor
public class ResultadoServicio {

    private final ResultadoRepositorio resultadoRepositorio;

    @Transactional
    public void crearResultado(Examen examen, Usuario usuario, Short respuestasCorrectas, Short respuestasIncorrectas, Long duracion, Integer puntajeFinal) {

        Resultado resultado = new Resultado();
        resultado.setExamen(examen);
        resultado.setUsuario(usuario);
        resultado.setRespuestasCorrectas(respuestasCorrectas);
        resultado.setRespuestasIncorrectas(respuestasIncorrectas);
        resultado.setDuracion(duracion);
        resultado.setPuntajeFinal(puntajeFinal);
        resultadoRepositorio.save(resultado);

    }

    @Transactional
    public void modificarResultado(Integer id, Short respuestasCorrectas, Short respuestasIncorrectas, Long duracion, Integer puntajeFinal) throws ObjetoNulloExcepcion {

        Resultado resultado = obtenerPorId(id);
        resultado.setRespuestasCorrectas(respuestasCorrectas);
        resultado.setRespuestasIncorrectas(respuestasIncorrectas);
        resultado.setDuracion(duracion);
        resultado.setPuntajeFinal(puntajeFinal);
        resultadoRepositorio.save(resultado);
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
}
