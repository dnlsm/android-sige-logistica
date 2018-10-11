package br.com.cpqd.instrumentacao.sige.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.models.AuthReturn;
import br.com.cpqd.instrumentacao.sige.models.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDto implements Serializable {

    @JsonProperty("return")
    private AuthReturn retorno;
    private String status_code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public AuthReturn getRetorno() {
        return retorno;
    }

    public void setRetorno(AuthReturn retorno) {
        this.retorno = retorno;
    }

}
