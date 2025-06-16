package com.example.edutech.Controller;

import com.example.edutech.Model.Curso;
import com.example.edutech.Service.CursoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CarritoController.class)
public class CarritoControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    private Curso cursoEjemplo;

    @BeforeEach
    void setUp() {
        cursoEjemplo = new Curso(1, "Clean Code", 2008, "Robert C. Martin", "Prentice Hall", 40, 25000);
    }

    @Test
    void agregarCurso_alCarrito_debeResponderConfirmacion() throws Exception {
        when(cursoService.getCursoid(1)).thenReturn(cursoEjemplo);

        mockMvc.perform(post("/api/v1/carrito/agregar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("El curso se agregó al carrito: Clean Code"));
    }

    @Test
    void verCarrito_debeMostrarCursosAgregados() throws Exception {
        when(cursoService.getCursoid(1)).thenReturn(cursoEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(get("/api/v1/carrito/ver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCurso").value("Clean Code"));
    }

    @Test
    void eliminarCurso_delCarrito_debeEliminarCorrectamente() throws Exception {
        when(cursoService.getCursoid(1)).thenReturn(cursoEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(delete("/api/v1/carrito/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("El curso ha sido eliminado."));
    }

    @Test
    void vaciarCarrito_debeResponderCorrectamente() throws Exception {
        when(cursoService.getCursoid(1)).thenReturn(cursoEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(delete("/api/v1/carrito/vaciar"))
                .andExpect(status().isOk())
                .andExpect(content().string("El carrito ha sido vaciado."));
    }

    @Test
    void totalCursosCarrito_debeRetornarCantidad() throws Exception {
        when(cursoService.getCursoid(1)).thenReturn(cursoEjemplo);
        mockMvc.perform(post("/api/v1/carrito/agregar/1"));

        mockMvc.perform(get("/api/v1/carrito/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }
}