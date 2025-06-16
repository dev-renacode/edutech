package com.example.edutech.Controller;

import com.example.edutech.Model.Reportes;
import com.example.edutech.Service.ReportesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportesController.class)
public class ReportesControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportesService reportesService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void guardarReporte_returnGuardado() throws Exception {
        Reportes reporte = new Reportes();
        reporte.setTitulo("Reporte XD");
        reporte.setMensaje("Mensaje XD");
        reporte.setUsuario("Pablo");
        reporte.setFecha("2025-06-01");
        when(reportesService.guardar(any(Reportes.class))).thenReturn(reporte);
        mockMvc.perform(post("/api/v1/reportes/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Reporte XD"))
                .andExpect(jsonPath("$.mensaje").value("Mensaje XD"))
                .andExpect(jsonPath("$.usuario").value("Pablo"))
                .andExpect(jsonPath("$.fecha").value("2025-06-01"));
    }

    @Test
    void obtenerReportePorId_returnReporte() throws Exception {
        Reportes reporte = new Reportes();
        reporte.setId(5L);
        reporte.setTitulo("Reporte XD");
        reporte.setUsuario("Pablo");
        when(reportesService.obtenerReportes(5L)).thenReturn(Optional.of(reporte));
        mockMvc.perform(get("/api/v1/reportes/obtener/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Reporte XD"))
                .andExpect(jsonPath("$.usuario").value("Pablo"));
    }

    @Test
    void listarTodosLosReportes_returnLista() throws Exception {
        Reportes reporte1 = new Reportes();
        reporte1.setTitulo("Reporte XD");
        reporte1.setUsuario("Pablo");
        Reportes reporte2 = new Reportes();
        reporte2.setTitulo("Reporte XD");
        reporte2.setUsuario("usuario2");
        List<Reportes> lista = Arrays.asList(reporte1, reporte2);
        when(reportesService.listarReportes()).thenReturn(lista);
        mockMvc.perform(get("/api/v1/reportes/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Reporte XD"))
                .andExpect(jsonPath("$[0].usuario").value("Pablo"))
                .andExpect(jsonPath("$[1].titulo").value("Reporte XD"))
                .andExpect(jsonPath("$[1].usuario").value("usuario2"));
    }
}
