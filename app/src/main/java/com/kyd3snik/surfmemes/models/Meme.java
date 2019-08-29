package com.kyd3snik.surfmemes.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Meme implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    public long id;
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
