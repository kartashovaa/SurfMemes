package com.kyd3snik.surfmemes.presentors;

import android.view.View;

import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;
import com.kyd3snik.surfmemes.repositories.AuthRepository;

public class LoginPresentor implements View.OnClickListener, AuthRepository.OnLoginFinishedListener {

    private LoginView view;

    public LoginPresentor(LoginView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.showProgressBar();
        AuthRepository.login(new AuthRequest(view.getLogin(), view.getPassword()), this);
    }

    @Override
    public void onSuccess(UserResponse userResponse) {
        // TODO: Save responce here
        view.loadMainActivity();
    }

    @Override
    public void onNetworkError() {
        view.showError("Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль");
        view.hideProgressBar();
    }

    @Override
    public void onLoginError(String msg) {
        view.setLoginError(msg);
        view.hideProgressBar();
    }

    @Override
    public void onPasswordError(String msg) {
        view.setPasswordError(msg);
        view.hideProgressBar();
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
