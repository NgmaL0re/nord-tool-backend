package br.com.nord_tool_backend.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoGeralApartamentoVistoria implements Serializable {
    private static final long serialVersionUID = -3517856672105506441L;

    private String nmStatusVistoria;
    private Integer qtApartamentoStatusVistoria;
    private Double pcApartamentoStatusVistoria;
    private LocalDate dtRevistoriaVigente;
    private Integer nrTotalRegistros;
}
