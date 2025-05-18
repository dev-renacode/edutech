package com.example.edutech.Repository;

import com.example.edutech.Model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
