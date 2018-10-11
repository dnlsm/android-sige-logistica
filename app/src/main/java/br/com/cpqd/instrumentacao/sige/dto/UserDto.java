package br.com.cpqd.instrumentacao.sige.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.models.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable{

    @JsonProperty("return")
    private User retorno;
    private String msg;
    private String status_code;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getRetorno() {
        return retorno;
    }

    public void setRetorno(User retorno) {
        this.retorno = retorno;
    }
}
