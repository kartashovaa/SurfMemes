package com.kyd3snik.surfmemes.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("accessToken")
    public String accessToken;
    @SerializedName("userInfo")
    public UserInfo userInfo;
}
