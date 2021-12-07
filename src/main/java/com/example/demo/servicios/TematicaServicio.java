package com.example.demo.servicios;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.TematicaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/*
Esta clase maneja toda la logica necesaria para el negocio
 */

@Service
public class TematicaServicio {


    @Autowired
    private TematicaRepositorio tematicaRepositorio;

    @Transactional
    public void crearTematica(String nombre){
        Tematica tematica = new Tematica();
        tematica.setNombre(nombre);
        tematicaRepositorio.save(tematica);
    }

    @Transactional
    public void editarTematica(String nombre,Integer id)throws ObjetoNulloExcepcion{
        Tematica tematica = tematicaRepositorio.findById(id).orElse(null) ;
        if(tematica == null){
            throw new ObjetoNulloExcepcion("No se ha encontrado la tematica");
        }
        tematica.setNombre(nombre);
        tematicaRepositorio.save(tematica);
    }

    @Transactional
    public void eliminarTematica(Integer id){
        tematicaRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Tematica> mostrarTematica(Boolean alta)
    {
        return tematicaRepositorio.mostrarTematicas(alta);
    }

    @Transactional
    public void darAlta(Integer id){
        tematicaRepositorio.darAlta(id);
    }

    @Transactional(readOnly = true)
    public Tematica buscarPorId(Integer id)throws ObjetoNulloExcepcion{
        Tematica tematica = tematicaRepositorio.findById(id).orElse(null);
        if(tematica == null){
            throw new ObjetoNulloExcepcion("No se ha encontrado la tematica");
        }

        return tematica;
    }
}
