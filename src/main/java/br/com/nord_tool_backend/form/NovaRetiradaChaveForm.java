package br.com.nord_tool_backend.form;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class NovaRetiradaChaveForm {
    @NotNull private Long idApartamento;
    @NotNull private Long idRetirante;
    @NotNull private Long idLiberador;
}
