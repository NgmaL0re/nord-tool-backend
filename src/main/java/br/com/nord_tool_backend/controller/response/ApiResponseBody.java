package br.com.nord_tool_backend.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseBody<T> {
    private static final long serialVersionUID = 1L;
    private LocalDateTime timestamp;
    private Integer nrStatus;
    @JsonProperty("body")
    private transient T body;
    private String txMensagem;

    public ApiResponseBody(NordHttpEnum status, String menssage, final T body) {
        this.timestamp = LocalDateTime.now();
        this.nrStatus = status.getStatus().value();
        this.txMensagem = menssage;
        this.body = body;
    }
}
