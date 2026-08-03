package br.com.nord_tool_backend.repository;

import br.com.nord_tool_backend.domain.CronogramaSemanal;
import br.com.nord_tool_backend.dto.CronogramaSemanalDto;

import java.util.List;

public interface CronogramaSemanalRepository {
    CronogramaSemanalDto salvarCronogramaSemanal(CronogramaSemanal cronogramaSemanal);
    CronogramaSemanalDto alterarCronogramaSemanal(CronogramaSemanal cronogramaSemanal);
    void deletarCronogramaSemanal(Long id);
    CronogramaSemanal buscarPorIdCronogramaSemanal(Long id);
    List<CronogramaSemanal> listarCronogramaSemanal();
}
