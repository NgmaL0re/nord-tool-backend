package br.com.nord_tool_backend.service.impl;

import br.com.nord_tool_backend.domain.ApartamentoVistoria;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaDto;
import br.com.nord_tool_backend.form.ApartamentoVistoriaForm;
import br.com.nord_tool_backend.handler.XlsxExtractorHandler;
import br.com.nord_tool_backend.repository.ApartamentoVistoriaRepository;
import br.com.nord_tool_backend.service.ApartamentoVistoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartamentoVistoriaServiceImpl extends XlsxExtractorHandler implements ApartamentoVistoriaService {

    @Autowired
    private ApartamentoVistoriaRepository apartamentoVistoriaRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApartamentoVistoriaDto salvarApartamentoVistoria(ApartamentoVistoriaForm apartamentoVistoriaForm){
        ApartamentoVistoria apartamentoVistoria = apartamentoVistoriaForm.converterToDomain();
        ApartamentoVistoriaDto apartamentoVistoriaDto = apartamentoVistoriaRepository.salvarApartamentoVistoria(apartamentoVistoria);
        return apartamentoVistoriaDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApartamentoVistoriaDto alterarApartamentoVistoria(ApartamentoVistoriaForm apartamentoVistoriaForm) {
        ApartamentoVistoria apartamentoVistoria = apartamentoVistoriaForm.converterToDomain();
        ApartamentoVistoriaDto apartamentoVistoriaDto = apartamentoVistoriaRepository.alterarApartamentoVistoria(apartamentoVistoria);
        return apartamentoVistoriaDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletarApartamentoVistoria(Long id) {
        this.apartamentoVistoriaRepository.deletarApartamentoVistoria(id);
    }

    @Override
    public ApartamentoVistoriaDto buscarApartamentoVistoria(Long id) {
        ApartamentoVistoria apartamentoVistoria = apartamentoVistoriaRepository.buscarApartamentoVistoria(id);
        return ApartamentoVistoriaDto.converterToDomain(apartamentoVistoria);
    }

    @Override
    public List<ApartamentoVistoriaDto> listarApartamentoVistoria() {
        List<ApartamentoVistoria> lsApartamentoVistoria = apartamentoVistoriaRepository.listarApartamentoVistoria();
        return lsApartamentoVistoria.stream().map(ApartamentoVistoriaDto::converterToDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importarPlanilha(MultipartFile arquivo) throws Exception {
        init(arquivo);
    }

}
