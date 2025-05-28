package com.example.edutech.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.edutech.Model.Reportes;
import com.example.edutech.Repository.ReportesRepository;

@Service
public class ReportesService {
    @Autowired
    private ReportesRepository repo;

    public Reportes guardar(Reportes r){
        return repo.save(r); 
    }

    public Optional<Reportes> obtenerReportes(Long id){
        return repo.findById(id);
    }
}
