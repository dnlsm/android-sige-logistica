package br.com.cpqd.instrumentacao.sige.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{

    private String login;
    private String full_name;
    private String user_password;
    private String place;
    private AuthReturn token;
    private String place_location;
    private UserCategory category;
    private String type;



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public UserCategory getCategory() {
        return category;
    }

    public void setCategory(UserCategory category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace_location() {
        return place_location;
    }

    public void setPlace_location(String place_location) {
        this.place_location = place_location;
    }

    public AuthReturn getToken() {
        return token;
    }

    public void setToken(AuthReturn token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return login + " - " + full_name + " - " + place + " - " + place_location + " - " + category + " - " + type;
    }
}
