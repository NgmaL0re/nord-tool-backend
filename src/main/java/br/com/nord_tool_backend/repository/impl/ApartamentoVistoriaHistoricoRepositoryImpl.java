package br.com.nord_tool_backend.repository.impl;

import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.domain.ApartamentoVistoriaHistorico;
import br.com.nord_tool_backend.dto.ApartamentoVistoriaHistoricoConsultaDto;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import br.com.nord_tool_backend.repository.ApartamentoVistoriaHistoricoRepository;
import br.com.nord_tool_backend.repository.RepositoryJdbcOperationsSql;
import br.com.nord_tool_backend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@PropertySource("classpath:query/apartamento-vistoria-historico.properties")
public class ApartamentoVistoriaHistoricoRepositoryImpl extends RepositoryJdbcOperationsSql<ApartamentoVistoriaHistorico> implements ApartamentoVistoriaHistoricoRepository {

    private static final String ERRO_GENERICO_SALVAR = "Erro ao salvar historico do apartamento";
    private static final String ERRO_GENERICO_LISTAR = "Erro ao listar historico do apartamento";

    @Value("${SPI.APARTAMENTO_VISTORIA_HISTORICO}")
    private String querySalvarApartamentoVistoriaHistorico;

    @Value("${SPS.APARTAMENTO_VISTORIA_HISTORICO}")
    private String queryBuscarApartamentoVistoriaHistorico;

    @Value("${SPS.APARTAMENTO_VISTORIA_HISTORICO_NR_VERSAO}")
    private String queryBuscarNrVersao;

    @Override
    public void salvarTodosHistoricos(List<ApartamentoVistoriaHistorico> lsApVistoriaHistorico) {
        try {
            log.info("Salvando lista de Historico de apartamentos");
            salvarTodos(querySalvarApartamentoVistoriaHistorico, lsApVistoriaHistorico);
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(NordHttpEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_SALVAR), ex.getMessage());
        }
    }

    @Override
    public List<ApartamentoVistoriaHistoricoConsultaDto> buscarHistorico(Long idApartamentoVistoria) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            log.info("Listando historico de alterações do apartamento");
            mapSqlParameterSource.addValue("idApartamentoVistoria",idApartamentoVistoria);
            return buscarTodosPorFiltro(queryBuscarApartamentoVistoriaHistorico, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(ApartamentoVistoriaHistoricoConsultaDto.class));
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(NordHttpEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_LISTAR), ex.getMessage());
        }
    }

    @Override
    public List<Integer> buscarNrVersaoHistorico(Long idApartamentoVistoria) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            log.info("Listando as versoes do historico alterado do apartamento");
            mapSqlParameterSource.addValue("idApartamentoVistoria",idApartamentoVistoria);
            return buscarIntegers(queryBuscarNrVersao, mapSqlParameterSource);
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(NordHttpEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_LISTAR), ex.getMessage());
        }
    }
}
