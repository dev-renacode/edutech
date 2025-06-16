package com.example.edutech.Controller;

import com.example.edutech.Model.Notificaciones;
import com.example.edutech.Service.NotificacionesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;



@WebMvcTest(NotificacionesController.class)
public class NotificacionesControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotificacionesService notificacionesService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void guardarNotificacion_returnGuardado() throws Exception {
        Notificaciones noti = new Notificaciones();
        noti.setMensaje("Mensaje de prueba");
        noti.setUsuario("usuario1");
        noti.setIdOrden(1L);
        noti.setLeido(false);
        when(notificacionesService.guardar(any(Notificaciones.class))).thenReturn(noti);
        mockMvc.perform(post("/api/v1/notificaciones/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noti)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Mensaje de prueba"))
                .andExpect(jsonPath("$.usuario").value("usuario1"))
                .andExpect(jsonPath("$.leido").value(false));
    }

    @Test
    void listarNotificacionesPorUsuario_returnNotificacion() throws Exception {
        Notificaciones noti = new Notificaciones();
        noti.setMensaje("Mensaje de usuario");
        noti.setUsuario("usuario2");
        noti.setIdOrden(2L);
        noti.setLeido(false);
        when(notificacionesService.listarNotificaciones("usuario2")).thenReturn(Optional.of(noti));
        mockMvc.perform(get("/api/v1/notificaciones/listar/usuario2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Mensaje de usuario"))
                .andExpect(jsonPath("$.usuario").value("usuario2"));
    }

    @Test
    void listarTodasNotificaciones_returnLista() throws Exception {
        Notificaciones noti1 = new Notificaciones();
        noti1.setMensaje("Mensaje 1");
        noti1.setUsuario("usuario1");
        noti1.setIdOrden(1L);
        noti1.setLeido(false);
        Notificaciones noti2 = new Notificaciones();
        noti2.setMensaje("Mensaje 2");
        noti2.setUsuario("usuario2");
        noti2.setIdOrden(2L);
        noti2.setLeido(true);
        List<Notificaciones> lista = Arrays.asList(noti1, noti2);
        when(notificacionesService.listar()).thenReturn(lista);
        mockMvc.perform(get("/api/v1/notificaciones/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensaje").value("Mensaje 1"))
                .andExpect(jsonPath("$[1].mensaje").value("Mensaje 2"));
    }

    @Test
    void marcarNotificacionComoLeida_returnOk() throws Exception {
        // No retorna nada, solo status OK
        mockMvc.perform(put("/api/v1/notificaciones/marcar-leida/5"))
                .andExpect(status().isOk());
    }
}
