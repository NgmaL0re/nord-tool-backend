package br.com.nord_tool_backend.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static br.com.nord_tool_backend.utils.StringUtils.normalizeDiaSemana;

@Getter
@AllArgsConstructor
public enum DiaSemanaEnum {
    SEGUNDA_FEIRA(1, "Segunda-Feira"),
    TERCA_FEIRA(2, "Terça-Feira"),
    QUARTA_FEIRA(3, "Quarta-Feira"),
    QUINTA_FEIRA(4, "Quinta-Feira"),
    SEXTA_FEIRA(5, "Sexta-Feira"),
    SEM_AGENDAMENTO(6, "Sem agendamento");

    private final Integer id;
    private final String nmDiaSemana;

    public static Integer getDiaSemana(String value) {
        if (value == null || value.isBlank()) {
            return SEM_AGENDAMENTO.getId();
        }
        String normalizedValue = normalizeDiaSemana(value);

        return Arrays.stream(values())
                .filter(d -> normalizeDiaSemana(d.nmDiaSemana).equals(normalizedValue))
                .findFirst()
                .map(DiaSemanaEnum::getId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Dia da semana inválido: " + value)
                );
    }
}