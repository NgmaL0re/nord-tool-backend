package br.com.nord_tool_backend.controller.write;

import br.com.nord_tool_backend.controller.response.*;
import br.com.nord_tool_backend.dto.RetiradaChaveDto;
import br.com.nord_tool_backend.form.*;
import br.com.nord_tool_backend.service.ControleChavesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController @RequiredArgsConstructor @RequestMapping("/api/controle-chaves/retiradas")
public class ControleChavesWriteController implements BaseResponse {
    private final ControleChavesService service;
    @PostMapping public ResponseEntity<ApiResponseBody<RetiradaChaveDto>> criar(@Valid @RequestBody NovaRetiradaChaveForm form){return created(service.criar(form));}
    @PatchMapping("/{id}/recebimento") public ResponseEntity<ApiResponseBody<RetiradaChaveDto>> receber(@PathVariable Long id,@Valid @RequestBody RecebimentoChaveForm form){return ok(service.receber(id,form.getIdRecebedor()));}
}
