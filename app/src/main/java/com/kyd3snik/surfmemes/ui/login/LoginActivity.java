package com.kyd3snik.surfmemes.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.presenters.LoginPresenter;
import com.kyd3snik.surfmemes.ui.main.MainActivity;
import com.kyd3snik.surfmemes.utils.CustomPhoneNumberFormattingTextWatcher;
import com.kyd3snik.surfmemes.utils.SnackbarHelper;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView {

    private LoginPresenter presenter;
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
        presenter = new LoginPresenter(this, this);
        initViews();
        initListeners();
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
        return loginField.getText().toString().replaceAll("[^\\d]", "");
    }

    @Override
    public String getPassword() {
        return passwordField.getText().toString();
    }

    @Override
    public void showError(String msg) {
        SnackbarHelper.showMessage(root, msg, getColor(R.color.colorError));
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

    private void initViews() {
        loginField = findViewById(R.id.login_te);
        loginBox = findViewById(R.id.login_box);
        passwordField = findViewById(R.id.password_te);
        passwordBox = findViewById(R.id.password_box);
        passwordBox.setHelperText(String.format(getString(R.string.password_helper_template), LoginPresenter.PASSWORD_LENGTH));
        loginBtn = findViewById(R.id.login_button);
        loginProgressBar = findViewById(R.id.login_pb);
        passwordBtn = passwordBox.getEndIconImageButton();
        root = findViewById(R.id.root);
    }

    private void initListeners() {
        loginBtn.setOnClickListener(v -> presenter.loginUser());

        loginField.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(loginField));
        passwordBtn.setOnClickListener(new View.OnClickListener() {
            boolean isHidden = true;

            @Override
            public void onClick(View v) {
                if (isHidden) {
                    passwordField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    passwordBtn.setImageResource(R.drawable.show_password);
                } else {
                    passwordField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    passwordBtn.setImageResource(R.drawable.hide_password);
                }
                isHidden = !isHidden;
            }
        });
    }
}

