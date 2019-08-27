package com.kyd3snik.surfmemes.adapters;

import android.app.Activity;
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
    private Activity activity;
    private List<Meme> memes;

    public MemesAdapter(List<Meme> data, Activity activity) {
        setMemes(data);
        this.activity = activity;
    }

    private boolean containsMeme(Meme newMeme) {
        for (Meme oldMeme : this.memes)
            if (newMeme.id.equals(oldMeme.id))
                return true;
        return false;
    }

    private int concatMemesList(List<Meme> memes) {
        int concatSize = 0;
        for (Meme meme : memes)
            if (!containsMeme(meme)) {
                this.memes.add(meme);
                ++concatSize;
            }
        return concatSize;
    }

    public void setMemes(List<Meme> memes) {
        this.memes = memes;
    }

    public void addMemes(List<Meme> memes) {
        if (this.memes == null) {
            setMemes(memes);
        } else {
            int start = this.memes.size();
            int size = concatMemesList(memes);
            notifyItemRangeChanged(start, size);
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
        private Activity context;
        private Meme meme;
        private ImageView imageView;
        private TextView titleView;
        private ImageButton likeButton;
        private ImageButton shareButton;

        ViewHolder(@NonNull View itemView, final Activity context) {
            super(itemView);
            this.context = context;
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title_tv);
            likeButton = itemView.findViewById(R.id.like_button);
            shareButton = itemView.findViewById(R.id.share_button);
            itemView.setOnClickListener(v -> showMemeDetailActivity(meme, titleView, imageView));
            likeButton.setOnClickListener(v -> {
                meme.isFavorite = !meme.isFavorite;
                setFavorite();
            });

            shareButton.setOnClickListener(v -> ShareUtil.shareMeme(context, meme));
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

        private void showMemeDetailActivity(Meme meme, View titleView, View imageView) {
            Intent intent = new Intent(context, MemeDetailActivity.class);
            intent.putExtra("Meme", meme);
            Pair<View, String> title = Pair.create(titleView, "title");
            Pair<View, String> image = Pair.create(imageView, "image");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, title, image);
            context.startActivity(intent, options.toBundle());
        }

    }

}
