package com.example.demo.servicios;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.UsuarioInformacionDTO;
import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.UsuarioRepositorio;
import com.example.demo.utilidades.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String nombreUsuario, String contrasenia, String mail, Rol rol){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreUsuario(nombreUsuario);
        usuarioDTO.setContrasenia(contrasenia);
        usuarioDTO.setMail(mail);
        usuarioDTO.setRol(rol);
        usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
    }

    @Transactional
    public void modificarUsuario(Integer id, String nombreUsuario, String contrasenia, String mail, Rol rol) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorId(id);
        usuarioDTO.setNombreUsuario(nombreUsuario);
        usuarioDTO.setContrasenia(contrasenia);
        usuarioDTO.setMail(mail);
        usuarioDTO.setRol(rol);
        usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
    }

    @Transactional
    public List<UsuarioDTO> mostrarUsuarios() {
        return Mapper.listaUsuarioEntidadADTO(usuarioRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> mostrarUsuariosPorAlta(Boolean alta){
        return Mapper.listaUsuarioEntidadADTO(usuarioRepositorio.mostrarPorAlta(alta));
    }

    @Transactional
    public UsuarioDTO obtenerPorId (Integer id) throws ObjetoNulloExcepcion {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario == null) {
            throw new ObjetoNulloExcepcion("");
        }
        return Mapper.usuarioEntidadADTO(usuario);
    }

    @Transactional
    public void eliminar(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(Integer id) {
        usuarioRepositorio.darAlta(id);
    }

    @Transactional(readOnly = true)
    public UsuarioInformacionDTO informacionUsuario(Integer id) throws ObjetoNulloExcepcion{
        return Mapper.usuarioDTOAUsuarioInformacionDTO(obtenerPorId(id));
    }
}
