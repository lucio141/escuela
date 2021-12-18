package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ResultadoRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;

@Service
@AllArgsConstructor
public class ResultadoServicio {

    private final ResultadoRepositorio resultadoRepositorio;
  private final UsuarioServicio usuarioServicio;
    @Transactional
    public void crearResultado(Examen examen, Integer id) throws ObjetoNulloExcepcion{

        Resultado resultado = new Resultado();
        resultado.setExamen(examen);
        resultado.setUsuario(Mapper.usuarioDTOAEntidad(usuarioServicio.obtenerPorId(id)));
        resultado.setRespuestasCorrectas((short)0);
        resultado.setRespuestasIncorrectas((short)(examen.getPreguntas().size()));
        resultado.setPuntajeFinal(null);
        resultadoRepositorio.save(resultado);
    }

    @Transactional
    public void modificarResultado(Integer id, Short respuestasCorrectas, Short respuestasIncorrectas, Integer puntajeFinal) throws ObjetoNulloExcepcion {

        Resultado resultado = obtenerPorId(id);
        resultadoRepositorio.save(resultado);
        if (resultado.getPuntajeFinal() == null){
            resultado.setRespuestasCorrectas(respuestasCorrectas);
            resultado.setRespuestasIncorrectas(respuestasIncorrectas);
            resultado.setDuracion(resultado.getTiempoFinalizacion().getTime() - resultado.getTiempoInicio().getTime());
            resultado.setPuntajeFinal(puntajeFinal);
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
}
