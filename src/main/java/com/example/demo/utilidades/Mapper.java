package com.example.demo.utilidades;

import com.example.demo.dto.InformacionUsuarioDTO;
import com.example.demo.entidades.Usuario;

public class Mapper {

    public static InformacionUsuarioDTO usuarioEntidadADTO (Usuario usuario){
        InformacionUsuarioDTO informacionUsuarioDTO = new InformacionUsuarioDTO();
        informacionUsuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        informacionUsuarioDTO.setApellido(usuario.getApellidoUsuario());
        informacionUsuarioDTO.setMail(usuario.getMail());
        informacionUsuarioDTO.setEdad(usuario.getEdad());
        informacionUsuarioDTO.setResultados(usuario.getResultados());
        return informacionUsuarioDTO;
    }
}
