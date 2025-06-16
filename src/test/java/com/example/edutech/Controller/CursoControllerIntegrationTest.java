package com.example.edutech.Controller;

import com.example.edutech.Model.Curso;
import com.example.edutech.Service.CursoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CursoController.class)
public class CursoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listarCursos_debeRetornarListaJson() throws Exception {
        List<Curso> cursos = List.of(
                new Curso(1, "Curso1", 2023, "Docente1", "Descripcion1", 20, 10000),
                new Curso(2, "Curso2", 2024, "Docente2", "Descripcion2", 30, 15000)
        );

        when(cursoService.getCursos()).thenReturn(cursos);

        mockMvc.perform(get("/api/v1/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombreCurso").value("Curso1"));
    }

    @Test
    void agregarCurso_debeGuardarYRetornarCurso() throws Exception {
        Curso curso = new Curso(0, "Nuevo Curso", 2022, "Nuevo Docente", "Nueva Descripcion", 25, 12000);

        when(cursoService.saveCurso(any(Curso.class))).thenReturn(curso);

        mockMvc.perform(post("/api/v1/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCurso").value("Nuevo Curso"));
    }

    @Test
    void buscarCurso_porId_existente() throws Exception {
        Curso curso = new Curso(5, "Buscado", 2021, "Docente", "Descripcion", 15, 18000);

        when(cursoService.getCursoid(5)).thenReturn(curso);

        mockMvc.perform(get("/api/v1/cursos/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCurso").value("Buscado"));
    }

    @Test
    void eliminarCurso_existente() throws Exception {
        when(cursoService.deleteCurso(3)).thenReturn("Curso eliminado");

        mockMvc.perform(delete("/api/v1/cursos/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Curso eliminado"));
    }

    @Test
    void totalCursosV2_debeRetornarCantidad() throws Exception {
        when(cursoService.totalCursosV2()).thenReturn(10);

        mockMvc.perform(get("/api/v1/cursos/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}
