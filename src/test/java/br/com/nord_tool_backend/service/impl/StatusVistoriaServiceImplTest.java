package br.com.nord_tool_backend.service.impl;

import br.com.nord_tool_backend.domain.DiaSemana;
import br.com.nord_tool_backend.domain.StatusVistoria;
import br.com.nord_tool_backend.dto.DiaSemanaDto;
import br.com.nord_tool_backend.dto.StatusVistoriaDto;
import br.com.nord_tool_backend.repository.StatusVistoriaRepository;
import br.com.nord_tool_backend.service.StatusVistoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatusVistoriaServiceImplTest {

    @InjectMocks
    private StatusVistoriaService statusVistoriaService = new StatusVistoriaServiceImpl();

    @Mock
    private StatusVistoriaRepository statusVistoriaRepository;

    List<StatusVistoria> lsStatusVistoria = new ArrayList<>();
    List<StatusVistoriaDto> lsStatusVistoriaDto = new ArrayList<>();

    @BeforeEach
    public void setup() {
        lsStatusVistoria.add(StatusVistoria.builder()
                .idStatusVistoria(1)
                .nmStatusVistoria("Pendente")
                .build());

        lsStatusVistoriaDto.add(StatusVistoriaDto.builder()
                .idStatusVistoria(1)
                .nmStatusVistoria("Pendente")
                .build());
    }

    @Test
    void deveListarStatusVistoria() {
        when(statusVistoriaRepository.listarStatusVistoria()).thenReturn((lsStatusVistoria));
        List<StatusVistoriaDto> statusVistoriaDto = statusVistoriaService.listarStatusVistoria();
        assertEquals(lsStatusVistoriaDto, statusVistoriaDto);
        verify(statusVistoriaRepository).listarStatusVistoria();
    }
}
