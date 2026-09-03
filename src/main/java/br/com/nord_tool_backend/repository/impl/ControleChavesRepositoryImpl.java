package br.com.nord_tool_backend.repository.impl;

import br.com.nord_tool_backend.dto.*;
import br.com.nord_tool_backend.repository.ControleChavesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository @RequiredArgsConstructor
public class ControleChavesRepositoryImpl implements ControleChavesRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private static final String PESSOA_SELECT = "SELECT u.id_user id, u.nm_user nome, p.nm_permissao permissao FROM users u JOIN permissoes p ON p.id_permissao=u.id_permissao ";
    private static final String RETIRADA_SELECT = "SELECT r.id_requisicao id, r.cd_retirada codigo, r.id_apartamento_vistoria apartamento_id, a.nm_apartamento_vistoria apartamento_label, r.id_user_retirada retirante_id, ur.nm_user retirante_nome, pr.nm_permissao retirante_permissao, r.id_user_liberacao liberador_id, ul.nm_user liberador_nome, pl.nm_permissao liberador_permissao, r.id_user_recebimento recebedor_id, ue.nm_user recebedor_nome, pe.nm_permissao recebedor_permissao, r.dt_retirada, r.dt_recebimento, r.st_requisicao FROM requisicoes_de_chaves r JOIN apartamento_vistoria a ON a.id_apartamento_vistoria=r.id_apartamento_vistoria JOIN users ur ON ur.id_user=r.id_user_retirada JOIN permissoes pr ON pr.id_permissao=ur.id_permissao JOIN users ul ON ul.id_user=r.id_user_liberacao JOIN permissoes pl ON pl.id_permissao=ul.id_permissao LEFT JOIN users ue ON ue.id_user=r.id_user_recebimento LEFT JOIN permissoes pe ON pe.id_permissao=ue.id_permissao ";

    public List<ControleChavesApartamentoDto> buscarApartamentos(String busca, int limite, int pagina) {
        boolean possuiBusca = busca != null && !busca.trim().isEmpty();
        String sql="SELECT id_apartamento_vistoria id, nm_apartamento_vistoria label FROM apartamento_vistoria "
                +(possuiBusca ? "WHERE LOWER(nm_apartamento_vistoria) LIKE :busca " : "")
                +"ORDER BY nm_apartamento_vistoria, id_apartamento_vistoria LIMIT :limite OFFSET :offset";
        return jdbc.query(sql, params(busca, limite, pagina), (rs,n)->new ControleChavesApartamentoDto(rs.getLong("id"),rs.getString("label")));
    }
    public Optional<ControleChavesApartamentoDto> buscarApartamento(Long id) {
        List<ControleChavesApartamentoDto> l=jdbc.query("SELECT id_apartamento_vistoria id,nm_apartamento_vistoria label FROM apartamento_vistoria WHERE id_apartamento_vistoria=:id",new MapSqlParameterSource("id",id),(rs,n)->new ControleChavesApartamentoDto(rs.getLong("id"),rs.getString("label"))); return l.stream().findFirst();
    }
    public Optional<ControleChavesPessoaDto> buscarPessoa(Long id) { return jdbc.query(PESSOA_SELECT+"WHERE u.id_user=:id",new MapSqlParameterSource("id",id),(rs,n)->pessoa(rs,"id","nome","permissao")).stream().findFirst(); }
    public List<ControleChavesPessoaDto> listarRetirantes() { return listarPessoas("LOWER(p.nm_permissao) IN ('engenharia','campo')"); }
    public List<ControleChavesPessoaDto> listarLiberadores() { return listarPessoas("LOWER(p.nm_permissao) = 'engenharia'"); }
    public List<String> listarObras() {
        return jdbc.queryForList("SELECT DISTINCT split_part(nm_apartamento_vistoria, '-', 1) obra FROM apartamento_vistoria WHERE position('-' in nm_apartamento_vistoria) > 1 ORDER BY obra", new MapSqlParameterSource(), String.class);
    }
    private List<ControleChavesPessoaDto> listarPessoas(String regra) { return jdbc.query(PESSOA_SELECT+"WHERE "+regra+" ORDER BY u.nm_user,u.id_user",(rs,n)->pessoa(rs,"id","nome","permissao")); }
    public RetiradaChaveDto criar(Long apartamento, Long retirante, Long liberador) {
        String sql="WITH novo AS (SELECT nextval(pg_get_serial_sequence('requisicoes_de_chaves','id_requisicao')::regclass) AS id) INSERT INTO requisicoes_de_chaves(id_requisicao,cd_retirada,dt_retirada,dt_recebimento,id_apartamento_vistoria,id_user_retirada,id_user_liberacao,id_user_recebimento,st_requisicao) SELECT id,'RET-' || LPAD(id::text,5,'0'),CURRENT_TIMESTAMP,NULL,:a,:r,:l,NULL,'ABERTO' FROM novo RETURNING id_requisicao";
        Long id=jdbc.queryForObject(sql,new MapSqlParameterSource().addValue("a",apartamento).addValue("r",retirante).addValue("l",liberador),Long.class);
        return buscarRetirada(id).orElseThrow(IllegalStateException::new);
    }
    public Optional<RetiradaChaveDto> buscarRetirada(Long id) { return jdbc.query(RETIRADA_SELECT+"WHERE r.id_requisicao=:id",new MapSqlParameterSource("id",id),(rs,n)->retirada(rs)).stream().findFirst(); }
    public boolean receber(Long id, Long recebedor) { return jdbc.update("UPDATE requisicoes_de_chaves SET st_requisicao='RECEBIDO',dt_recebimento=CURRENT_TIMESTAMP,id_user_recebimento=:p WHERE id_requisicao=:id AND st_requisicao='ABERTO'",new MapSqlParameterSource().addValue("id",id).addValue("p",recebedor))==1; }
    public List<RetiradaChaveDto> historico(String busca, String status, String obra, int limite, int pagina) {
        boolean possuiBusca = busca != null && !busca.trim().isEmpty();
        boolean possuiStatus = status != null && !status.trim().isEmpty();
        StringBuilder sql = new StringBuilder(RETIRADA_SELECT).append("WHERE 1=1 ");
        MapSqlParameterSource parametros = params(busca, limite, pagina);

        if (possuiBusca) {
            sql.append("AND (LOWER(r.cd_retirada) LIKE :busca ")
                    .append("OR LOWER(a.nm_apartamento_vistoria) LIKE :busca ")
                    .append("OR LOWER(ur.nm_user) LIKE :busca) ");
        }
        if (possuiStatus) {
            sql.append("AND r.st_requisicao = :status ");
            parametros.addValue("status", status);
        }
        if (obra != null) {
            sql.append("AND UPPER(split_part(a.nm_apartamento_vistoria, '-', 1)) = :obra ");
            parametros.addValue("obra", obra);
        }
        sql.append("ORDER BY r.dt_retirada DESC, r.id_requisicao DESC ")
                .append("LIMIT :limite OFFSET :offset");

        return jdbc.query(sql.toString(), parametros, (rs, n) -> retirada(rs));
    }
    public long contarAbertas(String obra){return count("SELECT COUNT(*) FROM requisicoes_de_chaves r JOIN apartamento_vistoria a ON a.id_apartamento_vistoria=r.id_apartamento_vistoria WHERE r.st_requisicao='ABERTO'"+filtroObra(obra),obra);}
    public long contarChavesEntregues(String obra){return count("SELECT COUNT(*) FROM apartamento_vistoria a JOIN status_vistoria s ON s.id_status_vistoria=a.id_status_vistoria WHERE UPPER(TRIM(s.nm_status_vistoria)) IN ('APROVADO','APROVADO DAT')"+filtroObra(obra),obra);}
    public long contarChavesNoQuadro(String obra){return count("SELECT COUNT(*) FROM apartamento_vistoria a JOIN status_vistoria s ON s.id_status_vistoria=a.id_status_vistoria WHERE UPPER(TRIM(s.nm_status_vistoria)) NOT IN ('APROVADO','APROVADO DAT')"+filtroObra(obra),obra);}
    private String filtroObra(String obra){return obra==null?"":" AND UPPER(split_part(a.nm_apartamento_vistoria, '-', 1)) = :obra";}
    private long count(String sql,String obra){MapSqlParameterSource p=new MapSqlParameterSource();if(obra!=null)p.addValue("obra",obra);return jdbc.queryForObject(sql,p,Long.class);}
    private MapSqlParameterSource params(String busca,int limite,int pagina){String b=busca==null||busca.trim().isEmpty()?null:"%"+busca.trim().toLowerCase()+"%";return new MapSqlParameterSource().addValue("busca",b).addValue("limite",limite).addValue("offset",pagina*limite);}
    private ControleChavesPessoaDto pessoa(ResultSet r,String i,String n,String p)throws SQLException{return ControleChavesPessoaDto.builder().id(r.getLong(i)).nome(r.getString(n)).permissao(r.getString(p)).build();}
    private RetiradaChaveDto retirada(ResultSet r)throws SQLException { Long rec=nullableLong(r,"recebedor_id"); return RetiradaChaveDto.builder().id(r.getLong("id")).codigo(r.getString("codigo")).apartamento(new ControleChavesApartamentoDto(r.getLong("apartamento_id"),r.getString("apartamento_label"))).retirante(pessoa(r,"retirante_id","retirante_nome","retirante_permissao")).liberador(pessoa(r,"liberador_id","liberador_nome","liberador_permissao")).recebedor(rec==null?null:pessoa(r,"recebedor_id","recebedor_nome","recebedor_permissao")).dataRetirada(r.getTimestamp("dt_retirada").toLocalDateTime()).dataRecebimento(r.getTimestamp("dt_recebimento")==null?null:r.getTimestamp("dt_recebimento").toLocalDateTime()).status(r.getString("st_requisicao")).build(); }
    static Long nullableLong(ResultSet r,String column)throws SQLException { Number value=(Number)r.getObject(column); return value==null?null:value.longValue(); }
}
