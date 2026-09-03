package br.com.nord_tool_backend.controller;

import br.com.nord_tool_backend.controller.read.ControleChavesReadController;
import br.com.nord_tool_backend.controller.write.ControleChavesWriteController;
import br.com.nord_tool_backend.dto.ControleChavesDashboardDto;
import br.com.nord_tool_backend.dto.RetiradaChaveDto;
import br.com.nord_tool_backend.form.NovaRetiradaChaveForm;
import br.com.nord_tool_backend.service.ControleChavesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class ControleChavesHttpContractTest {

    private ControleChavesService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        service = mock(ControleChavesService.class);
        mockMvc = standaloneSetup(
                new ControleChavesReadController(service),
                new ControleChavesWriteController(service))
                .build();
    }

    @Test
    void endpointsDeLeituraRetornamBodyNoEnvelopePadrao() throws Exception {
        when(service.dashboard(null, 5)).thenReturn(ControleChavesDashboardDto.builder().build());
        when(service.listarObras()).thenReturn(Collections.emptyList());
        when(service.buscarApartamentos(null, 20, 0)).thenReturn(Collections.emptyList());
        when(service.historico(null, null, null, 20, 0)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/controle-chaves/dashboard"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.body").exists());
        mockMvc.perform(get("/api/controle-chaves/obras"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.body").isArray());
        mockMvc.perform(get("/api/controle-chaves/apartamentos"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.body").isArray());
        mockMvc.perform(get("/api/controle-chaves/historico"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.body").isArray());
    }

    @Test
    void endpointsDeEscritaRetornamBodyNoEnvelopePadrao() throws Exception {
        RetiradaChaveDto retirada = RetiradaChaveDto.builder()
                .id(10L).codigo("RET-00010").status("ABERTO").build();
        when(service.criar(any(NovaRetiradaChaveForm.class))).thenReturn(retirada);
        when(service.receber(10L, 3L)).thenReturn(
                RetiradaChaveDto.builder().id(10L).codigo("RET-00010").status("RECEBIDO").build());

        mockMvc.perform(post("/api/controle-chaves/retiradas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(novaRetirada())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.body.codigo").value("RET-00010"));

        mockMvc.perform(patch("/api/controle-chaves/retiradas/10/recebimento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idRecebedor\":3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.status").value("RECEBIDO"));
    }

    private NovaRetiradaChaveForm novaRetirada() {
        NovaRetiradaChaveForm form = new NovaRetiradaChaveForm();
        form.setIdApartamento(1L);
        form.setIdRetirante(2L);
        form.setIdLiberador(3L);
        return form;
    }
}
