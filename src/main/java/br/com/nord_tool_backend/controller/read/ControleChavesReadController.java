package br.com.nord_tool_backend.controller.read;

import br.com.nord_tool_backend.controller.response.*;
import br.com.nord_tool_backend.dto.*;
import br.com.nord_tool_backend.service.ControleChavesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/controle-chaves")
public class ControleChavesReadController implements BaseResponse {
    private final ControleChavesService service;

    @GetMapping("/apartamentos")
    public ResponseEntity<ApiResponseBody<List<ControleChavesApartamentoDto>>> apartamentos(
            @RequestParam(name = "busca", required = false) String busca,
            @RequestParam(name = "limite", defaultValue = "20") int limite,
            @RequestParam(name = "pagina", defaultValue = "0") int pagina) {
        return ok(service.buscarApartamentos(busca, limite, pagina));
    }
    @GetMapping("/retirantes") public ResponseEntity<ApiResponseBody<List<ControleChavesPessoaDto>>> retirantes(){return ok(service.listarRetirantes());}
    @GetMapping("/liberadores") public ResponseEntity<ApiResponseBody<List<ControleChavesPessoaDto>>> liberadores(){return ok(service.listarLiberadores());}
    @GetMapping("/obras") public ResponseEntity<ApiResponseBody<List<ControleChavesObraDto>>> obras(){return ok(service.listarObras());}
    @GetMapping("/historico") public ResponseEntity<ApiResponseBody<List<RetiradaChaveDto>>> historico(@RequestParam(required=false) String busca,@RequestParam(required=false) String status,@RequestParam(required=false) String obra,@RequestParam(required=false) String condo,@RequestParam(defaultValue="20") int limite,@RequestParam(defaultValue="0") int pagina){return ok(service.historico(busca,status,obraOuCondominio(obra,condo),limite,pagina));}
    @GetMapping("/retiradas/abertas") public ResponseEntity<ApiResponseBody<List<RetiradaChaveDto>>> abertas(@RequestParam(required=false) String busca,@RequestParam(required=false) String obra,@RequestParam(required=false) String condo,@RequestParam(defaultValue="20") int limite,@RequestParam(defaultValue="0") int pagina){return ok(service.historico(busca,"ABERTO",obraOuCondominio(obra,condo),limite,pagina));}
    @GetMapping("/dashboard") public ResponseEntity<ApiResponseBody<ControleChavesDashboardDto>> dashboard(@RequestParam(required=false) String obra,@RequestParam(required=false) String condo,@RequestParam(defaultValue="5") int limiteRecentes){return ok(service.dashboard(obraOuCondominio(obra,condo),limiteRecentes));}
    @GetMapping("/recentes") public ResponseEntity<ApiResponseBody<List<RetiradaChaveDto>>> recentes(@RequestParam(required=false) String obra,@RequestParam(required=false) String condo,@RequestParam(defaultValue="5") int limite){return ok(service.historico(null,null,obraOuCondominio(obra,condo),limite,0));}

    private String obraOuCondominio(String obra, String condo) {
        return obra != null && !obra.trim().isEmpty() ? obra : condo;
    }
}
