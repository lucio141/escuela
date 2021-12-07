package com.example.demo.servicios;

import com.example.demo.dto.RolDTO;
import com.example.demo.entidades.Rol;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.RolRepositorio;
import com.example.demo.utilidades.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    public void crearRol(String nombre) {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setNombre(nombre);
        rolRepositorio.save(Mapper.rolDTOAEntidad(rolDTO));
    }

    @Transactional
    public void modificarRol(Integer id, String nombre) throws ObjetoNulloExcepcion {
        RolDTO rolDTO = obtenerPorId(id) ;
        rolDTO.setNombre(nombre);
        rolRepositorio.save(Mapper.rolDTOAEntidad(rolDTO));
    }

    @Transactional
    public List<RolDTO> mostrarRoles() {
        return Mapper.listaRolEntidadADTO(rolRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<RolDTO> mostrarRolesPorAlta(Boolean alta) {
        return Mapper.listaRolEntidadADTO(rolRepositorio.mostrarPorAlta(true));
    }

    @Transactional
    public RolDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        Rol rol = rolRepositorio.findById(id).orElse(null);
        if (rol == null) {
            throw new ObjetoNulloExcepcion("");
        }
        return Mapper.rolEntidadADTO(rol);
    }

    @Transactional
    public void eliminar(int id) {
        rolRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        rolRepositorio.darAlta(id);
    }
}
