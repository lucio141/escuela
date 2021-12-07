package com.example.demo.servicios;

import com.example.demo.entidades.Pregunta;
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
        Rol rol = obtenerPorId(id);

        rol.setNombre(nombre);

        rolRepositorio.save(rol);
    }

    @Transactional
    public List<Rol> mostrarRoles() {
        return rolRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Rol> mostrarRolesPorAlta(Boolean alta) {
        return rolRepositorio.mostrarPorAlta(true);
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
    public void eliminar(int id) {
        rolRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        Rol rol = obtenerPorId(id);

        rolRepositorio.darAlta(id);
    }
}
