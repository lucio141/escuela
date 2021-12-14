package com.example.demo.utilidades;

import com.example.demo.excepciones.ValidacionCampExcepcion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilidad {

    //Aca van los metodos de utilidad como regex (validaciones) por ej

    public void validarUsuario(String nombreUsuario) throws ValidacionCampExcepcion {

        Pattern pat = Pattern.compile("^([-_a-z-ù0-9]+)$");
        Matcher mat = pat.matcher(nombreUsuario);
        if (!mat.matches()) {
            throw new ValidacionCampExcepcion("El formato de usuario es inconrrecto");
        }
    }
}
