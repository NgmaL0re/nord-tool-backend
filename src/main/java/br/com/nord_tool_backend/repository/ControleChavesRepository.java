package br.com.nord_tool_backend.repository;

import br.com.nord_tool_backend.dto.*;
import java.util.List;
import java.util.Optional;

public interface ControleChavesRepository {
    List<ControleChavesApartamentoDto> buscarApartamentos(String busca, int limite, int pagina);
    Optional<ControleChavesApartamentoDto> buscarApartamento(Long id);
    Optional<ControleChavesPessoaDto> buscarPessoa(Long id);
    List<ControleChavesPessoaDto> listarRetirantes();
    List<ControleChavesPessoaDto> listarLiberadores();
    List<String> listarObras();
    RetiradaChaveDto criar(Long apartamento, Long retirante, Long liberador);
    Optional<RetiradaChaveDto> buscarRetirada(Long id);
    boolean receber(Long id, Long recebedor);
    List<RetiradaChaveDto> historico(String busca, String status, String obra, int limite, int pagina);
    long contarAbertas(String obra);
    long contarChavesEntregues(String obra);
    long contarChavesNoQuadro(String obra);
}
