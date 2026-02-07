package br.com.nord_tool_backend.controller.read;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.dto.StatusVistoriaDto;
import br.com.nord_tool_backend.service.StatusVistoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nord-tool/statusVistoria")
@Tag(name = "Status Vistoria", description = "Endpoint para listar os status de vistoria dos apartamentos")
public class StatusVistoriaReadController implements BaseResponse {

    private final StatusVistoriaService statusVistoriaService;

    @Operation(summary = "Lista os Status de vistoria")
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<StatusVistoriaDto>>> listarStatusVistoria() {
        return ok(statusVistoriaService.listarStatusVistoria());
    }
}
