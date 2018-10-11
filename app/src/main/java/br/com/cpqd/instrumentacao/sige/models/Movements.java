package br.com.cpqd.instrumentacao.sige.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movements implements Serializable{

    private transient Date delivery;
    private transient Date receipt;
    private String type;
    private String deliverer_user;
    private String receiver_user;
    private int transportation_code;
    private int item_code;
    private String from;
    private String to;
    private boolean isActive;

    public Movements(){

    }

    public Movements(Date delivery, Date receipt, String type, String deliverer_user, String receiver_user,
                     int transportation_code, int item_code, String from, String to, boolean isActive) {
        this.delivery = delivery;
        this.receipt = receipt;
        this.type = type;
        this.deliverer_user = deliverer_user;
        this.receiver_user = receiver_user;
        this.transportation_code = transportation_code;
        this.item_code = item_code;
        this.from = from;
        this.to = to;
        this.isActive = isActive;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public int getTransportation_code() {
        return transportation_code;
    }

    public void setTransportation_code(int transportation_code) {
        this.transportation_code = transportation_code;
    }

    public String getReceiver_user() {
        return receiver_user;
    }

    public void setReceiver_user(String receiver_user) {
        this.receiver_user = receiver_user;
    }

    public String getDeliverer_user() {
        return deliverer_user;
    }

    public void setDeliverer_user(String deliverer_user) {
        this.deliverer_user = deliverer_user;
    }

    public Date getReceipt() {
        return receipt;
    }

    public void setReceipt(Date receipt) {
        this.receipt = receipt;
    }

    public Date getDelivery() {
        return delivery;
    }

    public void setDelivery(Date delivery) {
        this.delivery = delivery;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
