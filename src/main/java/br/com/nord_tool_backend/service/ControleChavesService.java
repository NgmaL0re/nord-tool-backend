package br.com.nord_tool_backend.service;

import br.com.nord_tool_backend.dto.*;
import br.com.nord_tool_backend.form.NovaRetiradaChaveForm;
import java.util.List;

public interface ControleChavesService {
    List<ControleChavesApartamentoDto> buscarApartamentos(String busca, int limite, int pagina);
    List<ControleChavesPessoaDto> listarRetirantes();
    List<ControleChavesPessoaDto> listarLiberadores();
    List<ControleChavesObraDto> listarObras();
    RetiradaChaveDto criar(NovaRetiradaChaveForm form);
    RetiradaChaveDto receber(Long id, Long idRecebedor);
    List<RetiradaChaveDto> historico(String busca, String status, String obra, int limite, int pagina);
    ControleChavesDashboardDto dashboard(String obra, int limiteRecentes);
}
