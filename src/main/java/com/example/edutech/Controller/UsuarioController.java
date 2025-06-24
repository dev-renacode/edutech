package com.example.edutech.Controller;
import com.example.edutech.Model.Usuario;
import com.example.edutech.Service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
    
//importar las librerias de swagger para la documentacion de las API's
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin
//agregar anotación tag para agrupar el controlador en swagger
@Tag(name = "Usuarios", description = "Operaciones sobre los usuarios")

public class UsuarioController {
    @Autowired
    private UsuarioService serv;

    //agregar anotacion operation para documentar cada endpoint o método REST 
    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en la base de datos")
    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario u){
        return serv.registrar(u);
    }

    @Operation(summary = "Iniciar sesión", description = "Inicia sesión con un usuario existente")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Usuario u){
        Optional<Usuario> user = serv.autenticar(u.getEmail(), u.getPassword());
        Map <String, String> respuesta = new HashMap<>();
        if (user.isPresent()) {
            respuesta.put("result", "OK");
            respuesta.put("nombre", user.get().getNombre());
        } else {
            respuesta.put("result", "error");
        }
        return respuesta;
    }
}
