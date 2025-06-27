package com.example.edutech.assemblers;

//importar las clases necesarias para el modelo y controlador
import com.example.edutech.Model.Usuario;
import com.example.edutech.Controller.UsuarioController;

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

public class usuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{
    @Override
    public @NonNull EntityModel<Usuario> toModel(Usuario u){
        return EntityModel.of(u,
        linkTo(methodOn(usuarioController.class).registrarUsuario(null)).withSelfRel(),
        linkTo(methodOn(usuarioController.class).login(u)).withRel("Login"),
        )

    }
}
