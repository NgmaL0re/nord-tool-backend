package br.com.nord_tool_backend.controller;

import br.com.nord_tool_backend.controller.read.ColaboradorReadController;
import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.controller.write.ColaboradorWriteController;
import br.com.nord_tool_backend.dto.ColaboradorDto;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import br.com.nord_tool_backend.handler.GlobalExceptionHandler;
import br.com.nord_tool_backend.service.ColaboradorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ColaboradorHttpContractTest {
    private ColaboradorService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        service = mock(ColaboradorService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(
                        new ColaboradorReadController(service),
                        new ColaboradorWriteController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void deveSerializarEnvelopeEContratoDoColaborador() throws Exception {
        ColaboradorDto dto = ColaboradorDto.builder()
                .id(10L)
                .nome("João Silva")
                .celular("11999999999")
                .idEmpresa(1L)
                .nomeEmpresa("Nord")
                .idCargo(2L)
                .nomeCargo("Analista")
                .idPermissao(3L)
                .nomePermissao("Administrador")
                .build();
        when(service.buscarPorIdColaborador(10L)).thenReturn(dto);

        mockMvc.perform(get("/api/colaboradores/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nrStatus").value(200))
                .andExpect(jsonPath("$.txMensagem").exists())
                .andExpect(jsonPath("$.body.id").value(10))
                .andExpect(jsonPath("$.body.nome").value("João Silva"))
                .andExpect(jsonPath("$.body.idEmpresa").value(1))
                .andExpect(jsonPath("$.body.idCargo").value(2))
                .andExpect(jsonPath("$.body.idPermissao").value(3));
    }

    @Test
    void deveRetornarHttp400ParaContratoInvalido() throws Exception {
        mockMvc.perform(post("/api/colaboradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\",\"celular\":\"123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nrStatus").value(400))
                .andExpect(jsonPath("$.txMensagem").exists());
    }

    @Test
    void deveRetornarHttp404ParaIdInexistente() throws Exception {
        when(service.buscarPorIdColaborador(999L)).thenThrow(
                new ValidacaoException(NordHttpEnum.HTTP_404,
                        "Colaborador não encontrado", "999"));

        mockMvc.perform(get("/api/colaboradores/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.nrStatus").value(404))
                .andExpect(jsonPath("$.txMensagem").value("Colaborador não encontrado"));
    }
}
