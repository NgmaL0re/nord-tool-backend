package br.com.nord_tool_backend.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RecebimentoChaveForm {
    @NotNull
    private Long idRecebedor;
}
