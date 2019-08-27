package com.kyd3snik.surfmemes.repositories;

import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;

import io.reactivex.Observable;


public class AuthRepository {
    public static Observable<UserResponse> login(AuthRequest auth) {
        return NetworkService.getInstance().getAuthApi().login(auth);
    }
}
