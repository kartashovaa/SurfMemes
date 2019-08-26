package com.kyd3snik.surfmemes.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.ui.main.MemeDetailActivity;
import com.kyd3snik.surfmemes.utils.ShareUtil;

import java.util.List;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.ViewHolder> {
    private static Activity activity;
    private List<Meme> memes;

    public MemesAdapter(List<Meme> data, Activity activity) {
        setMemes(data);
        MemesAdapter.activity = activity;
    }

    private static void showMemeDetailActivity(Meme meme, View titleView, View imageView) {
        Intent intent = new Intent(activity, MemeDetailActivity.class);
        intent.putExtra("Meme", meme);
        Pair<View, String> title = Pair.create(titleView, "title");
        Pair<View, String> image = Pair.create(imageView, "image");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, title, image);
        activity.startActivity(intent, options.toBundle());
    }

    private boolean findMemeById(Meme newMeme) {
        for (Meme oldMeme : this.memes)
            if (newMeme.id.equals(oldMeme.id))
                return true;
        return false;
    }

    private void concatMemesList(List<Meme> memes) {
        for (Meme meme : memes)
            if (!this.memes.contains(meme) && !findMemeById(meme))
                this.memes.add(meme);
    }

    public void setMemes(List<Meme> memes) {
        this.memes = memes;
    }

    public void addMemes(List<Meme> memes) {
        if (this.memes == null) {
            setMemes(memes);
        } else {
            concatMemesList(memes);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_meme, viewGroup, false);
        return new ViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(memes.get(i));
    }

    @Override
    public int getItemCount() {
        return memes == null ? 0 : memes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private Meme meme;
        private ImageView imageView;
        private TextView titleView;
        private ImageButton likeButton;
        private ImageButton shareButton;

        ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            this.context = context;
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title_tv);
            likeButton = itemView.findViewById(R.id.like_button);
            shareButton = itemView.findViewById(R.id.share_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMemeDetailActivity(meme, titleView, imageView);
                }
            });
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meme.isFavorite = !meme.isFavorite;
                    setFavorite();
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtil.shareMeme(context, meme);
                }
            });
        }

        void bind(Meme meme) {
            this.meme = meme;
            titleView.setText(meme.title);
            Glide.with(context).load(meme.photoUrl).into(imageView);
            setFavorite();
        }

        private void setFavorite() {
            likeButton.setImageResource(
                    meme.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
        }
    }

}
