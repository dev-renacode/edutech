package com.example.edutech.Controller;

import org.springframework.web.bind.annotation.*;
import com.example.edutech.Model.Orden;
import com.example.edutech.Service.OrdenService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.*;

@RestController
@RequestMapping("/api/v1/ordenes")
@CrossOrigin
public class OrdenController {
    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @Operation(summary = "Guardar orden", description = "Guardar una orden")
    @PostMapping("/guardar")
    public Orden guardar(@RequestBody Orden o){
        return ordenService.guardar(o);
    }
    
    @Operation(summary = "Obtener orden", description = "Obtener una orden por su ID")
    @GetMapping("/obtener/{id}")
    public Optional<Orden> obtenerOrden(@PathVariable Long id){
        return ordenService.listarId(id);
    }

    @Operation(summary = "Vaciar carrito", description = "Vaciar el carrito de compras")
    @DeleteMapping("/vaciar")
    public void vaciarCarrito(){
        ordenService.vaciarCarrito();
    }

    @Operation(summary = "Listar ordenes", description = "Listar todas las ordenes")
    @GetMapping("/listar")
    public List<Orden> listar(){
        return ordenService.listar();
    }
}