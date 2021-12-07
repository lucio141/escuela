package com.example.demo.servicios;

import com.example.demo.dto.InformacionUsuarioDTO;
import com.example.demo.entidades.Pregunta;
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
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasenia(contrasenia);
        usuario.setMail(mail);
        usuario.setRol(rol);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificarUsuario(Integer id, String nombreUsuario, String contrasenia, String mail, Rol rol) throws ObjetoNulloExcepcion{
        Usuario usuario = obtenerPorId(id);

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasenia(contrasenia);
        usuario.setMail(mail);
        usuario.setRol(rol);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public List<Usuario> mostrarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> mostrarUsuariosPorAlta(Boolean alta){
        return usuarioRepositorio.mostrarPorAlta(alta);
    }

    @Transactional
    public Usuario obtenerPorId (Integer id) throws ObjetoNulloExcepcion {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

        if (usuario == null) {
            throw new ObjetoNulloExcepcion("");
        }

        return usuario;
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
    public InformacionUsuarioDTO informacionUsuario(Integer id) throws ObjetoNulloExcepcion{

        Usuario usuario = obtenerPorId(id);


        return Mapper.usuarioEntidadADTO(usuario);
    }
}
