package com.kyd3snik.surfmemes.repositories;

import android.support.annotation.NonNull;

import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthRepository {
    public final static int PASSWORD_LENGTH = 6;
    private final static String LOGIN = "78005553535";
    private final static String PASSWORD = "123456";

    private static boolean isValidFields(AuthRequest auth, OnLoginFinishedListener listener) {
        if (auth == null)
            return false;
        if (auth.password.isEmpty() || auth.login.isEmpty()) {
            if (auth.password.isEmpty())
                listener.onPasswordError("Поле не может быть пустым!");
            if (auth.login.isEmpty())
                listener.onLoginError("Поле не может быть пустым!");
            return false;
        } else if (auth.password.length() != PASSWORD_LENGTH) {
            listener.onPasswordError(String.format("Пароль должен содержать %d символов", PASSWORD_LENGTH));
            return false;
        }
        return true;
    }

    private static boolean isValidAuth(AuthRequest auth) {
        return auth.login.equals(LOGIN) && auth.password.equals(PASSWORD);
    }

    public static void login(AuthRequest auth, final OnLoginFinishedListener listener) {
        if (isValidFields(auth, listener))
            if (isValidAuth(auth))
                NetworkService.getInstance().getAuthApi().login(auth).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                        if (response.isSuccessful())
                            listener.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                        listener.onNetworkError();
                    }
                });
            else
                listener.onNetworkError();
    }

    public interface OnLoginFinishedListener {
        void onSuccess(UserResponse userResponse);

        void onNetworkError();

        void onLoginError(String msg);

        void onPasswordError(String msg);
    }
}
