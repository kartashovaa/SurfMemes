package com.kyd3snik.surfmemes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MemeDetailActivity extends AppCompatActivity {
    TextView titleView;
    ImageView imgView;
    TextView timeView;
    ImageButton favoriteBtn;
    TextView detailView;
    ImageButton closeBtn;
    ImageButton shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_detail);
        titleView = findViewById(R.id.title);
        imgView = findViewById(R.id.image);
        timeView = findViewById(R.id.time);
        favoriteBtn = findViewById(R.id.favorite);
        detailView = findViewById(R.id.detail);
        closeBtn = findViewById(R.id.close);
        shareBtn = findViewById(R.id.share);

        final Meme meme = (Meme)getIntent().getSerializableExtra("Meme");
        titleView.setText(meme.title);
        detailView.setText(meme.description);
        timeView.setText(String.valueOf(meme.createdDate));
        favoriteBtn.setImageResource(meme.isFavorite? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meme.isFavorite = !meme.isFavorite;
                favoriteBtn.setImageResource(meme.isFavorite? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
