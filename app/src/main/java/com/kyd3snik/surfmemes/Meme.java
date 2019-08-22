package com.kyd3snik.surfmemes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Meme implements Serializable {
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("isFavorite")
    @Expose
    boolean isFavorite;
    @SerializedName("createdDate")
    @Expose
    long createdDate;
    @SerializedName("photoUtl")
    @Expose
    String photoUrl;
}
