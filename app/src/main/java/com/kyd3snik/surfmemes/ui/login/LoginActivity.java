package com.kyd3snik.surfmemes.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.presentors.LoginPresentor;
import com.kyd3snik.surfmemes.repositories.AuthRepository;
import com.kyd3snik.surfmemes.ui.main.MainActivity;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class LoginActivity extends AppCompatActivity implements LoginPresentor.LoginView {

    private LoginPresentor presentor;
    private ExtendedEditText loginField;
    private TextFieldBoxes loginBox;
    private ExtendedEditText passwordField;
    private TextFieldBoxes passwordBox;
    private Button loginBtn;
    private AppCompatImageButton passwordBtn;
    private ProgressBar loginProgressBar;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presentor = new LoginPresentor(this);
        initViews();
        initListeners();
    }

    void initViews() {
        loginField = findViewById(R.id.login_te);
        loginBox = findViewById(R.id.login_box);
        passwordField = findViewById(R.id.password_te);
        passwordBox = findViewById(R.id.password_box);
        loginBtn = findViewById(R.id.login_button);
        loginProgressBar = findViewById(R.id.login_pb);
        passwordBtn = passwordBox.getEndIconImageButton();
        root = findViewById(R.id.root);
        passwordBox.setHelperText(String.format(getString(R.string.password_helper_pattern), AuthRepository.PASSWORD_LENGTH));
        passwordField.setFilters(new InputFilter.LengthFilter[]{new InputFilter.LengthFilter(AuthRepository.PASSWORD_LENGTH)});
    }

    void initListeners() {
        loginBtn.setOnClickListener(presentor);
        passwordBtn.setOnClickListener(new View.OnClickListener() {
            boolean is_hidden = true;

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
    }

    @Override
    public void showProgressBar() {
        loginBtn.setText("");
        loginBtn.setEnabled(false);
        loginProgressBar.setTranslationZ(10f);
    }

    @Override
    public void hideProgressBar() {
        loginBtn.setText(getString(R.string.login_button_text));
        loginBtn.setEnabled(true);
        loginProgressBar.setTranslationZ(0f);
    }

    @Override
    public String getLogin() {
        return loginField.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordField.getText().toString();
    }

    @Override
    public void showError(String msg) {
        Snackbar snack = Snackbar.make(root, msg, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(getColor(R.color.colorError));
        snack.show();
    }

    @Override
    public void setLoginError(String msg) {
        loginBox.setError(msg, true);
    }

    @Override
    public void setPasswordError(String msg) {
        passwordBox.setError(msg, true);
    }

    @Override
    public void loadMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

