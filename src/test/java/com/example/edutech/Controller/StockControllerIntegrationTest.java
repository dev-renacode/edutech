package com.example.edutech.Controller;

import com.example.edutech.Model.Stock;
import com.example.edutech.Service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
public class StockControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StockService stockService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodoStock_returnLista() throws Exception {
        Stock stock1 = new Stock(1L, 1L, "curso1", 50, 10, "DISPONIBLE");
        Stock stock2 = new Stock(2L, 2L, "curso2", 0, 5, "AGOTADO");
        List<Stock> lista = Arrays.asList(stock1, stock2);
        when(stockService.obtenerTodoStock()).thenReturn(lista);
        mockMvc.perform(get("/api/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProducto").value("curso1"))
                .andExpect(jsonPath("$[1].nombreProducto").value("curso2"));
    }

    @Test
    void obtenerStockPorId_returnStock() throws Exception {
        Stock stock = new Stock(1L, 1L, "curso1", 50, 10, "DISPONIBLE");
        when(stockService.obtenerStockPorId(1L)).thenReturn(Optional.of(stock));
        mockMvc.perform(get("/api/stock/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("curso1"));
    }

    @Test
    void obtenerStockPorId_returnNotFound() throws Exception {
        when(stockService.obtenerStockPorId(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/stock/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerStockPorIdProducto_returnStock() throws Exception {
        Stock stock = new Stock(1L, 1L, "curso1", 50, 10, "DISPONIBLE");
        when(stockService.obtenerStockPorIdProducto(1L)).thenReturn(stock);
        mockMvc.perform(get("/api/stock/producto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("curso1"));
    }

    @Test
    void obtenerStockPorIdProducto_returnNotFound() throws Exception {
        when(stockService.obtenerStockPorIdProducto(999L)).thenReturn(null);
        mockMvc.perform(get("/api/stock/producto/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearStock_returnCreado() throws Exception {
        Stock stock = new Stock(null, 1L, "curso1", 50, 10, "DISPONIBLE");
        when(stockService.crearStock(any(Stock.class))).thenReturn(stock);
        mockMvc.perform(post("/api/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("curso1"));
    }

    @Test
    void actualizarStock_returnActualizado() throws Exception {
        Stock detalles = new Stock(null, 1L, "curso1", 50, 10, "DISPONIBLE");
        Stock actualizado = new Stock(1L, 1L, "curso1", 50, 10, "DISPONIBLE");
        when(stockService.actualizarStock(eq(1L), any(Stock.class))).thenReturn(actualizado);
        mockMvc.perform(put("/api/stock/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(detalles)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadDisponible").value(50));
    }

    @Test
    void actualizarStock_returnNotFound() throws Exception {
        when(stockService.actualizarStock(eq(99L), any(Stock.class))).thenThrow(new RuntimeException("Stock no encontrado"));
        mockMvc.perform(put("/api/stock/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Stock())))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarStock_returnOk() throws Exception {
        mockMvc.perform(delete("/api/stock/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarStock_returnNotFound() throws Exception {
        doThrow(new RuntimeException("Stock no encontrado")).when(stockService).eliminarStock(99L);
        mockMvc.perform(delete("/api/stock/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarCantidad_returnActualizado() throws Exception {
        Stock actualizado = new Stock(1L, 1L, "curso1", 50, 10, "DISPONIBLE");
        when(stockService.actualizarCantidad(1L, 50)).thenReturn(actualizado);
        mockMvc.perform(put("/api/stock/1/cantidad/50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadDisponible").value(50));
    }

    @Test
    void actualizarCantidad_returnNotFound() throws Exception {
        when(stockService.actualizarCantidad(99L, 50)).thenThrow(new RuntimeException("Stock no encontrado"));
        mockMvc.perform(put("/api/stock/99/cantidad/50"))
                .andExpect(status().isNotFound());
    }
}
