package br.com.cpqd.instrumentacao.sige.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.models.Item;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto implements Serializable{

    private Item item;
    private String msg;
    private String status_code;
    @JsonProperty("return")
    private List<Item> retorno;


    public List<Item> getRetorno() {
        return retorno;
    }

    public void setRetorno(List<Item> retorno) {
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
