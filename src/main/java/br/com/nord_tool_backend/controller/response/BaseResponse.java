package br.com.nord_tool_backend.controller.response;

import org.springframework.http.ResponseEntity;

public interface BaseResponse {

    default <T> ResponseEntity<ApiResponseBody<T>> ok(T body) {
        return ResponseEntity
                .status(NordHttpEnum.HTTP_200.getStatus())
                .body(new ApiResponseBody<>(
                        NordHttpEnum.HTTP_200,
                        NordHttpEnum.HTTP_200.getMensagem(),
                        body
                ));
    }

    default <T> ResponseEntity<ApiResponseBody<T>> created(T body) {
        return ResponseEntity
                .status(NordHttpEnum.HTTP_201.getStatus())
                .body(new ApiResponseBody<>(
                        NordHttpEnum.HTTP_201,
                        NordHttpEnum.HTTP_201.getMensagem(),
                        body
                ));
    }

    default ResponseEntity<ApiResponseBody<Void>> noContent() {
        return ResponseEntity
                .status(NordHttpEnum.HTTP_204.getStatus())
                .body(new ApiResponseBody<>(
                        NordHttpEnum.HTTP_204,
                        NordHttpEnum.HTTP_204.getMensagem(),
                        null
                ));
    }

    default ResponseEntity<ApiResponseBody<Void>> badRequest(String mensagem) {
        return ResponseEntity
                .status(NordHttpEnum.HTTP_400.getStatus())
                .body(new ApiResponseBody<>(
                        NordHttpEnum.HTTP_400,
                        mensagem,
                        null
                ));
    }

    default ResponseEntity<ApiResponseBody<Void>> notFound() {
        return ResponseEntity
                .status(NordHttpEnum.HTTP_404.getStatus())
                .body(new ApiResponseBody<>(
                        NordHttpEnum.HTTP_404,
                        NordHttpEnum.HTTP_404.getMensagem(),
                        null
                ));
    }

    default ResponseEntity<ApiResponseBody<Void>> internalError() {
        return ResponseEntity
                .status(NordHttpEnum.HTTP_500.getStatus())
                .body(new ApiResponseBody<>(
                        NordHttpEnum.HTTP_500,
                        NordHttpEnum.HTTP_500.getMensagem(),
                        null
                ));
    }
}
