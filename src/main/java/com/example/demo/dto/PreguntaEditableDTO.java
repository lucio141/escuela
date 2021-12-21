package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreguntaEditableDTO {
    private String respuestaCorrecta;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
}
