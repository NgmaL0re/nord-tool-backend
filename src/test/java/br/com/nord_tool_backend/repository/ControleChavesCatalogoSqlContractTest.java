package br.com.nord_tool_backend.repository;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControleChavesCatalogoSqlContractTest {

    @Test
    void deveUsarSearchPathSemSchemaFixoNosCatalogos() throws IOException {
        String colaborador = recurso("query/colaborador.properties");
        String empresa = recurso("query/empresa.properties");
        String cargo = recurso("query/cargo.properties");
        String permissao = recurso("query/permissao.properties");

        assertTrue(colaborador.contains("INSERT INTO users"));
        assertTrue(colaborador.contains("UPDATE users"));
        assertTrue(colaborador.contains("DELETE FROM users"));
        assertTrue(colaborador.contains("FROM users u"));
        assertTrue(colaborador.contains("JOIN empresas e"));
        assertTrue(colaborador.contains("JOIN cargos c"));
        assertTrue(colaborador.contains("JOIN permissoes p"));
        assertTrue(empresa.contains("FROM empresas e"));
        assertTrue(cargo.contains("FROM cargos c"));
        assertTrue(permissao.contains("FROM permissoes p"));

        String todas = colaborador + empresa + cargo + permissao;
        assertFalse(todas.contains("\"Test\"."));
    }

    private String recurso(String nome) throws IOException {
        try (var stream = getClass().getClassLoader().getResourceAsStream(nome)) {
            if (stream == null) {
                throw new IOException("Recurso nao encontrado: " + nome);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
