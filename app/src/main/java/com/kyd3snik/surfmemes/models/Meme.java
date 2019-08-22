package com.kyd3snik.surfmemes.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meme implements Serializable {
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("isFavorite")
    public boolean isFavorite;
    @SerializedName("createdDate")
    public long createdDate;
    @SerializedName("photoUtl")
    public String photoUrl;
}
