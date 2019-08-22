package com.kyd3snik.surfmemes.models;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("username")
    public String username;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("userDescription")
    public String userDescription;

}
