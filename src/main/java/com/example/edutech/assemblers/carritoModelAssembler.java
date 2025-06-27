package com.example.edutech.assemblers;

//importar las clases necesarias para el modelo y controlador
import com.example.edutech.Model.Curso;
import com.example.edutech.Controller.CarritoController;

//importar las clases static para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//importar la clase EntityModel para usar HATEOAS
import org.springframework.hateoas.EntityModel;

//importar la interfaz RepresentationModelAssembler para crear el ensamblador de CarritoModelAssembler
import org.springframework.hateoas.server.RepresentationModelAssembler;

//importar los stereotipos necesarios para el ensamblador
import org.springframework.stereotype.Component;

//importar la anotacion NonNull para indicar que el método no acepta valores nulos
import org.springframework.lang.NonNull;

//agregar la anotacion Component para indicar que nuestra clase CarritoModelAssembler es un componente spring
@Component
public class carritoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>>{
    
    @Override
    public @NonNull EntityModel<Curso> toModel(Curso curso){
        return EntityModel.of(curso,
        linkTo(methodOn(CarritoController.class).eliminarCurso(curso.getId())).withRel("eliminarCurso"),
        linkTo(methodOn(CarritoController.class).agregarCurso(curso.getId())).withRel("agregarCurso")
        );
    }
}
