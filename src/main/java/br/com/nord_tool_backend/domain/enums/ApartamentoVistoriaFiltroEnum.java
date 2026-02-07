package br.com.nord_tool_backend.domain.enums;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum ApartamentoVistoriaFiltroEnum {
    QUERY_TODOS( "SPS.LISTAR.APARTAMENTO_VISTORIA_FILTRA_TODOS"),
    QUERY_WHERE( "SPS.LISTAR.APARTAMENTO_VISTORIA_FILTRA_ATRIBUTO");
    private static final String STANDARD_ORDER = " ORDER BY id_apartamento_vistoria DESC ";
    private String queryProperty;

    public String getQueryProperty() {
        return queryProperty;
    }

    public String getSort(String nmOrdenacao) {
        if (nmOrdenacao == null || nmOrdenacao.trim().isEmpty()) {
            return STANDARD_ORDER;
        }
        String[] partes = nmOrdenacao.split(",");
        String campo = partes[0].trim();
        String ordem = partes.length > 1 ? partes[1].trim().toUpperCase() : "ASC";
        String campoValidado = SortEnum.buscaPorCampo(campo);

        return " ORDER BY " + campoValidado + " " + ordem + " ";
    }

    @AllArgsConstructor
    private enum SortEnum {
        id_apartamento_vistoria("idApartamentoVistoria"),
        nm_apartamento_vistoria("nmApartamentoVistoria"),
        nm_dia_semana("nmDiaSemana"),
        dt_apartamento_vigente("dtApartamentoVigente"),
        nm_horario_vistoria("nmHorarioVistoria"),
        nm_status_vistoria("nmStatusVistoria"),
        tx_observacao_revistoria("txObservacaoRevistoria"),
        dt_revistoria_vigente("dtRevistoriaVigente");
        private String campo;

        public static String buscaPorCampo(String campo) {
            return Stream.of(SortEnum.values()).filter(sort ->
                            sort.campo.equals(campo))
                    .findFirst()
                    .orElse(SortEnum.id_apartamento_vistoria).toString();
        }
    }
}
