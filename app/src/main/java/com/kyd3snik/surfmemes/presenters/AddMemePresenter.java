package com.kyd3snik.surfmemes.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

public class AddMemePresenter {
    public static final int PHOTO_LOAD_REQUEST = 1;
    public static final int READ_REQUEST_CODE = 0;

    private Activity activity;

    public AddMemePresenter(Activity activity) {
        this.activity = activity;
    }

    public void saveMeme(Meme meme) {
        MemesRepository.putMeme(meme);
        activity.finish();
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void getPhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_REQUEST_CODE);
        } else {
            uploadPhoto();
        }

    }

    private void uploadPhoto() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activity.startActivityForResult(photoPicker, PHOTO_LOAD_REQUEST);
    }
}
