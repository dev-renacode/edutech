package com.example.edutech.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.edutech.Model.Orden;
import com.example.edutech.Repository.OrdenRepository;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository repo;

    public Orden guardar(Orden o){
        return repo.save(o);
    }

    public Optional<Orden> listarId(Long id){
        return repo.findById(id);
    }

    public void vaciarCarrito(){
        repo.deleteAll();
    }

    public List<Orden> listar(){
        return repo.findAll();
    }
}
