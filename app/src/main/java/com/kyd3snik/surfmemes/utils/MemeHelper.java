package com.kyd3snik.surfmemes.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.ui.main.MemeDetailActivity;

public class MemeHelper {
    public static void showMemeDetailActivity(Activity context, Meme meme, View titleView, View imageView) {
        Intent intent = new Intent(context, MemeDetailActivity.class);
        intent.putExtra("Meme", meme);
        Pair<View, String> title = Pair.create(titleView, "title");
        Pair<View, String> image = Pair.create(imageView, "image");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, title, image);
        context.startActivity(intent, options.toBundle());
    }

    public interface CanShowDetailActivity {
        void showMemeDetailActivity(Meme meme, View titleView, View imageView);
    }
}
