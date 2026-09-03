package br.com.nord_tool_backend.controller;

import br.com.nord_tool_backend.controller.read.ControleChavesReadController;
import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.dto.ControleChavesApartamentoDto;
import br.com.nord_tool_backend.service.ControleChavesService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ControleChavesApartamentoControllerTest {

    @Test
    void deveRetornarApartamentosNoEnvelopePadrao() {
        ControleChavesService service = mock(ControleChavesService.class);
        List<ControleChavesApartamentoDto> apartamentos = Collections.singletonList(
                new ControleChavesApartamentoDto(42L, "EN-01-0204"));
        when(service.buscarApartamentos("0204", 10, 1)).thenReturn(apartamentos);

        ResponseEntity<ApiResponseBody<List<ControleChavesApartamentoDto>>> response =
                new ControleChavesReadController(service).apartamentos("0204", 10, 1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(200, response.getBody().getNrStatus());
        assertEquals(apartamentos, response.getBody().getBody());
        verify(service).buscarApartamentos("0204", 10, 1);
    }

    @Test
    void deveRetornarListaVaziaSemTransformarEmErro() {
        ControleChavesService service = mock(ControleChavesService.class);
        when(service.buscarApartamentos("inexistente", 20, 0)).thenReturn(Collections.emptyList());

        ResponseEntity<ApiResponseBody<List<ControleChavesApartamentoDto>>> response =
                new ControleChavesReadController(service).apartamentos("inexistente", 20, 0);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.emptyList(), response.getBody().getBody());
    }
}
