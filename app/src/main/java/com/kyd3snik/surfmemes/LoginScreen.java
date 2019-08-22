package com.kyd3snik.surfmemes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class LoginScreen extends AppCompatActivity implements Callback<UserResponse>, View.OnClickListener {
    private final static String LOGIN = "88005553535";
    private final static String PASSWORD = "123456";
    private final static int passwordLength = 6;
    private final static String SHARED_PREFS="mainPrefs";
    ExtendedEditText loginField;
    TextFieldBoxes loginBox;
    ExtendedEditText passwordField;
    TextFieldBoxes passwordBox;
    Button loginBtn;
    AppCompatImageButton passwordBtn;
    ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loginField = findViewById(R.id.login);
        loginBox = findViewById(R.id.login_box);
        passwordField = findViewById(R.id.password);
        passwordBox = findViewById(R.id.password_box);
        loginBtn = findViewById(R.id.login_button);
        loginProgressBar = findViewById(R.id.progress_bar);
        passwordBtn = passwordBox.getEndIconImageButton();
        passwordBox.setHelperText(String.format(getString(R.string.password_helper_pattern), passwordLength));
        passwordField.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(passwordLength)});


        passwordBtn.setOnClickListener(new View.OnClickListener() {
            boolean is_hidden=true;
            @Override
            public void onClick(View v) {
                if (is_hidden) {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordBtn.setImageResource(R.drawable.show_password);
                } else {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordBtn.setImageResource(R.drawable.hide_password);

                }
                is_hidden = !is_hidden;
            }
        });

        loginBtn.setOnClickListener(this);
    }
    void showProgressBar() {
        loginBtn.setText("");
        loginBtn.setEnabled(false);
        loginProgressBar.setTranslationZ(10f);
    }

    void hideProgressBar() {
        loginBtn.setText(getString(R.string.login_button_text));
        loginBtn.setEnabled(true);
        loginProgressBar.setTranslationZ(0f);
    }


    Snackbar getErrorSnack(String text, int duration) {
        Snackbar snack = Snackbar.make(findViewById(R.id.root), text, duration);
        snack.getView().setBackgroundColor(getColor(R.color.colorError));
        return snack;
    }

    boolean checkFields(AuthRequest auth) {
        if(auth==null)
            return false;
        if (auth.getPassword().isEmpty() || auth.getLogin().isEmpty()) {
            if(auth.getPassword().isEmpty())
                passwordBox.setError(getString(R.string.auth_error_msg), true);
            if (auth.getLogin().isEmpty())
                loginBox.setError(getString(R.string.auth_error_msg), true);
            return false;
        } else if (auth.getPassword().length() != passwordLength) {
            passwordBox.setError(String.format(
                    getString(R.string.password_helper_pattern), passwordLength), true);
            return false;
        }
        return true;
    }
    boolean checkAuth(AuthRequest auth) {
        return auth.getLogin().equals(LOGIN) && auth.getPassword().equals(PASSWORD);
    }

    @Override
    public void onClick(View v) {
        AuthRequest auth = new AuthRequest();
        auth.setLogin(loginField.getText().toString());
        auth.setPassword(passwordField.getText().toString());

        if(checkFields(auth))
            if(checkAuth(auth)){
                showProgressBar();
                NetworkService.getInstance().getAuthApi().login(auth).enqueue(LoginScreen.this);
            } else {
                getErrorSnack("Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль",
                        Snackbar.LENGTH_LONG).show();
            }
    }


    @Override
    public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        UserResponse ur = response.body();
        if(response.isSuccessful() && ur != null) {
            e.putString("accessToken", ur.getAccessToken());
            UserInfo ui = ur.getUserInfo();
            if(ui != null) {
                e.putString("username", ui.getUsername());
                e.putString("firstName", ui.getFirstName());
                e.putString("lastName", ui.getLastName());
                e.putString("userDescription", ui.getUserDescription());
            }
            e.apply();

            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
        getErrorSnack( "Login failure!", Toast.LENGTH_SHORT).show();
        hideProgressBar();
    }

}

