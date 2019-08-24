package com.kyd3snik.surfmemes.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kyd3snik.surfmemes.R;

public class AddMemeActivity extends AppCompatActivity {
    private EditText title_et;
    private EditText text_et;
    private Button create_button;
    private ImageButton close_button;
    private ImageButton attach_button;
    private ImageView dettach_button;
    private ImageView attached_image_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meme);
        initViews();
        initListeners();
    }

    void initViews() {
        title_et = findViewById(R.id.title_et);
        text_et = findViewById(R.id.text_et);
        close_button = findViewById(R.id.close_button);
        create_button = findViewById(R.id.create_button);
        attach_button = findViewById(R.id.attach_button);
        dettach_button = findViewById(R.id.dettach_button);
        attached_image_view = findViewById(R.id.attached_image_view);

        setEnabledCreateButton(false);
        attached_image_view.setVisibility(View.GONE);
        dettach_button.setVisibility(View.GONE);
    }

    void setEnabledCreateButton(boolean enable) {
        create_button.setEnabled(enable);
        create_button.setAlpha(enable ? 1f : 0.5f);
    }

    void initListeners() {
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : Add to database
                finish();
            }
        });

        attach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Select photo from gallery
                Glide.with(AddMemeActivity.this).load(R.drawable.avatar).into(attached_image_view);
                attached_image_view.setVisibility(View.VISIBLE);
                dettach_button.setVisibility(View.VISIBLE);
            }
        });

        dettach_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attached_image_view.setVisibility(View.GONE);
                dettach_button.setVisibility(View.GONE);
            }
        });

        title_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setEnabledCreateButton(!title_et.getText().toString().trim().isEmpty());
            }
        });
    }
}
