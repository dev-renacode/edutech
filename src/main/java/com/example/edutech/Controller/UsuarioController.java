package com.example.edutech.Controller;
import com.example.edutech.Model.Usuario;
import com.example.edutech.Service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin

public class UsuarioController {
    @Autowired
    private UsuarioService serv;

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario u){
        return serv.registrar(u);
    }

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
