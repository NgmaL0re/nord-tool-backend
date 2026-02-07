package br.com.nord_tool_backend.controller.write;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.BaseResponse;
import br.com.nord_tool_backend.service.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nord-tool/cache")
@Tag(name = "Cache Manager", description = "Endpoints para limpar cache")
public class CacheWriteController implements BaseResponse {

    private final CacheService cacheService;

    @Operation(summary = "Limpar cache especifico")
    @PutMapping
    public ResponseEntity<ApiResponseBody<Void>> limparCache(@PathVariable String cacheName) {
        cacheService.limparCache(cacheName);
        return noContent();
    }

    @Operation(summary = "Limpar todos os cache")
    @PutMapping("/limparTodos")
    public ResponseEntity<ApiResponseBody<Void>> limparTodos() {
        cacheService.limparTodos();
        return noContent();
    }
}
