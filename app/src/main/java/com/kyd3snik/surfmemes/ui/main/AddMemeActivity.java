package com.kyd3snik.surfmemes.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kyd3snik.surfmemes.R;

public class AddMemeActivity extends AppCompatActivity {
    private EditText title_et;
    private EditText text_et;
    private Button close_button;
    private Button create_button;
    private Button attach_button;
    private Button dettach_button;
    private ImageView attached_image_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meme);
    }
}
