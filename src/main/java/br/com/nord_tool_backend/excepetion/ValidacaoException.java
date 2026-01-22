package br.com.nord_tool_backend.excepetion;

import br.com.nord_tool_backend.controller.response.NordHttpEnum;

public class ValidacaoException extends RuntimeException {

    private final NordHttpEnum httpEnum;
    private final String menssage;
    private final String ex;

    public ValidacaoException(NordHttpEnum httpEnum, String menssage, String ex) {
        super(menssage);
        this.httpEnum = httpEnum;
        this.menssage = menssage;
        this.ex = ex;
    }

    public NordHttpEnum getHttpEnum() {
        return httpEnum;
    }

    public String getMenssage() {
        return menssage;
    }

    public String getEx() {
        return ex;
    }
}