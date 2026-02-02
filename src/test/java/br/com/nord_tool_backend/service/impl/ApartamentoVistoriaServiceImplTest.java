package br.com.nord_tool_backend.service.impl;

import br.com.nord_tool_backend.domain.ApartamentoVistoria;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaDto;
import br.com.nord_tool_backend.form.ApartamentoVistoriaForm;
import br.com.nord_tool_backend.repository.ApartamentoVistoriaRepository;
import br.com.nord_tool_backend.service.ApartamentoVistoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApartamentoVistoriaServiceImplTest {

    @InjectMocks
    private ApartamentoVistoriaService apartamentoVistoriaService = new ApartamentoVistoriaServiceImpl();

    @Mock
    private ApartamentoVistoriaRepository apartamentoVistoriaRepository;

    ApartamentoVistoria apartamentoVistoria = new ApartamentoVistoria();
    ApartamentoVistoriaDto apartamentoVistoriaDto = new ApartamentoVistoriaDto();
    List<ApartamentoVistoria> lsApartamentoVistoria = new ArrayList<>();
    ApartamentoVistoriaForm apartamentoVistoriaForm = new ApartamentoVistoriaForm();


    @BeforeEach
    public void setup(){
        apartamentoVistoria = ApartamentoVistoria.builder()
                .id(1L)
                .nmApartamentoVistoria("nmApartamentoVistoria")
                .idDiaSemana(1)
                .nmDiaSemana("nmDiaSemana")
                .dtApartamentoVigente(LocalDate.now())
                .nmHorarioVistoria("nmHorarioVistoria")
                .idStatusVistoria(1)
                .nmStatusVistoria("nmStatusVistoria")
                .inMarcarRevistoria(true)
                .txObservacaoRevistoria("txObservacaoRevistoria")
                .dtRevistoriaVigente(LocalDate.now())
                .build();

        apartamentoVistoriaDto = ApartamentoVistoriaDto.builder()
                .idApartamentoVistoria(apartamentoVistoria.getId())
                .nmApartamentoVistoria("nmApartamentoVistoria")
                .idDiaSemana(1)
                .nmDiaSemana("nmDiaSemana")
                .dtApartamentoVigente(LocalDate.now())
                .nmHorarioVistoria("nmHorarioVistoria")
                .idStatusVistoria(1)
                .nmStatusVistoria("nmStatusVistoria")
                .inMarcarRevistoria(true)
                .txObservacaoRevistoria("txObservacaoRevistoria")
                .dtRevistoriaVigente(LocalDate.now())
                .build();

        lsApartamentoVistoria.add(ApartamentoVistoria.builder()
                .nmApartamentoVistoria("nmApartamentoVistoria")
                .idDiaSemana(1)
                .nmDiaSemana("nmDiaSemana")
                .dtApartamentoVigente(LocalDate.now())
                .nmHorarioVistoria("nmHorarioVistoria")
                .idStatusVistoria(1)
                .nmStatusVistoria("nmStatusVistoria")
                .inMarcarRevistoria(true)
                .txObservacaoRevistoria("txObservacaoRevistoria")
                .dtRevistoriaVigente(LocalDate.now())
                .build());

        apartamentoVistoriaForm = ApartamentoVistoriaForm.builder()
                .nmApartamentoVistoria("nmApartamentoVistoria")
                .idDiaSemana(1)
                .nmDiaSemana("nmDiaSemana")
                .dtApartamentoVigente(LocalDate.now())
                .nmHorarioVistoria("nmHorarioVistoria")
                .idStatusVistoria(1)
                .nmStatusVistoria("nmStatusVistoria")
                .inMarcarRevistoria(true)
                .txObservacaoRevistoria("txObservacaoRevistoria")
                .dtRevistoriaVigente(LocalDate.now())
                .build();
    }

    @Test
    void deveSalvarApartamentoVistoria() {
        when(apartamentoVistoriaRepository.salvarApartamentoVistoria(any(ApartamentoVistoria.class))).thenReturn(apartamentoVistoriaDto);
        ApartamentoVistoriaDto result = apartamentoVistoriaService.salvarApartamentoVistoria(apartamentoVistoriaForm);
        assertEquals(apartamentoVistoriaDto, result);
        verify(apartamentoVistoriaRepository).salvarApartamentoVistoria(any(ApartamentoVistoria.class));
    }

    @Test
    void deveAlterarApartamentoVistoria() {
        when(apartamentoVistoriaRepository.alterarApartamentoVistoria(any(ApartamentoVistoria.class))).thenReturn(apartamentoVistoriaDto);
        ApartamentoVistoriaDto result = apartamentoVistoriaService.alterarApartamentoVistoria(apartamentoVistoriaForm);
        assertEquals(apartamentoVistoriaDto, result);
        verify(apartamentoVistoriaRepository).alterarApartamentoVistoria(any(ApartamentoVistoria.class));
    }

    @Test
    void deveDeletarApartamentoVistoria(){
        doNothing().when(apartamentoVistoriaRepository).deletarApartamentoVistoria(Mockito.anyLong());
        apartamentoVistoriaService.deletarApartamentoVistoria(Mockito.anyLong());
        verify(apartamentoVistoriaRepository, Mockito.times(1)).deletarApartamentoVistoria(Mockito.anyLong());
    }

    @Test
    void deveRetornarUmApartamentoVistoria(){
        when(apartamentoVistoriaRepository.buscarApartamentoVistoria(Mockito.anyLong())).thenReturn(apartamentoVistoria);
        apartamentoVistoriaService.buscarApartamentoVistoria(Mockito.anyLong());
        verify(apartamentoVistoriaRepository, Mockito.times(1)).buscarApartamentoVistoria(Mockito.anyLong());
    }

    @Test
    void deveRetornarUmaListaApartamentoVistoria(){
        when(apartamentoVistoriaRepository.listarApartamentoVistoria()).thenReturn(lsApartamentoVistoria);
        apartamentoVistoriaService.listarApartamentoVistoria();
        verify(apartamentoVistoriaRepository, Mockito.times(1)).listarApartamentoVistoria();
    }

    @Test
    void deveImportarApartamentoVistoria() throws Exception {
        doNothing().when(apartamentoVistoriaRepository).salvarEmLote(Mockito.anyList());
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("apartamento-modelo.xlsx");
        MockMultipartFile planilhaFile =  new MockMultipartFile("arquivo", "aquivo.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);
        apartamentoVistoriaService.importarPlanilha(planilhaFile);
        verify(apartamentoVistoriaRepository, Mockito.times(1)).salvarEmLote(Mockito.anyList());
    }
}
