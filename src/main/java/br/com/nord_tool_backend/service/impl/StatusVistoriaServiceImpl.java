package br.com.nord_tool_backend.service.impl;

import br.com.nord_tool_backend.domain.StatusVistoria;
import br.com.nord_tool_backend.dto.StatusVistoriaDto;
import br.com.nord_tool_backend.repository.StatusVistoriaRepository;
import br.com.nord_tool_backend.service.StatusVistoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusVistoriaServiceImpl implements StatusVistoriaService {

    @Autowired
    private StatusVistoriaRepository statusVistoriaRepository;

    public List<StatusVistoriaDto> listarStatusVistoria(){
        List<StatusVistoria> lsStatusVistoria = statusVistoriaRepository.listarStatusVistoria();
        return lsStatusVistoria.stream().map(StatusVistoriaDto::converterToDomain).collect(Collectors.toList());
    }
}
