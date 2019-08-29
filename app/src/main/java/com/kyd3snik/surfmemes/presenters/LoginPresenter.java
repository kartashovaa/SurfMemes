package com.kyd3snik.surfmemes.presenters;

import android.content.Context;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.models.AuthRequest;
import com.kyd3snik.surfmemes.repositories.AuthRepository;
import com.kyd3snik.surfmemes.repositories.UserStorage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter {
    public final static int PASSWORD_LENGTH = 6;
    private final static String LOGIN = "78005553535";
    private final static String PASSWORD = "123456";
    private LoginView view;
    private Context context;

    public LoginPresenter(LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }


    public void loginUser() {
        AuthRequest auth = new AuthRequest(view.getLogin(), view.getPassword());
        view.showProgressBar();
        if (isValidFields(auth))
            if (isValidAuth(auth))
                AuthRepository.login(auth)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(user -> {
                                    UserStorage.saveUser(user);
                                    view.loadMainActivity();
                                }, e -> showError(getString(R.string.network_error_message),
                                ErrorType.NETWORK)
                        );
            else {
                showError(getString(R.string.network_error_message), ErrorType.NETWORK);
                view.hideProgressBar();
            }
    }

    private boolean isValidFields(AuthRequest auth) {
        if (auth == null)
            return false;
        if (auth.password.isEmpty() || auth.login.isEmpty()) {
            if (auth.password.isEmpty())
                showError(getString(R.string.empty_field), ErrorType.PASSWORD);
            if (auth.login.isEmpty())
                showError(getString(R.string.empty_field), ErrorType.LOGIN);
            return false;
        } else if (auth.password.length() != PASSWORD_LENGTH) {
            showError(String.format(getString(R.string.password_helper_template), PASSWORD_LENGTH), ErrorType.PASSWORD);
            return false;
        }
        return true;
    }

    private boolean isValidAuth(AuthRequest auth) {
        return auth.login.equals(LOGIN) && auth.password.equals(PASSWORD);
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

    private String getString(int id) {
        return context.getString(id);
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
