package br.com.nord_tool_backend.dto;

import br.com.nord_tool_backend.domain.StatusVistoria;
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
public class StatusVistoriaDto implements Serializable {
    private Long idStatusVistoria;
    private String nmStatusVistoria;

    public static StatusVistoriaDto converterToDomain(StatusVistoria statusVistoria) {
        return StatusVistoriaDto.builder()
                .idStatusVistoria(statusVistoria.getId())
                .nmStatusVistoria(statusVistoria.getNmStatusVistoria())
                .build();
    }
}
