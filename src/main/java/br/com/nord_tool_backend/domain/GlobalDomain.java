package br.com.nord_tool_backend.domain;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public abstract class GlobalDomain implements Serializable{

    private static final long serialVersionUID = -1241232446441658L;

    public abstract Long getId();

    public abstract void setId(Long id);

    public GlobalDomain() {
    }

}
