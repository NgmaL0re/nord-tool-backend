package br.com.nord_tool_backend.handler;

import br.com.nord_tool_backend.controller.response.ApiResponseBody;
import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiResponseBody<String>> handleValidacaoException(
            ValidacaoException ex) {

        NordHttpEnum status = ex.getHttpEnum();

        return ResponseEntity
                .status(status.getStatus())
                .body(
                        new ApiResponseBody<>(
                                status,
                                ex.getMenssage(),
                                ex.getException()
                        )
                );
    }
}
