package br.com.cpqd.instrumentacao.sige.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.models.Movements;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementsDto implements Serializable {

    private String msg;
    private int status_code;
    @JsonProperty("return")
    private List<Movements> retorno;

    public MovementsDto(){

    }

    public MovementsDto(String msg, int status_code, List<Movements> retorno) {
        this.msg = msg;
        this.status_code = status_code;
        this.retorno = retorno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Movements> getRetorno() {
        return retorno;
    }

    public void setRetorno(List<Movements> retorno) {
        this.retorno = retorno;
    }
}
