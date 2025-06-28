package com.example.edutech.Controller;
import com.example.edutech.Model.Usuario;
import com.example.edutech.Service.UsuarioService;
import com.example.edutech.assemblers.usuarioModelAssembler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



//importar las librerias de swagger para la documentacion de las API's
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

//Importar las clases necesarias para HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
// Importar las clases de HATEOAS EntityModel y CollectionModel para manejar los modelos de respuesta
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;

// Importar las clases de responseEntity para manejar respuestas HTTP
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/v2/usuarios") //URL base para las peticiones HTTP 
@CrossOrigin //Permitir peticiones desde cualquier origen
@Tag(name = "Usuario", description = "Operaciones sobre los usuarios del sistema")
public class UsuarioControllerV2 {
    @Autowired
    private UsuarioService serv;

    // Inyectamos el assembler para HATEOAS
    @Autowired
    private usuarioModelAssembler assembler;

    @Operation(summary = "Registrar usuarios", description = "Registra un nuevo usuario en el sistema")
    @PostMapping(value = "/registrar", produces = MediaTypes.HAL_JSON_VALUE) //Método para registrar un usuario en la tabla usuario
    public ResponseEntity<EntityModel<Usuario>> registrar(@RequestBody Usuario u) {
        Usuario creado = serv.registrar(u);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).registrar(creado)).toUri())
                .body(assembler.toModel(creado));
    }


    //Método para autenticar usuario
    @Operation(summary = "Iniciar Sesión", description = "Autentica la sesión de un usuario registrado con email y password")
    @PostMapping(value = "/login", produces = MediaTypes.HAL_JSON_VALUE) //Método para iniciar sesión
    //Recibe un objeto usuario con email y contraseña
    public ResponseEntity<EntityModel<Map<String, String>>> login(@RequestBody Usuario u) {
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map<String, String> response = new HashMap<>();
        if (user.isPresent()) {
            response.put("result", "OK");
            response.put("nombre", user.get().getNombre());
            response.put("email", user.get().getEmail()); // Añadir el email al response para emular el test
            response.put("password", user.get().getPassword()); // Añadir la contraseña al response para emular el test
        } else {
            response.put("result", "Error");
        }
        
        EntityModel<Map<String, String>> model = EntityModel.of(response);
        model.add(linkTo(methodOn(UsuarioControllerV2.class).login(u)).withSelfRel());
        model.add(linkTo(methodOn(UsuarioControllerV2.class).registrar(new Usuario())).withRel("registrar"));

        return ResponseEntity.ok(model);
    }
}