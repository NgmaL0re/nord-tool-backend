package br.com.nord_tool_backend.repository;

import br.com.nord_tool_backend.domain.GlobalDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public abstract class RepositoryJdbcOperationsSql<T extends GlobalDomain> {

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected T salvar(String sql, T entity, String idColumnName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        this.namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[]{idColumnName});
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    protected T alterar(String sql, T entity) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        this.namedParameterJdbcTemplate.update(sql, parameterSource);
        return entity;
    }

    protected Integer deletar(String sql, MapSqlParameterSource params) {
        return namedParameterJdbcTemplate.update(sql, params);
    }

    protected void salvarTodos(String query, List<T> ls) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(ls.toArray());
        this.namedParameterJdbcTemplate.batchUpdate(query, batch);
    }

    protected <T> List<T> buscarTodos(String sql, BeanPropertyRowMapper<T> mapper) {
        return namedParameterJdbcTemplate.query(sql, mapper);
    }

    protected <T> T buscarPorId(String sql, MapSqlParameterSource params, BeanPropertyRowMapper<T> mapper) {
        return namedParameterJdbcTemplate.queryForObject(sql, params, mapper);
    }

    protected<T> List<T> buscarTodosPorFiltro(String sql, MapSqlParameterSource params, BeanPropertyRowMapper<T> mapper) {
        return namedParameterJdbcTemplate.query(sql, params, mapper);
    }
}
