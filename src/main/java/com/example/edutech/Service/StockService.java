package com.example.edutech.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.edutech.Model.Stock;
import com.example.edutech.Model.Curso;
import com.example.edutech.Repository.StockRepository;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CursoService cursoService;

    public List<Stock> obtenerTodoStock() {
        return stockRepository.findAll();
    }

    public Optional<Stock> obtenerStockPorId(Long id) {
        return stockRepository.findById(id);
    }

    public Stock obtenerStockPorIdProducto(Long idProducto) {
        return stockRepository.findByIdProducto(idProducto);
    }

    public Stock crearStock(Stock stock) {
        // Obtener información del curso
        Curso curso = cursoService.getCursoid(stock.getIdProducto().intValue());
        if (curso != null) {
            stock.setNombreProducto(curso.getNombreCurso());
        }
        actualizarEstado(stock);
        return stockRepository.save(stock);
    }

    public Stock actualizarStock(Long id, Stock detallesStock) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Stock no encontrado con id: " + id));
        
        stock.setIdProducto(detallesStock.getIdProducto());
        
        // Obtener información del curso
        Curso curso = cursoService.getCursoid(detallesStock.getIdProducto().intValue());
        if (curso != null) {
            stock.setNombreProducto(curso.getNombreCurso());
        }
        
        stock.setCantidadDisponible(detallesStock.getCantidadDisponible());
        stock.setCantidadMinima(detallesStock.getCantidadMinima());
        actualizarEstado(stock);
        
        return stockRepository.save(stock);
    }

    public void eliminarStock(Long id) {
        stockRepository.deleteById(id);
    }

    public Stock actualizarCantidad(Long id, int cantidad) {
        Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Stock no encontrado con id: " + id));
        
        stock.setCantidadDisponible(cantidad);
        actualizarEstado(stock);
        
        return stockRepository.save(stock);
    }

    private void actualizarEstado(Stock stock) {
        if (stock.getCantidadDisponible() <= 0) {
            stock.setEstado("AGOTADO");
        } else if (stock.getCantidadDisponible() <= stock.getCantidadMinima()) {
            stock.setEstado("BAJO_STOCK");
        } else {
            stock.setEstado("DISPONIBLE");
        }
    }
}