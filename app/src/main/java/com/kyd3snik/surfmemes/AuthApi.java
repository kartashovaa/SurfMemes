package com.kyd3snik.surfmemes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/login")
    Call<UserResponse> login(@Body AuthRequest auth);

}
