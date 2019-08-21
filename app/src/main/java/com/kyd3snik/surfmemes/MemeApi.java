package com.kyd3snik.surfmemes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MemeApi {
    @POST("/auth/login")
    Call<UserResponse> login(@Body authRequest auth);
    @GET("/memes")
    Call<List<Meme>> getMemes();
}
