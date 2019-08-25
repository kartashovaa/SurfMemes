package com.kyd3snik.surfmemes.presenters;

import android.view.View;

import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;
import com.kyd3snik.surfmemes.repositories.AuthRepository;
import com.kyd3snik.surfmemes.utils.PrefUtil;
import com.kyd3snik.surfmemes.utils.PrefUtil.Keys;

public class LoginPresenter implements View.OnClickListener, AuthRepository.OnLoginFinishedListener {

    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        view.showProgressBar();
        AuthRepository.login(new AuthRequest(view.getLogin(), view.getPassword()), this);
    }

    @Override
    public void onSuccess(UserResponse userResponse) {
        PrefUtil util = PrefUtil.getInstance();
        util.putString(Keys.ACCESS_TOKEN, userResponse.accessToken);
        util.putString(Keys.USERNAME, userResponse.userInfo.username);
        util.putString(Keys.FIRSTNAME, userResponse.userInfo.firstName);
        util.putString(Keys.LASTNAME, userResponse.userInfo.lastName);
        util.putString(Keys.USER_DESCRIPTION, userResponse.userInfo.userDescription);
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
