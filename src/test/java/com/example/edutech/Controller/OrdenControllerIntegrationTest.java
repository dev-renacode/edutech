package com.example.edutech.Controller;

import com.example.edutech.Model.Orden;
import com.example.edutech.Service.OrdenService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenController.class)
public class OrdenControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrdenService ordenService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void guardarOrden_returnGuardado() throws Exception {
        Orden orden = new Orden();
        orden.setUsuario("usuario1");
        orden.setNombreCurso("Curso Java");
        orden.setPrecio(100.0);
        orden.setCantidad(2);
        orden.setTotal(200.0);
        orden.setFecha("2024-06-01");
        when(ordenService.guardar(any(Orden.class))).thenReturn(orden);
        mockMvc.perform(post("/api/v1/ordenes/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orden)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("usuario1"))
                .andExpect(jsonPath("$.nombreCurso").value("Curso Java"))
                .andExpect(jsonPath("$.precio").value(100.0))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.total").value(200.0))
                .andExpect(jsonPath("$.fecha").value("2024-06-01"));
    }

    @Test
    void obtenerOrdenPorId_returnOrden() throws Exception {
        Orden orden = new Orden();
        orden.setId(10L);
        orden.setUsuario("usuario2");
        orden.setNombreCurso("Curso Spring");
        when(ordenService.listarId(10L)).thenReturn(Optional.of(orden));
        mockMvc.perform(get("/api/v1/ordenes/obtener/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("usuario2"))
                .andExpect(jsonPath("$.nombreCurso").value("Curso Spring"));
    }

    @Test
    void vaciarCarrito_returnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/ordenes/vaciar"))
                .andExpect(status().isOk());
    }

    @Test
    void listarTodasLasOrdenes_returnLista() throws Exception {
        Orden orden1 = new Orden();
        orden1.setUsuario("usuario1");
        orden1.setNombreCurso("Curso Java");
        Orden orden2 = new Orden();
        orden2.setUsuario("usuario2");
        orden2.setNombreCurso("Curso Spring");
        List<Orden> lista = Arrays.asList(orden1, orden2);
        when(ordenService.listar()).thenReturn(lista);
        mockMvc.perform(get("/api/v1/ordenes/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuario").value("usuario1"))
                .andExpect(jsonPath("$[1].usuario").value("usuario2"));
    }
}
