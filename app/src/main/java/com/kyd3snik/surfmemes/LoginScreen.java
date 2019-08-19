package com.kyd3snik.surfmemes;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class LoginScreen extends AppCompatActivity {
    private final static int passwordLength = 6;
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
        passwordBox.setHelperText(String.format("Пароль должен содержать %d символов", passwordLength));


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
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

                if (login.isEmpty())
                    loginBox.setError("Поле не можен быть пустым!", true);
                else if (password.isEmpty())
                    passwordBox.setError("Поле не можен быть пустым!", true);
                else {
                    findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    final String btnText = loginBtn.getText().toString();
                    loginBtn.setText("");
                    loginProgressBar.setTranslationZ(10f);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loginBtn.setText(btnText);
                            loginProgressBar.setTranslationZ(0f);

                            Toast.makeText(LoginScreen.this.getApplicationContext(), "Login... login: " + loginField.getText() + " password: " + passwordField.getText(), Toast.LENGTH_SHORT).show();
                        }
                    }, 5000);
                }
            }
        });
    }
}
