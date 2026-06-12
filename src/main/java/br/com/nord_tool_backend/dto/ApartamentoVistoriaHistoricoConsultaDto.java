package br.com.nord_tool_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartamentoVistoriaHistoricoConsultaDto {
    private Integer nrVersao;
    private String nmAtributo;
    private String txAnterior;
    private String txAtual;
    private LocalDateTime dtAlteracao;
    private String nmUsuario;
}
