package com.example.edutech.Controller;

import com.example.edutech.Model.Curso;
import com.example.edutech.Service.CursoService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {
    private final List<Curso> carrito = new ArrayList<>();
    
    @Autowired
    private CursoService cursoService;
    
    //Método para agregar un libro al carrito de compras.
    @PostMapping("/agregar/{id}")
    public String agregarCurso(@PathVariable int id) {
        Curso curso = cursoService.getCursoid(id);
        if(curso != null){
            carrito.add(curso);
            return "El curso se agregó al carrito: " + curso.getNombreCurso(); 
        }

        return "El curso no fue encontrado.";
    }
    
    @DeleteMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable int id){
        boolean eliminado = carrito.removeIf(curso -> curso.getId() == id);
        return eliminado ? "El curso ha sido eliminado." : "Curso no encontrado.";
    }

    @GetMapping("/ver")
    public List<Curso> verCarrito() {
        return carrito;
    }

    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "El carrito ha sido vaciado.";
    }
    
    @GetMapping("/total")
    public int totalCursosCarrito() {
        return carrito.size();
    }


}

