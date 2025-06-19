package com.example.edutech.Controller;

import com.example.edutech.Model.Curso;
import com.example.edutech.Service.CursoService;

import java.util.*;

//importar las librerias de swagger para la documentacion de las API's
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/carrito")
//agregar anotación tag para agrupar el controlador en swagger
@Tag(name = "Carrito de compras", description = "Operaciones sobre el carrito de compras")

public class CarritoController {
    private final List<Curso> carrito = new ArrayList<>();
    
    @Autowired
    private CursoService cursoService;
    
    //Método para agregar un libro al carrito de compras.
    //agregar anotacion operation para documentar cada endpoint o método REST 
    @Operation(summary = "Agregar un curso al carrito de compras", description = "Agrega un curso al carrito de compras por su id")
    @PostMapping("/agregar/{id}")
    public String agregarCurso(@PathVariable int id) {
        Curso curso = cursoService.getCursoid(id);
        if(curso != null){
            carrito.add(curso);
            return "El curso se agregó al carrito: " + curso.getNombreCurso(); 
        }

        return "El curso no fue encontrado.";
    }
    
    @Operation(summary = "Eliminar un curso del carrito de compras", description = "Elimina un curso del carrito de compras por su id")
    @DeleteMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable int id){
        Iterator<Curso> iterator = carrito.iterator();
        while (iterator.hasNext()) {
            Curso curso = iterator.next();
            if (curso.getId() == id) {
                iterator.remove();
                return "El curso ha sido eliminado.";
            }
        }
        return "Curso no encontrado.";
    }
    @Operation(summary = "Ver el carrito de compras", description = "Muestra los cursos que se han agregado al carrito de compras")
    @GetMapping("/ver")
    public List<Curso> verCarrito() {
        return carrito;
    }

    @Operation(summary = "Vaciar el carrito de compras", description = "Elimina todos los cursos del carrito de compras")
    @DeleteMapping("/vaciar")
    public String vaciarCarrito() {
        carrito.clear();
        return "El carrito ha sido vaciado.";
    }
    
    @Operation(summary = "Obtener el total de cursos en el carrito de compras", description = "Obtiene el número total de cursos en el carrito de compras")
    @GetMapping("/total")
    public int totalCursosCarrito() {
        return carrito.size();
    }


}

