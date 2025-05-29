package com.example.edutech.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.edutech.Model.Notificaciones;
import com.example.edutech.Repository.NotificacionesRepository;

@Service
public class NotificacionesService {
    @Autowired
    private NotificacionesRepository repo;

    public Notificaciones guardar(Notificaciones n){
        return repo.save(n);
    }

    public Optional<Notificaciones> listarNotificaciones(String usuario){
        return repo.findByUsuario(usuario);
    }

    public List<Notificaciones> listar(){
        return repo.findAll();
    }

    public void marcarLeida(Long id){
        Notificaciones n = repo.findById(id).orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        n.setLeido(true);
        repo.save(n);
    }
}
