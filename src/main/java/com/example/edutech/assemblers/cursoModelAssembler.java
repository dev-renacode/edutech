package com.example.edutech.assemblers;

//importar las clases necesarias para el modelo y controlador
import com.example.edutech.Model.Curso;
import com.example.edutech.Controller.CursoController;

//importar las clases static para crear los enlaces HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//importar la clase EntityModel para usar HATEOAS
import org.springframework.hateoas.EntityModel;

//importar la interfaz RepresentationModelAssembler para crear el emsamblador de CursoModelAssembler
import org.springframework.hateoas.server.RepresentationModelAssembler;

//importar los stereotipos necesarios para el ensamblador
import org.springframework.stereotype.Component;

//importar la anotacion NonNull para indicar que el método no acepta valores nulos
import org.springframework.lang.NonNull;

//agregar la anotacion Component para indicar que nuestra clase CursoModelAssembler es un componente spring
@Component
//la clase CursoModelAssembler debe implementar a RepresentationModelAssembler para convertir un objeto Curso en un EntityModel
public class cursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>>{
    //Anotacion Override para indicar que este método implementa un método de la interfaz RepresentationModelAssembler
    @Override
    //Metodo para convertir un objeto de Curso en una EntityModel. Usamos la anotacio NonNull para no aceptar valores nulos, usamos linkTo con el metodo MethodOn para crear los enlaces HATEOAS para cada metodo REST del controlador
    public @NonNull EntityModel<Curso> toModel(Curso curso){
        return EntityModel.of(curso,
            linkTo(methodOn(CursoController.class).buscarCurso(curso.getId())).withSelfRel(),
            linkTo(methodOn(CursoController.class).listarCursos()).withRel("listarCursos"),
            linkTo(methodOn(CursoController.class).actualizarCurso(curso.getId(), curso)).withRel("Actualizar"),
            linkTo(methodOn(CursoController.class).eliminarCurso(curso.getId())).withRel("Eliminar")
            );

    }
    
}