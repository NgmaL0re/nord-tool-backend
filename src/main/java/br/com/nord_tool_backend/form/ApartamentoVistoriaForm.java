package br.com.nord_tool_backend.form;

import br.com.nord_tool_backend.domain.ApartamentoVistoria;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartamentoVistoriaForm {

    private Long idApartamentoVistoria;
    private String nmApartamentoVistoria;
    private Integer idDiaSemana;
    private LocalDate dtApartamentoVigente;
    private String nmHorarioVistoria;
    private Integer idStatusVistoria;
    private String nmStatusVistoria;
    private boolean inMarcarRevistoria;
    private String txObservacaoRevistoria;
    private LocalDate dtRevistoriaVigente;

    public ApartamentoVistoria converterToDomain(){
        return ApartamentoVistoria.builder()
                .id(idApartamentoVistoria)
                .nmApartamentoVistoria(nmApartamentoVistoria)
                .idDiaSemana(idDiaSemana)
                .dtApartamentoVigente(dtApartamentoVigente)
                .nmHorarioVistoria(nmHorarioVistoria)
                .idStatusVistoria(idStatusVistoria)
                .nmStatusVistoria(nmStatusVistoria)
                .inMarcarRevistoria(inMarcarRevistoria)
                .txObservacaoRevistoria(txObservacaoRevistoria)
                .dtRevistoriaVigente(dtRevistoriaVigente)
                .build();
    }
}
