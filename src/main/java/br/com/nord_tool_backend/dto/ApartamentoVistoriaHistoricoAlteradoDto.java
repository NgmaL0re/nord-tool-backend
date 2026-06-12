package br.com.nord_tool_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartamentoVistoriaHistoricoAlteradoDto {
    private String nmAtributo;
    private String txAnterior;
    private String txAtual;

    public static ApartamentoVistoriaHistoricoAlteradoDto converter(ApartamentoVistoriaHistoricoConsultaDto apHistoricoConsultaDto) {
        return ApartamentoVistoriaHistoricoAlteradoDto.builder()
                .nmAtributo(apHistoricoConsultaDto.getNmAtributo())
                .txAnterior(apHistoricoConsultaDto.getTxAnterior())
                .txAtual(apHistoricoConsultaDto.getTxAtual())
                .build();
    }
}
