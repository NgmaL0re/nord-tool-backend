package br.com.nord_tool_backend.repository;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class ControleChavesSqlContractTest {
    @Test void ddlGaranteCodigoSequencialEUnicaRetiradaAberta() throws Exception {
        String sql = new String(Files.readAllBytes(Paths.get("src/main/resources/db/controle-chaves.sql")), StandardCharsets.UTF_8).toLowerCase();
        assertTrue(sql.contains("alter table requisicoes_de_chaves"));
        assertTrue(sql.contains("references apartamento_vistoria(id_apartamento_vistoria)"));
        assertTrue(sql.contains("unique index"));
        assertTrue(sql.contains("where st_requisicao = 'aberto'"));
        assertTrue(sql.contains("add column if not exists id_user_retirada"));
        assertTrue(sql.contains("add column if not exists id_user_recebimento"));
        assertTrue(sql.contains("add column if not exists st_requisicao"));
        assertTrue(sql.contains("setval("));
        assertTrue(sql.contains("pg_get_serial_sequence('requisicoes_de_chaves', 'id_requisicao')"));
        assertFalse(sql.contains("create sequence"));
    }

    @Test void queriesDoModuloUsamSearchPathSemSchemasFixos() throws Exception {
        String java = new String(Files.readAllBytes(Paths.get(
                "src/main/java/br/com/nord_tool_backend/repository/impl/ControleChavesRepositoryImpl.java")),
                StandardCharsets.UTF_8);
        assertTrue(java.contains("FROM requisicoes_de_chaves"));
        assertTrue(java.contains("FROM users"));
        assertTrue(java.contains("JOIN permissoes"));
        assertTrue(java.contains("FROM apartamento_vistoria"));
        assertFalse(java.contains("\\\"Test\\\"."));
        assertFalse(java.contains("apartamento."));
        assertFalse(java.contains("public."));
        assertTrue(java.contains("r.id_requisicao"));
        assertTrue(java.contains("r.id_user_retirada"));
        assertTrue(java.contains("r.id_user_liberacao"));
        assertTrue(java.contains("r.st_requisicao"));
        assertTrue(java.contains("s.nm_status_vistoria"));
        assertTrue(java.contains("'APROVADO','APROVADO DAT'"));
        assertFalse(java.contains("nm_colaborador_retirada"));
        assertTrue(java.contains("returning id_requisicao".toUpperCase()) || java.toLowerCase().contains("returning id_requisicao"));
        assertTrue(java.contains("pg_get_serial_sequence('requisicoes_de_chaves','id_requisicao')"));
        assertFalse(java.contains("nextval('requisicoes_de_chaves_seq')"));
        assertFalse(java.toLowerCase().contains("max("));
    }

    @Test void buscaVaziaNaoEnviaParametroNuloAmbiguoAoPostgresql() throws Exception {
        String java = new String(Files.readAllBytes(Paths.get(
                "src/main/java/br/com/nord_tool_backend/repository/impl/ControleChavesRepositoryImpl.java")),
                StandardCharsets.UTF_8);
        String inicio = "public List<ControleChavesApartamentoDto> buscarApartamentos";
        String fim = "public Optional<ControleChavesApartamentoDto> buscarApartamento";
        String metodoBuscaApartamentos = java.substring(java.indexOf(inicio), java.indexOf(fim));

        assertFalse(metodoBuscaApartamentos.contains(":busca IS NULL"));
        assertTrue(metodoBuscaApartamentos.contains(
                "possuiBusca ? \"WHERE LOWER(nm_apartamento_vistoria) LIKE :busca \" : \"\""));
        assertTrue(metodoBuscaApartamentos.contains("FROM apartamento_vistoria"));
    }

    @Test void historicoNaoEnviaFiltrosNulosAmbiguosAoPostgresql() throws Exception {
        String java = new String(Files.readAllBytes(Paths.get(
                "src/main/java/br/com/nord_tool_backend/repository/impl/ControleChavesRepositoryImpl.java")),
                StandardCharsets.UTF_8);
        String inicio = "public List<RetiradaChaveDto> historico";
        String fim = "public long contarAbertas";
        String metodoHistorico = java.substring(java.indexOf(inicio), java.indexOf(fim));

        assertFalse(metodoHistorico.contains(":busca IS NULL"));
        assertFalse(metodoHistorico.contains(":status IS NULL"));
        assertTrue(metodoHistorico.contains("if (possuiBusca)"));
        assertTrue(metodoHistorico.contains("if (possuiStatus)"));
        assertTrue(metodoHistorico.contains("r.id_requisicao DESC"));
    }
}
