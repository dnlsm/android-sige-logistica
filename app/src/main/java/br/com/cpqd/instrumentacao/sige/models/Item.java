package br.com.cpqd.instrumentacao.sige.models;

import java.io.Serializable;

public class Item implements Serializable{

    private int code;
    private String name;
    private int protocol;
    private String type;
    private String last_place;

    public Item(){

    }

    public Item(int code, String name, int protocol, String type, String last_place) {
        this.code = code;
        this.name = name;
        this.protocol = protocol;
        this.type = type;
        this.last_place = last_place;
    }


    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code + " - " + this.name + " - " + this.protocol + " - " + this.getLast_place();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLast_place() {
        return last_place;
    }

    public void setLast_place(String last_place) {
        this.last_place = last_place;
    }
}
