package br.com.nord_tool_backend.service.impl;

import br.com.nord_tool_backend.controller.response.NordHttpEnum;
import br.com.nord_tool_backend.dto.*;
import br.com.nord_tool_backend.excepetion.ValidacaoException;
import br.com.nord_tool_backend.form.NovaRetiradaChaveForm;
import br.com.nord_tool_backend.repository.ControleChavesRepository;
import br.com.nord_tool_backend.service.ControleChavesService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service @RequiredArgsConstructor
public class ControleChavesServiceImpl implements ControleChavesService {
    private final ControleChavesRepository repository;
    public List<ControleChavesApartamentoDto> buscarApartamentos(String b,int l,int p){validarPagina(l,p);return repository.buscarApartamentos(b,l,p);}
    public List<ControleChavesPessoaDto> listarRetirantes(){return repository.listarRetirantes();}
    public List<ControleChavesPessoaDto> listarLiberadores(){return repository.listarLiberadores();}
    public List<ControleChavesObraDto> listarObras(){return Arrays.asList(
            new ControleChavesObraDto("N1","Nord 1"),
            new ControleChavesObraDto("N2","Nord 2"),
            new ControleChavesObraDto("EN","Energy"));}
    @Transactional
    public RetiradaChaveDto criar(NovaRetiradaChaveForm f){
        repository.buscarApartamento(f.getIdApartamento()).orElseThrow(()->erro(NordHttpEnum.HTTP_404,"Apartamento inexistente"));
        ControleChavesPessoaDto retirante=pessoa(f.getIdRetirante(),"Retirante inexistente");
        ControleChavesPessoaDto liberador=pessoa(f.getIdLiberador(),"Liberador inexistente");
        if(!podeRetirar(retirante))throw erro(NordHttpEnum.HTTP_400,"Colaborador sem permissao para retirar");
        if(!engenharia(liberador))throw erro(NordHttpEnum.HTTP_400,"Colaborador sem permissao para liberar");
        try{return repository.criar(f.getIdApartamento(),f.getIdRetirante(),f.getIdLiberador());}
        catch(DataIntegrityViolationException e){throw erro(NordHttpEnum.HTTP_409,"Apartamento ja possui retirada aberta");}
    }
    @Transactional
    public RetiradaChaveDto receber(Long id,Long recebedor){
        RetiradaChaveDto atual=repository.buscarRetirada(id).orElseThrow(()->erro(NordHttpEnum.HTTP_404,"Retirada inexistente"));
        if("RECEBIDO".equals(atual.getStatus()))throw erro(NordHttpEnum.HTTP_409,"Retirada ja recebida");
        pessoa(recebedor,"Recebedor inexistente");
        if(!repository.receber(id,recebedor))throw erro(NordHttpEnum.HTTP_409,"Retirada ja recebida");
        return repository.buscarRetirada(id).orElseThrow(IllegalStateException::new);
    }
    public List<RetiradaChaveDto> historico(String b,String s,String obra,int l,int p){validarPagina(l,p);String st=normalizarStatus(s);return repository.historico(b,st,normalizarObra(obra),l,p);}
    public ControleChavesDashboardDto dashboard(String obra,int limite){validarPagina(limite,0);String filtro=normalizarObra(obra);return ControleChavesDashboardDto.builder().chavesEmCampo(repository.contarAbertas(filtro)).chavesNoQuadro(repository.contarChavesNoQuadro(filtro)).chavesEntregues(repository.contarChavesEntregues(filtro)).retiradasRecentes(repository.historico(null,null,filtro,limite,0)).build();}
    private ControleChavesPessoaDto pessoa(Long id,String msg){return repository.buscarPessoa(id).orElseThrow(()->erro(NordHttpEnum.HTTP_404,msg));}
    static boolean podeRetirar(ControleChavesPessoaDto p){return engenharia(p)||"campo".equals(permissao(p));}
    static boolean engenharia(ControleChavesPessoaDto p){return "engenharia".equals(permissao(p));}
    private static String permissao(ControleChavesPessoaDto p){return p.getPermissao()==null?"":p.getPermissao().trim().toLowerCase(Locale.ROOT);}
    private String normalizarStatus(String s){if(s==null||s.trim().isEmpty())return null;String st=s.trim().toUpperCase(Locale.ROOT);if(!st.equals("ABERTO")&&!st.equals("RECEBIDO"))throw erro(NordHttpEnum.HTTP_400,"Status deve ser ABERTO ou RECEBIDO");return st;}
    private String normalizarObra(String obra){if(obra==null||obra.trim().isEmpty())return null;String valor=obra.trim();if(valor.equalsIgnoreCase("Todas as obras")||valor.equalsIgnoreCase("Todas")||valor.equalsIgnoreCase("All"))return null;return valor.toUpperCase(Locale.ROOT);}
    private void validarPagina(int l,int p){if(l<1||l>100||p<0)throw erro(NordHttpEnum.HTTP_400,"limite deve estar entre 1 e 100 e pagina nao pode ser negativa");}
    private static ValidacaoException erro(NordHttpEnum h,String m){return new ValidacaoException(h,m,null);}
}
