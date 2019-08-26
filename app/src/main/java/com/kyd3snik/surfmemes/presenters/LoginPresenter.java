package com.kyd3snik.surfmemes.presenters;

import android.support.annotation.NonNull;

import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;
import com.kyd3snik.surfmemes.repositories.AuthRepository;
import com.kyd3snik.surfmemes.storages.UserStorage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    public final static int PASSWORD_LENGTH = 6;
    private final static String NETWORK_ERROR_MSG = "Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль";
    private final static String LOGIN = "78005553535";
    private final static String PASSWORD = "123456";
    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    private static boolean isValidAuth(AuthRequest auth) {
        return auth.login.equals(LOGIN) && auth.password.equals(PASSWORD);
    }

    private boolean isValidFields(AuthRequest auth) {
        if (auth == null)
            return false;
        if (auth.password.isEmpty() || auth.login.isEmpty()) {
            if (auth.password.isEmpty())
                showError("Поле не может быть пустым!", ErrorType.PASSWORD);
            if (auth.login.isEmpty())
                showError("Поле не может быть пустым!", ErrorType.LOGIN);
            return false;
        } else if (auth.password.length() != PASSWORD_LENGTH) {
            showError(String.format("Пароль должен содержать %d символов", PASSWORD_LENGTH), ErrorType.PASSWORD);
            return false;
        }
        return true;
    }

    private void showError(String msg, ErrorType type) {
        switch (type) {
            case LOGIN:
                view.setLoginError(msg);
                break;
            case PASSWORD:
                view.setPasswordError(msg);
                break;
            case NETWORK:
            default:
                view.showError(msg);
                break;
        }
        view.hideProgressBar();
    }

    public void loginUser() {
        AuthRequest auth = new AuthRequest(view.getLogin(), view.getPassword());
        view.showProgressBar();
        if (isValidFields(auth))
            if (isValidAuth(auth))
                AuthRepository.login(auth).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            UserStorage.saveUser(response.body());
                            view.loadMainActivity();
                        } else
                            showError(NETWORK_ERROR_MSG, ErrorType.NETWORK);
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                        showError(NETWORK_ERROR_MSG, ErrorType.NETWORK);
                    }
                });
    }

    private enum ErrorType {
        LOGIN,
        PASSWORD,
        NETWORK
    }

    public interface LoginView {
        void showProgressBar();

        void hideProgressBar();

        String getLogin();

        String getPassword();

        void showError(String msg);

        void setLoginError(String msg);

        void setPasswordError(String msg);

        void loadMainActivity();
    }
}
