package com.kyd3snik.surfmemes.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.models.Meme;

public class MemeDetailActivity extends AppCompatActivity {
    private TextView titleView;
    private ImageView imgView;
    private TextView timeView;
    private ImageButton favoriteBtn;
    private TextView detailView;
    private ImageButton closeBtn;
    private ImageButton shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_detail);
        titleView = findViewById(R.id.title);
        imgView = findViewById(R.id.image);
        timeView = findViewById(R.id.time);
        favoriteBtn = findViewById(R.id.favorite_button);
        detailView = findViewById(R.id.detail_view);
        closeBtn = findViewById(R.id.close_button);
        shareBtn = findViewById(R.id.share_button);

        final Meme meme = (Meme)getIntent().getSerializableExtra("Meme");
        titleView.setText(meme.title);
        detailView.setText(meme.description);
        long timeDiff = System.currentTimeMillis() - meme.createdDate*1000;
        final int millisInDay = 60*60*24*1000;
        if(timeDiff<millisInDay)
            timeView.setText("Cегодня");
        else
            timeView.setText(String.valueOf(timeDiff/millisInDay)+" дней назад");

        Glide.with(getApplicationContext()).load(meme.photoUrl).error(R.drawable.ic_add).into(imgView);
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
