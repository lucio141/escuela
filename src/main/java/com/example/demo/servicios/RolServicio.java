package com.example.demo.servicios;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Rol;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    public void crearRol( String nombre) {
        Rol rol = new Rol();

        rol.setNombre(nombre);

        rolRepositorio.save(rol);
    }

    public void modificarRol(Integer id, String nombre) throws ObjetoNulloExcepcion {
        Rol rol = rolRepositorio.findById(id).orElse(null);

        if(rol == null){throw new ObjetoNulloExcepcion("");}

        rol.setNombre(nombre);

        rolRepositorio.save(rol);
    }

    @Transactional(readOnly = true)
    public List<Rol> obtenerRoles(Boolean alta) {
        return rolRepositorio.mostrarRoles(true);
    }

    @Transactional
    public Rol obtenerPorId(int id) throws ObjetoNulloExcepcion {
        Rol rol = rolRepositorio.findById(id).orElse(null);

        if (rol == null) {
            throw new ObjetoNulloExcepcion("");
        }

        return rol;
    }

    @Transactional
    public void eliminarRol(int id) {
        rolRepositorio.deleteById(id);
    }

    @Transactional
    public void recuperarRol(int id) throws ObjetoNulloExcepcion {
        Rol rol = rolRepositorio.findById(id).orElse(null);

        if(rol==null){
            throw new ObjetoNulloExcepcion("");
        }

        rolRepositorio.recuperarRol(id);
    }
}
