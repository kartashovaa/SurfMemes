package com.kyd3snik.surfmemes.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.storages.UserStorage;
import com.kyd3snik.surfmemes.utils.ShareUtil;

public class MemeDetailActivity extends AppCompatActivity {
    private Meme meme;
    private TextView titleView;
    private ImageView imgView;
    private TextView timeView;
    private ImageButton favoriteBtn;
    private TextView detailView;
    private TextView usernameTv;
    private ImageButton closeBtn;
    private ImageButton shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setContentView(R.layout.activity_meme_detail);
        meme = (Meme) getIntent().getSerializableExtra("Meme");
        initViews();
        initListeners();
        usernameTv.setText(UserStorage.getUserName());
        showMeme();
    }

    private void initViews() {
        titleView = findViewById(R.id.title_tv);
        imgView = findViewById(R.id.image_view);
        timeView = findViewById(R.id.time_tv);
        favoriteBtn = findViewById(R.id.favorite_button);
        detailView = findViewById(R.id.detail_view);
        closeBtn = findViewById(R.id.close_button);
        shareBtn = findViewById(R.id.share_button);
        usernameTv = findViewById(R.id.username_tv);
    }

    private void initListeners() {
        favoriteBtn.setOnClickListener(v -> {
            meme.isFavorite = !meme.isFavorite;
            favoriteBtn.setImageResource(meme.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
        });

        closeBtn.setOnClickListener(v -> finish());

        shareBtn.setOnClickListener(v -> ShareUtil.shareMeme(MemeDetailActivity.this, meme));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMeme() {
        titleView.setText(meme.title);
        detailView.setText(meme.description);
        long timeDiff = System.currentTimeMillis() - meme.createdDate * 1000;
        final int millisInDay = 60 * 60 * 24 * 1000;
        if (timeDiff < millisInDay)
            timeView.setText(getString(R.string.today));
        else
            timeView.setText(String.format("%d %s", timeDiff / millisInDay, getString(R.string.date_view_suffix)));
        Glide.with(this).load(meme.photoUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                supportStartPostponedEnterTransition();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                supportStartPostponedEnterTransition();
                return false;
            }
        }).into(imgView);
        favoriteBtn.setImageResource(meme.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
    }
}
