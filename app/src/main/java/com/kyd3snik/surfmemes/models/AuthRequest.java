package com.kyd3snik.surfmemes.models;

import com.google.gson.annotations.SerializedName;

public class AuthRequest {
    @SerializedName("login")
    public String login;
    @SerializedName("password")
    public String password;

    public AuthRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
