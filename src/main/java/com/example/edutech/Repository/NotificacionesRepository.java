package com.example.edutech.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.edutech.Model.Notificaciones;

public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {
    Optional<Notificaciones> findByUsuario(String usuario);
}
