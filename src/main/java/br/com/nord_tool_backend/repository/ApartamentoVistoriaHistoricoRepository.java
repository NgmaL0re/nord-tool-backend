package br.com.nord_tool_backend.repository;

import br.com.nord_tool_backend.domain.ApartamentoVistoriaHistorico;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaHistoricoConsultaDto;

import java.util.List;

public interface ApartamentoVistoriaHistoricoRepository {
    void salvarTodosHistoricos(List<ApartamentoVistoriaHistorico> lsApVistoriaHistorico);
    List<ApartamentoVistoriaHistoricoConsultaDto> buscarHistorico(Long idApartamentoVistoria);
    List<Integer> buscarNrVersaoHistorico(Long idApartamentoVistoria);
}
