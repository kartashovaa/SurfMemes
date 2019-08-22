package com.kyd3snik.surfmemes.api;

import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MemeApi {
    @GET("/memes")
    Call<List<Meme>> getMemes();
}
