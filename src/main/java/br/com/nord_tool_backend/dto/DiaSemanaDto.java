package br.com.nord_tool_backend.dto;

import br.com.nord_tool_backend.domain.DiaSemana;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiaSemanaDto implements Serializable {

    private Long idDiaSemana;
    private String nmDiaSemana;

    public static DiaSemanaDto converterToDomain(DiaSemana diaSemana ) {
        return DiaSemanaDto.builder()
                .idDiaSemana(diaSemana.getId())
                .nmDiaSemana(diaSemana.getNmDiaSemana())
                .build();
    }
}
