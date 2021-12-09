package com.example.demo.utilidades;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.RolDTO;
import com.example.demo.dto.UsuarioInformacionDTO;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static UsuarioDTO usuarioEntidadADTO (Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setMail(usuario.getMail());
        usuarioDTO.setEdad(usuario.getEdad());
        usuarioDTO.setContrasenia(usuario.getContrasenia());
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setResultados(usuario.getResultados());
        return usuarioDTO;
    }

    public static Usuario usuarioDTOAEntidad (UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setMail(usuarioDTO.getMail());
        usuario.setEdad(usuarioDTO.getEdad());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setResultados(usuarioDTO.getResultados());
        return usuario;
    }

    public static UsuarioInformacionDTO usuarioDTOAUsuarioInformacionDTO(UsuarioDTO usuarioDTO){
        UsuarioInformacionDTO usuarioInformacionDTO = new UsuarioInformacionDTO();
        usuarioInformacionDTO.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuarioInformacionDTO.setApellido(usuarioDTO.getApellido());
        usuarioInformacionDTO.setMail(usuarioDTO.getMail());
        usuarioInformacionDTO.setNombre(usuarioDTO.getNombre());
        usuarioInformacionDTO.setEdad(usuarioDTO.getEdad());
        usuarioInformacionDTO.setResultados(usuarioDTO.getResultados());
        usuarioInformacionDTO.setTelefono(usuarioDTO.getTelefono());
        return usuarioInformacionDTO;
    }

    public static List<UsuarioDTO> listaUsuarioEntidadADTO(List<Usuario> usuarios){
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDTOS.add(usuarioEntidadADTO(usuario));
        }
        return usuarioDTOS;
    }

    public static Rol rolDTOAEntidad(RolDTO rolDTO){
        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());
        return rol;
    }

    public static RolDTO rolEntidadADTO(Rol rol){
        RolDTO rolDTO = new RolDTO();
        rolDTO.setNombre(rol.getNombre());
        rolDTO.setId(rol.getId());
        return rolDTO;
    }

    public static List<RolDTO> listaRolEntidadADTO (List<Rol> roles){
        List<RolDTO> rolesDTO = new ArrayList<>();
        for (Rol rol: roles) {
               rolesDTO.add(rolEntidadADTO(rol));
        }
        return rolesDTO;
    }
}
