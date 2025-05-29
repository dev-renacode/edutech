package com.example.edutech.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.edutech.Model.Notificaciones;
import com.example.edutech.Service.NotificacionesService;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionesController {
    private final NotificacionesService notificacionesService;

    public NotificacionesController(NotificacionesService notificacionesService){
        this.notificacionesService = notificacionesService;
    }

    @GetMapping("/listar/{usuario}")
    public Optional<Notificaciones> listar(@PathVariable String usuario){
        return notificacionesService.listarNotificaciones(usuario);
    }

    @PostMapping("/guardar")
    public Notificaciones guardar(@RequestBody Notificaciones n){
        return notificacionesService.guardar(n);
    }

    @GetMapping("/listar")
    public List<Notificaciones> listar(){
        return notificacionesService.listar();
    }

    @PutMapping("/marcar-leida/{id}")
    public void marcarLeida(@PathVariable Long id) {
        notificacionesService.marcarLeida(id);
    }
}