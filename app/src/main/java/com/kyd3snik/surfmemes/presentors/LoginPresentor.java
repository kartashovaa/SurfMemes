package com.kyd3snik.surfmemes.presentors;

import android.content.Context;
import android.view.View;

import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.models.UserResponse;
import com.kyd3snik.surfmemes.repositories.AuthRepository;
import com.kyd3snik.surfmemes.utils.PrefUtil;
import com.kyd3snik.surfmemes.utils.PrefUtil.Keys;

public class LoginPresentor implements View.OnClickListener, AuthRepository.OnLoginFinishedListener {

    private LoginView view;
    private Context context;

    public LoginPresentor(Context context, LoginView view) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        view.showProgressBar();
        AuthRepository.login(new AuthRequest(view.getLogin(), view.getPassword()), this);
    }

    @Override
    public void onSuccess(UserResponse userResponse) {
        PrefUtil.with(context).putString(Keys.ACCESS_TOKEN, userResponse.accessToken);
        PrefUtil.with(context).putString(Keys.USERNAME, userResponse.userInfo.username);
        PrefUtil.with(context).putString(Keys.FIRSTNAME, userResponse.userInfo.firstName);
        PrefUtil.with(context).putString(Keys.LASTNAME, userResponse.userInfo.lastName);
        PrefUtil.with(context).putString(Keys.USER_DESCRIPTION, userResponse.userInfo.userDescription);
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
