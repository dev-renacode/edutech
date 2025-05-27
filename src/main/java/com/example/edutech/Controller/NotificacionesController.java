package com.example.edutech.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.edutech.Model.Notificaciones;
import com.example.edutech.Service.NotificacionesServices;
import java.util.*;

@RestController
@RequestMapping("api/v1/notificaciones")
@CrossOrigin
public class NotificacionesController {
    @Autowired
    private NotificacionesServices notificacionesServices;

    @PostMapping("/guardar")
    public Notificaciones guardar(@RequestBody Notificaciones n){
        return notificacionesServices.guardar(n);
    }

    @GetMapping("/obtener/{id}")
    public Optional<Notificaciones> obtenerNotificaciones(@PathVariable Long id){
        return notificacionesServices.obtenerNotificaciones(id);
    }
    
}
