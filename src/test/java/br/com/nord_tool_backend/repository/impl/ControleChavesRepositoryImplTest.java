package br.com.nord_tool_backend.repository.impl;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControleChavesRepositoryImplTest {

    @Test
    void converteIdRecebedorIntegerDoPostgresParaLong() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getObject("recebedor_id")).thenReturn(Integer.valueOf(7));

        assertEquals(Long.valueOf(7), ControleChavesRepositoryImpl.nullableLong(resultSet, "recebedor_id"));
    }

    @Test
    void preservaIdRecebedorNuloEmRetiradaAberta() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getObject("recebedor_id")).thenReturn(null);

        assertNull(ControleChavesRepositoryImpl.nullableLong(resultSet, "recebedor_id"));
    }
}
