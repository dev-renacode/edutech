package com.example.edutech.Model;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Usuario {
    
    @Id //Este campo es una clave primeria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String password;

    //Metodo para crear la tabla usuario automaticamente en la base de datos
    public static Optional<Usuario>map(Object o){
        throw new UnsupportedOperationException("Uninplemented method 'map'");
    }
    
}
