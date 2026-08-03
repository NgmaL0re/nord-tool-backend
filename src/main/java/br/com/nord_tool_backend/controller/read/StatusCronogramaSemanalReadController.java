package br.com.nord_tool_backend.controller.read;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.domain.enums.StatusCronogramaSemanalEnum;
import br.com.nord_tool_backend.dto.StatusCronogramaSemanalDto;
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
@RequestMapping("/api/v1/nord-tool/statusCronogramaSemanal")
@Tag(name = "Status Cronograma Semanal", description = "Endpoint para listar os status do cronograma semanal")
public class StatusCronogramaSemanalReadController implements BaseResponse  {
    @Operation(summary = "Lista os status do cronograma semanal")
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<StatusCronogramaSemanalDto>>> listaStatusCronogramaSemanal() {
        return ok(StatusCronogramaSemanalEnum.listaStatusCronogramaSemanal());
    }
}
