package br.com.nord_tool_backend.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormatDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final DateTimeFormatter DATA_HORA_SEGUNDO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser parser,
                                     DeserializationContext context) throws IOException {

        String value = parser.getText();

        if (value == null || value.isBlank()) {
            return null;
        }

        value = value.trim();

        try {
            return LocalDateTime.parse(value, DATA_HORA_SEGUNDO);
        } catch (DateTimeParseException ignored) {
        }

        try {
            return LocalDateTime.parse(value, DATA_HORA);
        } catch (DateTimeParseException ignored) {
        }

        try {
            return LocalDate.parse(value, DATA).atStartOfDay();
        } catch (DateTimeParseException ignored) {
        }

        throw new IllegalArgumentException(
                "Formato inválido. Utilize: dd/MM/yyyy, dd/MM/yyyy HH:mm ou dd/MM/yyyy HH:mm:ss.");
    }
}
