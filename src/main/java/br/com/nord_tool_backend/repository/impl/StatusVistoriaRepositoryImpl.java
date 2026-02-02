package br.com.nord_tool_backend.repository.impl;

import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.domain.StatusVistoria;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import br.com.nord_tool_backend.repository.RepositoryJdbcOperationsSql;
import br.com.nord_tool_backend.repository.StatusVistoriaRepository;
import br.com.nord_tool_backend.service.impl.StatusVistoriaServiceImpl;
import br.com.nord_tool_backend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@PropertySource("classpath:query/status-vistoria.properties")
public class StatusVistoriaRepositoryImpl extends RepositoryJdbcOperationsSql<StatusVistoria> implements StatusVistoriaRepository {

    private static final String LISTAR_ERRO_GENERICO = "Erro ao listar os dados da tabela Status Vistoria";

    @Value("${SPS.LISTAR_TODOS_STATUS_VISTORIA}")
    private String queryListarStatusVistoria;

    @Override
    public List<StatusVistoria> listarStatusVistoria (){
        try {
            log.info("Listando todos os Status Vistoria da base de dados");
            return buscarTodos(queryListarStatusVistoria, BeanPropertyRowMapper.newInstance(StatusVistoria.class));
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(NordHttpEnum.HTTP_400, StringUtils.getMensagem(LISTAR_ERRO_GENERICO), ex.getMessage());
        }
    }
}
