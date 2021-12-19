package com.example.demo.servicios;


import com.example.demo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void enviarNuevoUsuario (UsuarioDTO usuario){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(usuario.getMail());
        mensaje.setFrom(from);
        mensaje.setSubject(usuario.getNombre() + " Te damos la bienvenida");
        mensaje.setText(textoNuevoUsuario(usuario));
        sender.send(mensaje);
    }

    private String textoNuevoUsuario(UsuarioDTO usuario){


        return "01000010 01101001 01100101 01101110 01110110 01100101 01101110 01101001 01100100 01101111 \n" +
                "(Bienvenido!)\n\n" +
                usuario.getNombre() + ", Estamos encantados de que te unas a nuestra comunidad! \n" +
                "A partir de ahora serás mundialmente conocido como " + usuario.getNombreUsuario() + "." +
                " Te invitamos a ingresar a la página nuevamente para empezar a poner a prueba tus SKILLS y formar parte de nuestros Rankings!!!!! \n\n" +
                "Te deseamos lo mejor en este proceso \n" +
                "El equipo de SkillTest";
    }
}
