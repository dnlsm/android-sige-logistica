package br.com.cpqd.instrumentacao.sige.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.cpqd.instrumentacao.sige.models.Transportation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransportationDto {

    private String msg;
    private String status_code;
    @JsonProperty("return")
    private List<Transportation> retorno;


    public List<Transportation> getRetorno() {
        return retorno;
    }

    public void setRetorno(List<Transportation> retorno) {
        this.retorno = retorno;
    }

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
}
