package br.com.nord_tool_backend.service.impl;

import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.dto.*;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import br.com.nord_tool_backend.form.NovaRetiradaChaveForm;
import br.com.nord_tool_backend.repository.ControleChavesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControleChavesServiceImplTest {
    private ControleChavesRepository repository;
    private ControleChavesServiceImpl service;
    private ControleChavesPessoaDto engenharia;
    @BeforeEach void setup(){repository=mock(ControleChavesRepository.class);service=new ControleChavesServiceImpl(repository);engenharia=new ControleChavesPessoaDto(1L,"Ana","Engenharia");}
    @Test void engenhariaPodeRetirarELiberarInclusiveNaMesmaOperacao(){
        NovaRetiradaChaveForm f=form(10L,1L,1L); RetiradaChaveDto criado=RetiradaChaveDto.builder().id(10001L).codigo("RET-10001").status("ABERTO").build();
        when(repository.buscarApartamento(10L)).thenReturn(Optional.of(new ControleChavesApartamentoDto(10L,"EN-01-0204")));
        when(repository.buscarPessoa(1L)).thenReturn(Optional.of(engenharia)); when(repository.criar(10L,1L,1L)).thenReturn(criado);
        assertEquals("RET-10001",service.criar(f).getCodigo()); verify(repository).criar(10L,1L,1L);
        assertTrue(ControleChavesServiceImpl.podeRetirar(engenharia)); assertTrue(ControleChavesServiceImpl.engenharia(engenharia));
    }
    @Test void listasSaoDelegadasSemExcluirEngenhariaDosRetirantes(){
        when(repository.listarRetirantes()).thenReturn(Collections.singletonList(engenharia)); when(repository.listarLiberadores()).thenReturn(Collections.singletonList(engenharia));
        assertEquals(engenharia,service.listarRetirantes().get(0)); assertEquals(engenharia,service.listarLiberadores().get(0));
    }
    @Test void campoRetiraMasNaoLibera(){ControleChavesPessoaDto campo=new ControleChavesPessoaDto(2L,"Bia","Campo");assertTrue(ControleChavesServiceImpl.podeRetirar(campo));assertFalse(ControleChavesServiceImpl.engenharia(campo));}
    @Test void converteDuplicidadeConcorrenteEmConflito(){
        NovaRetiradaChaveForm f=form(10L,1L,1L);when(repository.buscarApartamento(10L)).thenReturn(Optional.of(new ControleChavesApartamentoDto()));when(repository.buscarPessoa(1L)).thenReturn(Optional.of(engenharia));when(repository.criar(10L,1L,1L)).thenThrow(new DuplicateKeyException("indice parcial"));
        ValidacaoException e=assertThrows(ValidacaoException.class,()->service.criar(f));assertEquals(NordHttpEnum.HTTP_409,e.getHttpEnum());
    }
    @Test void recebimentoDuplicadoRetornaConflito(){when(repository.buscarRetirada(10001L)).thenReturn(Optional.of(RetiradaChaveDto.builder().status("RECEBIDO").build()));ValidacaoException e=assertThrows(ValidacaoException.class,()->service.receber(10001L,1L));assertEquals(NordHttpEnum.HTTP_409,e.getHttpEnum());verify(repository,never()).receber(anyLong(),any());}
    @Test void recebimentoExigeRecebedorExistente(){when(repository.buscarRetirada(10001L)).thenReturn(Optional.of(RetiradaChaveDto.builder().status("ABERTO").build()));when(repository.buscarPessoa(9L)).thenReturn(Optional.empty());ValidacaoException e=assertThrows(ValidacaoException.class,()->service.receber(10001L,9L));assertEquals(NordHttpEnum.HTTP_404,e.getHttpEnum());verify(repository,never()).receber(anyLong(),any());}
    @Test void historicoValidaStatusEPaginacao(){assertThrows(ValidacaoException.class,()->service.historico(null,"INVALIDO",null,20,0));assertThrows(ValidacaoException.class,()->service.historico(null,null,null,101,0));}
    @Test void dashboardContaEntreguesPeloStatusDoApartamentoEFiltraObra(){
        when(repository.contarAbertas("N1")).thenReturn(2L);
        when(repository.contarChavesNoQuadro("N1")).thenReturn(30L);
        when(repository.contarChavesEntregues("N1")).thenReturn(473L);
        ControleChavesDashboardDto dashboard=service.dashboard("n1",5);
        assertEquals(2L,dashboard.getChavesEmCampo());
        assertEquals(30L,dashboard.getChavesNoQuadro());
        assertEquals(473L,dashboard.getChavesEntregues());
        verify(repository).historico(null,null,"N1",5,0);
    }
    @Test void todasAsObrasNaoAplicaFiltro(){
        service.historico(null,null,"Todas as obras",20,0);
        verify(repository).historico(null,null,null,20,0);
    }
    @Test void listaObrasComNomeECodigoDeFiltro(){
        List<ControleChavesObraDto> obras=service.listarObras();
        assertEquals(Arrays.asList("N1","N2","EN"),obras.stream().map(ControleChavesObraDto::getId).collect(java.util.stream.Collectors.toList()));
        assertEquals(Arrays.asList("Nord 1","Nord 2","Energy"),obras.stream().map(ControleChavesObraDto::getNome).collect(java.util.stream.Collectors.toList()));
    }
    @Test void buscaApartamentosDelegaTermoEPaginacao(){
        List<ControleChavesApartamentoDto> apartamentos=Collections.singletonList(new ControleChavesApartamentoDto(10L,"EN-01-0204"));
        when(repository.buscarApartamentos("0204",25,2)).thenReturn(apartamentos);
        assertEquals(apartamentos,service.buscarApartamentos("0204",25,2));
        verify(repository).buscarApartamentos("0204",25,2);
    }
    @Test void buscaApartamentosValidaLimiteEPagina(){
        assertThrows(ValidacaoException.class,()->service.buscarApartamentos(null,0,0));
        assertThrows(ValidacaoException.class,()->service.buscarApartamentos(null,101,0));
        assertThrows(ValidacaoException.class,()->service.buscarApartamentos(null,20,-1));
        verify(repository,never()).buscarApartamentos(any(),anyInt(),anyInt());
    }
    private NovaRetiradaChaveForm form(long a,long r,long l){NovaRetiradaChaveForm f=new NovaRetiradaChaveForm();f.setIdApartamento(a);f.setIdRetirante(r);f.setIdLiberador(l);return f;}
}
