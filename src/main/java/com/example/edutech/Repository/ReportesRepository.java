package com.example.edutech.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.edutech.Model.Reportes;

public interface ReportesRepository extends JpaRepository<Reportes, Long>{
    Optional<Reportes> findById(Long id); 
}
