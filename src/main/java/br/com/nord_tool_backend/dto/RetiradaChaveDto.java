package br.com.nord_tool_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RetiradaChaveDto {
    private Long id;
    private String codigo;
    private ControleChavesApartamentoDto apartamento;
    private ControleChavesPessoaDto retirante;
    private ControleChavesPessoaDto liberador;
    private ControleChavesPessoaDto recebedor;
    private LocalDateTime dataRetirada;
    private LocalDateTime dataRecebimento;
    private String status;
}
