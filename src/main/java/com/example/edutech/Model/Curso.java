package com.example.edutech.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Curso {
    private int id;
    private String nombreCurso;
    private int fechaCreacion;
    private String docenteCurso;
    private String descripcion;
    private int horasDuracion;    
}
