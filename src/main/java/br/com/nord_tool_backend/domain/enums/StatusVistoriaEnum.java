package br.com.nord_tool_backend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static br.com.nord_tool_backend.utils.StringUtils.normalizeStatusVistoria;

@Getter
@AllArgsConstructor
public enum StatusVistoriaEnum {

    NAO_LIBERADO(1, "Não Liberado"),
    AGENDADO(2, "Agendado"),
    LIBERADO(3, "Liberado"),
    APROVADO(4, "Aprovado"),
    REPROVADO(5, "Reprovado"),
    PENDENTE(6, "Pendente");

    private final Integer id;
    private final String nmStatusVistoria;

    public static Integer getStatusVistoria(String value) {
        if (value == null || value.isBlank()) return null;

        String normalizedValue = normalizeStatusVistoria(value);

        return Arrays.stream(values())
                .filter(s -> normalizeStatusVistoria(s.nmStatusVistoria).equals(normalizedValue)
                        || normalizeStatusVistoria(s.name()).equals(normalizedValue))
                .findFirst()
                .map(StatusVistoriaEnum::getId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Status inválido: " + value)
                );
    }

}