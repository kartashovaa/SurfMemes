package com.kyd3snik.surfmemes;

import com.google.gson.annotations.SerializedName;

public class AuthRequest {
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
