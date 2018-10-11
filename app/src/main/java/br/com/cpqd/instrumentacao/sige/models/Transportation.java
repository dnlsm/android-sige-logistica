package br.com.cpqd.instrumentacao.sige.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transportation implements Serializable {

    private int code;
    private String type;
    private Date date;
    private Date created_date;
    private String notes;
    private boolean is_active;
    private int protocol;
    private String requester_user;
    private String requested_user;
    private String from;
    private String to;
    private List<Item> items;

    public Transportation() {

    }

    public Transportation(int code, Date date, Date created_date, String notes, boolean is_active,
                          int protocol, String requester_user, String requested_user,
                          String from, String to, List<Item> items) {
        this.code = code;
        this.date = date;
        this.created_date = created_date;
        this.notes = notes;
        this.is_active = is_active;
        this.protocol = protocol;
        this.requester_user = requester_user;
        this.requested_user = requested_user;
        this.from = from;
        this.to = to;
        this.items = items;
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

    public String getRequested_user() {
        return requested_user;
    }

    public void setRequested_user(String requested_user) {
        this.requested_user = requested_user;
    }

    public String getRequester_user() {
        return requester_user;
    }

    public void setRequester_user(String requester_user) {
        this.requester_user = requester_user;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
