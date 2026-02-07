package br.com.nord_tool_backend.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaSemana extends GlobalDomain implements Serializable {

    private static final long serialVersionUID = -3238490796551071555L;

    private Long id;
    private String nmDiaSemana;
}
