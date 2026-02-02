package br.com.nord_tool_backend.controller;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.dto.DiaSemanaDto;
import br.com.nord_tool_backend.service.DiaSemanaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nord-tool/diaSemana")
@Tag(name = "Dia Semana", description = "Endpoint para listar os dias da semana")
public class DiaSemanaController implements BaseResponse {

    private final DiaSemanaService diaSemanaService;

    @Operation(summary = "Lista os dias da semana")
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<DiaSemanaDto>>> listaDiaSemana() {
        return ok(diaSemanaService.listarDiaSemana());
    }
}
