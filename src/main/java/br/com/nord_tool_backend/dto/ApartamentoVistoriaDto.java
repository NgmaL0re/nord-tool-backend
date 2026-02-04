package br.com.nord_tool_backend.dto;

import br.com.nord_tool_backend.domain.ApartamentoVistoria;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApartamentoVistoriaDto implements Serializable{

    private Long idApartamentoVistoria;
    private String nmApartamentoVistoria;
    private Integer idDiaSemana;
    private String nmDiaSemana;
    private LocalDate dtApartamentoVigente;
    private String nmHorarioVistoria;
    private Integer idStatusVistoria;
    private String nmStatusVistoria;
    private boolean inMarcarRevistoria;
    private String txObservacaoRevistoria;
    private LocalDate dtRevistoriaVigente;
    private Integer nrTotalRegistros;

    public static ApartamentoVistoriaDto converterToDomain(ApartamentoVistoria apartamentoVistoria ) {
        return ApartamentoVistoriaDto.builder()
                .idApartamentoVistoria(apartamentoVistoria.getId())
                .nmApartamentoVistoria(apartamentoVistoria.getNmApartamentoVistoria())
                .idDiaSemana(apartamentoVistoria.getIdDiaSemana())
                .nmDiaSemana(apartamentoVistoria.getNmDiaSemana())
                .dtApartamentoVigente(apartamentoVistoria.getDtApartamentoVigente())
                .nmHorarioVistoria(apartamentoVistoria.getNmHorarioVistoria())
                .idStatusVistoria(apartamentoVistoria.getIdStatusVistoria())
                .nmStatusVistoria(apartamentoVistoria.getNmStatusVistoria())
                .inMarcarRevistoria(apartamentoVistoria.isInMarcarRevistoria())
                .txObservacaoRevistoria(apartamentoVistoria.getTxObservacaoRevistoria())
                .dtRevistoriaVigente(apartamentoVistoria.getDtRevistoriaVigente())
                .build();
    }
}
