package com.kyd3snik.surfmemes.utils;

import android.content.Context;
import android.content.Intent;

import com.kyd3snik.surfmemes.models.Meme;

public class ShareUtil {
    public static void shareMeme(Context context, Meme meme) {
        StringBuilder shareText = new StringBuilder();
        shareText.append(meme.title);
        if (meme.photoUrl != null & !meme.photoUrl.isEmpty())
            shareText.append("\n").append(meme.photoUrl);
        if (meme.description != null && !meme.description.isEmpty())
            shareText.append("\n").append(meme.description);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
        shareIntent.setType("text/plain");
        context.startActivity(shareIntent);
    }
}
