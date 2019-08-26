package com.kyd3snik.surfmemes.api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterseptor implements Interceptor {

    private String token;

    public TokenInterseptor(String token) {
        this.token = token;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequst = request.newBuilder()
                .addHeader("Authorisation", token)
                .build();
        return chain.proceed(newRequst);
    }
}
