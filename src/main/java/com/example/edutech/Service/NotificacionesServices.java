package com.example.edutech.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.edutech.Model.Notificaciones;
import com.example.edutech.Repository.NotificacionesRepository;

@Service
public class NotificacionesServices {
    @Autowired
    private NotificacionesRepository repo;

    public Notificaciones guardar(Notificaciones n){
        return repo.save(n); 
    }

    public Optional<Notificaciones> obtenerNotificaciones(Long id){
        return repo.findById(id);
    }
}
