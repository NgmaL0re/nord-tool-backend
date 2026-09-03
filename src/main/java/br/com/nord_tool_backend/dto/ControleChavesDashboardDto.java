package br.com.nord_tool_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ControleChavesDashboardDto {
    private long chavesEmCampo;
    private long chavesNoQuadro;
    private long chavesEntregues;
    private List<RetiradaChaveDto> retiradasRecentes;
}
