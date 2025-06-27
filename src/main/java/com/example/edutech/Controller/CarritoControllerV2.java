package com.example.edutech.Controller;

import com.example.edutech.Model.Curso;
import com.example.edutech.Service.CursoService;
import com.example.edutech.assemblers.carritoModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.*;
import java.util.stream.Collectors;

//importar las librerias de swagger para la documentacion de las API's
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v2/carrito")
//agregar anotación tag para agrupar el controlador en swagger
@Tag(name = "Carrito de compras", description = "Operaciones sobre el carrito de compras")
public class CarritoControllerV2 {
    private final List<Curso> carrito = new ArrayList<>();
    
    @Autowired
    private CursoService cursoService;

    @Autowired
    private carritoModelAssembler assembler;
    
    //Método para agregar un libro al carrito de compras.
    //agregar anotacion operation para documentar cada endpoint o método REST 
    @Operation(summary = "Agregar un curso al carrito de compras", description = "Agrega un curso al carrito de compras por su id")
    @PostMapping("/agregar/{id}")
    public ResponseEntity<String> agregarCurso(@PathVariable int id) {
        Curso curso = cursoService.getCursoid(id);
        if(curso != null){
            carrito.add(curso);
            return ResponseEntity.ok("El curso se agregó al carrito: " + curso.getNombreCurso()); 
        }

        return ResponseEntity.badRequest().body("El curso no fue encontrado.");
    }

    @Operation(summary = "Muestrar los productos agregados al carrito de compras", description = "Muestra todos los libros en el carrito de compras")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Curso>> verCarrito() {
        List<EntityModel<Curso>> curso = carrito.stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(curso,
                linkTo(methodOn(CarritoControllerV2.class).verCarrito()).withSelfRel());
    }
    
    @Operation(summary = "Eliminar un curso del carrito de compras", description = "Elimina un curso del carrito de compras por su id")
    @DeleteMapping(value = "/eliminar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> eliminarCurso(@PathVariable int id){
        boolean eliminado = carrito.removeIf(libro -> libro.getId() == id);
        return eliminado ? ResponseEntity.ok("Libro eliminado del carrito")
        : ResponseEntity.badRequest().body("Libro no estaba en el carrito");
    }


    @Operation(summary = "Vaciar el carrito de compras", description = "Elimina todos los cursos del carrito de compras")
    @DeleteMapping(value = "/vaciar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> vaciarCarrito() {
        carrito.clear();
        return ResponseEntity.ok("Carrito vaciado");
    }
    
    @Operation(summary = "Obtener el total de cursos en el carrito de compras", description = "Obtiene el número total de cursos en el carrito de compras")
    @GetMapping(value = "/total", produces = MediaTypes.HAL_JSON_VALUE)
    public int totalCursosCarrito() {
        return carrito.size();
    }


}

