package br.com.nord_tool_backend.controller.read;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.domain.enums.CategoriaEnum;
import br.com.nord_tool_backend.dto.CategoriaDto;
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
@RequestMapping("/api/v1/nord-tool/categoria")
@Tag(name = "Categoria", description = "Endpoints para listar as categorias")
public class CategoriaReadController implements BaseResponse {

    @Operation(summary = "Lista as categorias")
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<CategoriaDto>>> listaCategoria() {
        return ok(CategoriaEnum.listarCategoria());
    }
}
