package com.example.edutech.Controller;

import com.example.edutech.Model.Usuario;
import com.example.edutech.Service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsuarioService usuarioService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test 
    void registrarUsuario_returnGuardado() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Pablo");
        nuevoUsuario.setEmail("pablo@gmail.com");
        nuevoUsuario.setPassword("1234");
        when(usuarioService.registrar(any(Usuario.class))).thenReturn(nuevoUsuario);
        mockMvc.perform(post("/api/v1/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pablo"))
                .andExpect(jsonPath("$.email").value("pablo@gmail.com"))
                .andExpect(jsonPath("$.password").value("1234"));
    }

    @Test
    void loginUsuario_returnOK() throws Exception {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setNombre("Pablo");
        usuarioExistente.setEmail("pablo@gmail.com");
        usuarioExistente.setPassword("1234");
        when(usuarioService.autenticar("pablo@gmail.com", "1234"))
                .thenReturn(Optional.of(usuarioExistente));
        mockMvc.perform(post("/api/v1/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioExistente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("OK"))
                .andExpect(jsonPath("$.nombre").value("Pablo"));
    }

    @Test
    void loginUsuario_returnError() throws Exception {
        Usuario usuarioInexistente = new Usuario();
        usuarioInexistente.setEmail("noexiste@gmail.com");
        usuarioInexistente.setPassword("1234");
        when(usuarioService.autenticar("noexiste@gmail.com", "1234"))
                .thenReturn(Optional.empty());
        mockMvc.perform(post("/api/v1/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioInexistente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("error"));
    }
}