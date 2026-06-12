package br.com.nord_tool_backend.domain;

import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartamentoVistoriaHistoricoCustom extends GlobalDomain implements Serializable{
    private static final long serialVersionUID = 325243634534646222L;

    private Long id;
    private String nmAtributo;
    private String txAnterior;
    private String txAtual;

    public ApartamentoVistoriaHistoricoCustom(String nmAtributo, String txAnterior, String txAtual) {
        this.nmAtributo = nmAtributo;
        this.txAnterior = txAnterior;
        this.txAtual = txAtual;
    }

    public String getNmAtributo() {
        return nmAtributo;
    }

    public String getTxAnterior() {
        return txAnterior;
    }

    public String getTxAtual() {
        return txAtual;
    }

}
