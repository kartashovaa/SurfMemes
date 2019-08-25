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
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

public class AddMemeActivity extends AppCompatActivity {
    private EditText titleEt;
    private EditText textEt;
    private Button createButton;
    private ImageButton closeButton;
    private ImageButton attachButton;
    private ImageView dettachButton;
    private ImageView attachedImageView;
    private String attachedImagePath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meme);
        initViews();
        initListeners();
    }

    void initViews() {
        titleEt = findViewById(R.id.title_et);
        textEt = findViewById(R.id.text_et);
        closeButton = findViewById(R.id.close_button);
        createButton = findViewById(R.id.create_button);
        attachButton = findViewById(R.id.attach_button);
        dettachButton = findViewById(R.id.dettach_button);
        attachedImageView = findViewById(R.id.attached_image_view);

        setEnabledCreateButton(false);
        attachedImageView.setVisibility(View.GONE);
        dettachButton.setVisibility(View.GONE);
    }

    void setEnabledCreateButton(boolean enable) {
        createButton.setEnabled(enable);
        createButton.setAlpha(enable ? 1f : 0.5f);
    }

    private Meme getMeme() {
        Meme meme = new Meme();
        long createdDate = System.currentTimeMillis() / 1000;
        meme.id = String.valueOf(createdDate);
        meme.title = titleEt.getText().toString();
        meme.description = textEt.getText().toString();
        meme.createdDate = createdDate;
        if (attachedImageView.getVisibility() == View.VISIBLE)
            meme.photoUrl = attachedImagePath;
        return meme;
    }

    void initListeners() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemesRepository.putMeme(getMeme());
                finish();
            }
        });

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Select photo from gallery
                //attachedMemePath = getPhotoFromGallary();
                Glide.with(AddMemeActivity.this).load(attachedImagePath).into(attachedImageView);
                attachedImageView.setVisibility(View.VISIBLE);
                dettachButton.setVisibility(View.VISIBLE);
            }
        });

        dettachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachedImagePath = "";
                attachedImageView.setVisibility(View.GONE);
                dettachButton.setVisibility(View.GONE);
            }
        });

        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setEnabledCreateButton(!titleEt.getText().toString().trim().isEmpty());
            }
        });
    }
}
