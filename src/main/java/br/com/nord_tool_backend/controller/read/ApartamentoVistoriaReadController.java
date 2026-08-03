package br.com.nord_tool_backend.controller.read;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaDto;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaFiltroDto;
import br.com.nord_tool_backend.dto.InfoGeralApartamentoVistoriaDto;
import br.com.nord_tool_backend.service.ApartamentoVistoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nord-tool/apartamentoVistoria")
@Tag(name = "Apartamento Vistoria", description = "Endpoints para listar e listar apartamentos retornados")
public class ApartamentoVistoriaReadController implements BaseResponse {

    private final ApartamentoVistoriaService apartamentoVistoriaService;

    @Operation(summary = "Buscar um apartamento em vistoria")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody<ApartamentoVistoriaDto>> buscarApartamentoVistoria(@PathVariable("id") Long id) {
        ApartamentoVistoriaDto apartamentoVistoriaDto = apartamentoVistoriaService.buscarApartamentoVistoria(id);
        return ok(apartamentoVistoriaDto);
    }

    @Operation(summary = "Listar todos os apartamento em vistoria")
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<ApartamentoVistoriaDto>>> listarApartamentoVistoria() {
        List<ApartamentoVistoriaDto> apartamentoVistoriaDto = apartamentoVistoriaService.listarApartamentoVistoria();
        return ok(apartamentoVistoriaDto);
    }

    @Operation(summary = "Busca uma lista filtrada de todos os apartamento em vistoria")
    @GetMapping("/lista-filtrada")
    public ResponseEntity<ApiResponseBody<List<ApartamentoVistoriaDto>>> listaFiltradaApartamentoVistoria(
            @ModelAttribute ApartamentoVistoriaFiltroDto apartamentoVistoriaFiltroDto,
            @RequestParam(required = false) String filtraTodos,
            @RequestParam(value = "nrPagina", defaultValue = "0") int nrPagina,
            @RequestParam(value = "nrQuantidadePorPagina", defaultValue = "20") int nrQuantidadePorPagina,
            @RequestParam(required = false) String nmOrdenacao)
     {
        List<ApartamentoVistoriaDto> apartamentoVistoriaDto = apartamentoVistoriaService.listarApartamentoVistoriaFiltrado(apartamentoVistoriaFiltroDto, filtraTodos, nrPagina, nrQuantidadePorPagina,nmOrdenacao);
        return ok(apartamentoVistoriaDto);
    }

    @Operation(summary = "Busca informações gerais dos apartamentos")
    @GetMapping("/InfoGeralApartamentoVistoria")
    public ResponseEntity<ApiResponseBody<List<InfoGeralApartamentoVistoriaDto>>> listarInfoGeralApartamentoVistoria(
            @RequestParam(required = false) String dtiApartamentoVistoria,
            @RequestParam(required = false) String dtfApartamentoVistoria)
    {
        List<InfoGeralApartamentoVistoriaDto> infoGeralApartamentoVistoriaDto = apartamentoVistoriaService.listarInfoGeralApartamentoVistoria(dtiApartamentoVistoria, dtfApartamentoVistoria);
        return ok(infoGeralApartamentoVistoriaDto);
    }
}
