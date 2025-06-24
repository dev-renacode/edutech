package com.example.edutech.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.edutech.Model.Stock;
import com.example.edutech.Service.StockService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;

    @Operation(summary = "Obtener todo el stock", description = "Obtener todo el stock")
    @GetMapping
    public List<Stock> obtenerTodoStock() {
        return stockService.obtenerTodoStock();
    }

    @Operation(summary = "Obtener stock por ID", description = "Obtener stock por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Stock> obtenerStockPorId(@PathVariable Long id) {
        return stockService.obtenerStockPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener stock por ID de producto", description = "Obtener stock por ID de producto")
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Stock> obtenerStockPorIdProducto(@PathVariable Long idProducto) {
        Stock stock = stockService.obtenerStockPorIdProducto(idProducto);
        return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear stock", description = "Crear un nuevo stock")
    @PostMapping
    public Stock crearStock(@RequestBody Stock stock) {
        return stockService.crearStock(stock);
    }

    @Operation(summary = "Actualizar stock", description = "Actualizar un stock existente")
    @PutMapping("/{id}")
    public ResponseEntity<Stock> actualizarStock(@PathVariable Long id, @RequestBody Stock detallesStock) {
        try {
            Stock stockActualizado = stockService.actualizarStock(id, detallesStock);
            return ResponseEntity.ok(stockActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar stock", description = "Eliminar un stock existente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarStock(@PathVariable Long id) {
        try {
            stockService.eliminarStock(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar cantidad de stock", description = "Actualizar la cantidad de un stock existente")
    @PutMapping("/{id}/cantidad/{cantidad}")
    public ResponseEntity<Stock> actualizarCantidad(@PathVariable Long id, @PathVariable int cantidad) {
        try {
            Stock stockActualizado = stockService.actualizarCantidad(id, cantidad);
            return ResponseEntity.ok(stockActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}