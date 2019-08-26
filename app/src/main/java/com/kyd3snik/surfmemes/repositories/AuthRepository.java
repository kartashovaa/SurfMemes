package com.kyd3snik.surfmemes.repositories;

import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;

import retrofit2.Call;


public class AuthRepository {
    public static Call<UserResponse> login(AuthRequest auth) {
        return NetworkService.getInstance().getAuthApi().login(auth);
    }
}
