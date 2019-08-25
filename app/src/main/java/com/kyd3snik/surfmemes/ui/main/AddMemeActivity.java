package com.kyd3snik.surfmemes.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    private static final int READ_REQUEST_CODE = 0;
    private static final int PHOTO_LOAD_REQUEST = 1;
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

    private void initViews() {
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

    private void initListeners() {
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
                getPhotoFromGallery();
            }
        });

        dettachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachedImageView.setVisibility(View.GONE);
                attachButton.setVisibility(View.VISIBLE);
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

    private void setEnabledCreateButton(boolean enable) {
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

    private void getPhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_REQUEST_CODE);
        } else {
            loadPhoto();
        }

    }

    private void loadPhoto() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, PHOTO_LOAD_REQUEST);
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        //startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void setAttachedImage(Uri uri) {
        attachedImagePath = getPath(uri);
        Glide.with(this).load(attachedImagePath).into(attachedImageView);
        attachedImageView.setVisibility(View.VISIBLE);
        attachButton.setVisibility(View.GONE);
        dettachButton.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_LOAD_REQUEST && resultCode == RESULT_OK && data != null) {
            setAttachedImage(data.getData());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_REQUEST_CODE)
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadPhoto();
            }
    }
}
