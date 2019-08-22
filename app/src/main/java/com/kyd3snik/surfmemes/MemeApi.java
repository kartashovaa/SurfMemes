package com.kyd3snik.surfmemes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MemeApi {
    @GET("/memes")
    Call<List<Meme>> getMemes();
}
