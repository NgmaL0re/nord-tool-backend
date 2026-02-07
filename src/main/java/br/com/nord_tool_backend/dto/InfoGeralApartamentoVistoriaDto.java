package br.com.nord_tool_backend.dto;

import br.com.nord_tool_backend.domain.InfoGeralApartamentoVistoria;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoGeralApartamentoVistoriaDto {
    private String nmStatusVistoria;
    private Integer qtApartamentoStatusVistoria;
    private Double pcApartamentoStatusVistoria;
    private Integer nrTotalRegistros;

    public static InfoGeralApartamentoVistoriaDto converterToDomain(InfoGeralApartamentoVistoria infoGeralApartamentoVistoria) {
        return InfoGeralApartamentoVistoriaDto.builder()
                .nmStatusVistoria(infoGeralApartamentoVistoria.getNmStatusVistoria())
                .qtApartamentoStatusVistoria(infoGeralApartamentoVistoria.getQtApartamentoStatusVistoria())
                .pcApartamentoStatusVistoria(infoGeralApartamentoVistoria.getPcApartamentoStatusVistoria())
                .nrTotalRegistros(infoGeralApartamentoVistoria.getNrTotalRegistros())
                .build();
    }
}
