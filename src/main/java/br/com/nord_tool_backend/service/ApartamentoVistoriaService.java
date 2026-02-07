package br.com.nord_tool_backend.service;

import br.com.nord_tool_backend.dto.ApartamentoVistoriaDto;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaFiltroDto;
import br.com.nord_tool_backend.dto.InfoGeralApartamentoVistoriaDto;
import br.com.nord_tool_backend.form.ApartamentoVistoriaForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApartamentoVistoriaService {
    ApartamentoVistoriaDto salvarApartamentoVistoria(ApartamentoVistoriaForm apartamentoVistoriaForm);
    ApartamentoVistoriaDto alterarApartamentoVistoria(ApartamentoVistoriaForm apartamentoVistoriaForm);
    void deletarApartamentoVistoria(Long id);
    ApartamentoVistoriaDto buscarApartamentoVistoria(Long id);
    List<ApartamentoVistoriaDto> listarApartamentoVistoria();
    void importarPlanilha(MultipartFile planilha) throws Exception;
    List<ApartamentoVistoriaDto> listarApartamentoVistoriaFiltrado(ApartamentoVistoriaFiltroDto apartamentoVistoriaFiltroDto,
                                                                   String filtraTodos, int nrPagina, int nrQuantidadePorPagina, String nmOrdenacao);
    List<InfoGeralApartamentoVistoriaDto> listarInfoGeralApartamentoVistoria(String dtiApartamentoVistoria, String dtfApartamentoVistoria);

}
