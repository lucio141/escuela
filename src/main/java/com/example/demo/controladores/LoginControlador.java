package com.example.demo.controladores;

import com.example.demo.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/login")
    public ModelAndView login (){
        ModelAndView mav = new ModelAndView(); //Falta HTML
        return mav;
    }

}
