package com.kyd3snik.surfmemes;

import android.content.Context;
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

public class LoginScreen extends AppCompatActivity {
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
            boolean is_hidden = true;

            @Override
            public void onClick(View v) {
                if (is_hidden) {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordBtn.setImageResource(R.drawable.hide_password);
                } else {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordBtn.setImageResource(R.drawable.show_password);

                }
                is_hidden = !is_hidden;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String login = loginField.getText().toString();
                    String password = passwordField.getText().toString();

                if(login.isEmpty() || password.isEmpty()) {
                    if (password.isEmpty())
                        passwordBox.setError(getString(R.string.auth_error_msg), true);
                    if (login.isEmpty())
                        loginBox.setError(getString(R.string.auth_error_msg), true);

                } else if (password.length()!=passwordLength) {
                    passwordBox.setError(String.format(
                            getString(R.string.password_helper_pattern), passwordLength),
                            true);
                } else {

                    final String btnText = loginBtn.getText().toString();
                    loginBtn.setText("");
                    loginBtn.setEnabled(false);
                    loginProgressBar.setTranslationZ(10f);
                    authRequest auth = new authRequest();
                    auth.setLogin(login);
                    auth.setPassword(password);
                    NetworkService.getInstance().getAPI().login(auth).enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                            SharedPreferences sp = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor e = sp.edit();
                            UserResponse ur = response.body();
                            if(ur != null) {
                                e.putString("accessToken", ur.getAccessToken());
                                UserInfo ui = ur.getUserInfo();
                                if(ui != null) {
                                    e.putString("username", ui.getUsername());
                                    e.putString("firstName", ui.getFirstName());
                                    e.putString("lastName", ui.getLastName());
                                    e.putString("userDescription", ui.getUserDescription());
                                }
                                e.apply();
                            }else
                                Snackbar.make(findViewById(R.id.root),
                                        "Во время запроса произошла ошибка, возможно вы неверно ввели логин/пароль",
                                        Snackbar.LENGTH_LONG).show();

                            Snackbar.make(findViewById(R.id.root),String.format("Code %d",response.raw().code()),Snackbar.LENGTH_SHORT).show();
                            loginBtn.setText(btnText);
                            loginBtn.setEnabled(true);
                            loginProgressBar.setTranslationZ(0f);
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                            Snackbar.make(findViewById(R.id.root), "Login falture!", Toast.LENGTH_SHORT).show();
                            loginBtn.setText(btnText);
                            loginBtn.setEnabled(true);
                            loginProgressBar.setTranslationZ(0f);
                        }
                    });
                }
            }
        });
    }

}

