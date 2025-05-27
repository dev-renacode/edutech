package com.example.edutech.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reportes {
    private int id;
    private String email;
    private String titulo;
    private String descripcion;
}
