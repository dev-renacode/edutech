package com.example.edutech.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.edutech.Model.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long>{
    Optional<Orden> findById(Long id);
}
