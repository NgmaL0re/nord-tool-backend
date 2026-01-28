package br.com.nord_tool_backend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusVistoria extends GlobalDomain implements Serializable {
    private Long id;
    private String nmStatusVistoria;
}
