package br.com.nord_tool_backend.controller;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaDto;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaFiltroDto;
import br.com.nord_tool_backend.form.ApartamentoVistoriaForm;
import br.com.nord_tool_backend.service.ApartamentoVistoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nord-tool/apartamentoVistoria")
@Tag(name = "Apartamento Vistoria", description = "Endpoints para listar, buscar e manipular os apartamentos retornados")
public class ApartamentoVistoriaController implements BaseResponse {

    private final ApartamentoVistoriaService apartamentoVistoriaService;

    @Operation(summary = "Cria os apartamentos em vistoria")
    @PostMapping
    public ResponseEntity<ApiResponseBody<ApartamentoVistoriaDto>> criarApartamentoVistoria(@Valid @RequestBody ApartamentoVistoriaForm apartamentoVistoriaForm) {
        ApartamentoVistoriaDto apartamentoVistoriaDto = apartamentoVistoriaService.salvarApartamentoVistoria(apartamentoVistoriaForm);
        return created(apartamentoVistoriaDto);
    }

    @Operation(summary = "Editar os apartamentos em vistoria")
    @PutMapping
    public ResponseEntity<ApiResponseBody<ApartamentoVistoriaDto>> alterarApartamentoVistoria(@Valid @RequestBody ApartamentoVistoriaForm apartamentoVistoriaForm) {
        ApartamentoVistoriaDto apartamentoVistoriaDto = apartamentoVistoriaService.alterarApartamentoVistoria(apartamentoVistoriaForm);
        return ok(apartamentoVistoriaDto);
    }

    @Operation(summary = "Excluir os apartamentos em vistoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody<Void>> deletarApartamentoVistoria(@PathVariable("id") Long id) {
        this.apartamentoVistoriaService.deletarApartamentoVistoria(id);
        return noContent();
    }

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

    @Operation(summary = "Importar planilha de apartamentos para vistoria")
    @PostMapping("/importar")
    public ResponseEntity<ApiResponseBody<String>> importar(@RequestParam("planilha") MultipartFile planilha) throws Exception {
        apartamentoVistoriaService.importarPlanilha(planilha);
        return ok("Planilha importada com sucesso");
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
}
