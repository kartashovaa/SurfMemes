package com.kyd3snik.surfmemes.api;

import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MemeApi {
    @GET("/memes")
    Observable<List<Meme>> getMemes();
}
