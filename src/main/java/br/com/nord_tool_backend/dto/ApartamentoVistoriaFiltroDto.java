package br.com.nord_tool_backend.dto;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartamentoVistoriaFiltroDto {
    private String nmApartamentoVistoria;
    private String nmDiaSemana;
    private LocalDate dtApartamentoVigente;
    private String nmHorarioVistoria;
    private String nmStatusVistoria;
    private String txObservacaoRevistoria;
    private LocalDate dtRevistoriaVigente;
}
