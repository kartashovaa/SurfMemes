package com.kyd3snik.surfmemes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
    private Context context;
    private List<Meme> memes;

    public MemesAdapter(List<Meme> data) {
        setMemes(data);
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
        if (this.memes == null) {
            this.memes = memes;
        } else {
            concatMemesList(memes);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null)
            context = viewGroup.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_meme, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.update(memes.get(i));
    }

    @Override
    public int getItemCount() {
        return memes == null ? 0 : memes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final public Context context;
        public Meme meme;
        public ImageView imageView;
        public TextView titleView;
        public ImageButton likeButton;
        public ImageButton shareButton;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            this.context = context;
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title_tv);
            likeButton = itemView.findViewById(R.id.like_button);
            shareButton = itemView.findViewById(R.id.share_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewHolder.this.context, MemeDetailActivity.class);
                    intent.putExtra("Meme", meme);
                    context.startActivity(intent);
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

        public void update(Meme meme) {
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
