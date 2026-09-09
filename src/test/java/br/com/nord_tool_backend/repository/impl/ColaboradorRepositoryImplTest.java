package br.com.nord_tool_backend.repository.impl;

import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.domain.Colaborador;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ColaboradorRepositoryImplTest {
    private NamedParameterJdbcTemplate jdbcTemplate;
    private ColaboradorRepositoryImpl repository;

    @BeforeEach
    void setup() {
        jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        repository = new ColaboradorRepositoryImpl();
        ReflectionTestUtils.setField(repository, "namedParameterJdbcTemplate", jdbcTemplate);
        ReflectionTestUtils.setField(repository, "queryAlterar", "UPDATE users");
        ReflectionTestUtils.setField(repository, "queryDeletar", "DELETE users");
        ReflectionTestUtils.setField(repository, "queryBuscarPorId", "SELECT users");
    }

    @Test
    void deveRetornar404AoBuscarIdInexistente() {
        when(jdbcTemplate.queryForObject(anyString(), any(SqlParameterSource.class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        ValidacaoException ex = assertThrows(ValidacaoException.class,
                () -> repository.buscarPorIdColaborador(999L));

        assertEquals(NordHttpEnum.HTTP_404, ex.getHttpEnum());
    }

    @Test
    void deveRetornar404AoAlterarIdInexistente() {
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class))).thenReturn(0);

        ValidacaoException ex = assertThrows(ValidacaoException.class,
                () -> repository.alterarColaborador(colaborador()));

        assertEquals(NordHttpEnum.HTTP_404, ex.getHttpEnum());
    }

    @Test
    void deveRetornar404AoDeletarIdInexistente() {
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class))).thenReturn(0);

        ValidacaoException ex = assertThrows(ValidacaoException.class,
                () -> repository.deletarColaborador(999L));

        assertEquals(NordHttpEnum.HTTP_404, ex.getHttpEnum());
    }

    @Test
    void deveRetornar400ParaReferenciasInvalidasNaAlteracao() {
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class)))
                .thenThrow(new DataIntegrityViolationException("fk"));

        ValidacaoException ex = assertThrows(ValidacaoException.class,
                () -> repository.alterarColaborador(colaborador()));

        assertEquals(NordHttpEnum.HTTP_400, ex.getHttpEnum());
        assertEquals("Empresa, cargo ou permissão inválidos", ex.getMenssage());
    }

    private Colaborador colaborador() {
        return Colaborador.builder()
                .id(999L)
                .nome("João")
                .celular("11999999999")
                .idEmpresa(1L)
                .idCargo(2L)
                .idPermissao(3L)
                .build();
    }
}
