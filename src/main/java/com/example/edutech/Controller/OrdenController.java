package com.example.edutech.Controller;

import org.springframework.web.bind.annotation.*;
import com.example.edutech.Model.Orden;
import com.example.edutech.Service.OrdenService;
import java.util.*;

@RestController
@RequestMapping("/api/v1/ordenes")
@CrossOrigin
public class OrdenController {
    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @PostMapping("/guardar")
    public Orden guardar(@RequestBody Orden o){
        return ordenService.guardar(o);
    }
    
    @GetMapping("/obtener/{id}")
    public Optional<Orden> obtenerOrden(@PathVariable Long id){
        return ordenService.listarId(id);
    }

    @DeleteMapping("/vaciar")
    public void vaciarCarrito(){
        ordenService.vaciarCarrito();
    }

    @GetMapping("/listar")
    public List<Orden> listar(){
        return ordenService.listar();
    }
}