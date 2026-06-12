package br.com.nord_tool_backend.service;

import br.com.nord_tool_backend.dto.ApartamentoVistoriaHistoricoDto;

import java.util.List;

public interface ApartamentoVistoriaHistoricoService {
    List<ApartamentoVistoriaHistoricoDto> buscarHistoricoApartamentoVistoria(Long idApartamentoVistoria);
}
