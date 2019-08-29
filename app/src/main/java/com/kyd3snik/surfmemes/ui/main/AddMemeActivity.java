package com.kyd3snik.surfmemes.ui.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.kyd3snik.surfmemes.presenters.AddMemePresenter;


public class AddMemeActivity extends AppCompatActivity {
    private EditText titleEt;
    private EditText textEt;
    private Button createButton;
    private ImageButton closeButton;
    private ImageButton attachButton;
    private ImageView dettachButton;
    private ImageView attachedImageView;
    private String attachedImagePath = "";
    private AddMemePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meme);
        presenter = new AddMemePresenter(this);
        initViews();
        initListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddMemePresenter.PHOTO_LOAD_REQUEST
                && resultCode == RESULT_OK
                && data != null)
            setAttachedPhoto(presenter.getPath(data.getData()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AddMemePresenter.READ_REQUEST_CODE
                && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            presenter.getPhotoFromGallery();
    }

    private void initViews() {
        titleEt = findViewById(R.id.title_et);
        textEt = findViewById(R.id.text_et);
        closeButton = findViewById(R.id.close_button);
        createButton = findViewById(R.id.create_button);
        attachButton = findViewById(R.id.attach_button);
        dettachButton = findViewById(R.id.dettach_button);
        attachedImageView = findViewById(R.id.attached_image_view);

        setEnableCreateButton(false);
        attachedImageView.setVisibility(View.GONE);
        dettachButton.setVisibility(View.GONE);
    }

    private void initListeners() {
        closeButton.setOnClickListener(v -> finish());

        createButton.setOnClickListener(v -> {
            createButton.setEnabled(false);
            presenter.saveMeme(getMeme());
        });

        attachButton.setOnClickListener(v -> presenter.getPhotoFromGallery());

        dettachButton.setOnClickListener(v -> {
            attachedImageView.setVisibility(View.GONE);
            attachButton.setVisibility(View.VISIBLE);
            dettachButton.setVisibility(View.GONE);
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
                setEnableCreateButton(!titleEt.getText().toString().trim().isEmpty());
            }
        });
    }

    private Meme getMeme() {
        Meme meme = new Meme();
        long createdDate = System.currentTimeMillis() / 1000;
        meme.title = titleEt.getText().toString();
        meme.description = textEt.getText().toString();
        meme.createdDate = createdDate;
        if (attachedImageView.getVisibility() == View.VISIBLE)
            meme.photoUrl = attachedImagePath;
        return meme;
    }


    private void setEnableCreateButton(boolean enable) {
        createButton.setEnabled(enable);
        createButton.setAlpha(enable ? 1f : 0.5f);
    }

    private void setAttachedPhoto(String path) {
        attachedImagePath = path;
        Glide.with(this).load(attachedImagePath).into(attachedImageView);
        attachedImageView.setVisibility(View.VISIBLE);
        attachButton.setVisibility(View.GONE);
        dettachButton.setVisibility(View.VISIBLE);
    }
}
