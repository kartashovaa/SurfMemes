package com.kyd3snik.surfmemes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.ViewHolder> {
    private Context context;
    private List<Meme> memes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;
        public ImageButton likeButton;
        public ImageButton shareButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titleView = itemView.findViewById(R.id.title);
            likeButton = itemView.findViewById(R.id.like_button);
            shareButton = itemView.findViewById(R.id.share_button);

        }
    }

    public MemesAdapter(List<Meme> data) {
        memes = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context == null)context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_meme,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Meme meme = memes.get(i);
        viewHolder.titleView.setText(meme.title);

        Glide.with(context).load(meme.photoUrl).error(R.drawable.ic_add).centerCrop().into(viewHolder.imageView);

        if(meme.isFavorite)
            viewHolder.likeButton.setImageResource(R.drawable.ic_favorite);

    }

    @Override
    public int getItemCount() {
        return memes==null ? 0 : memes.size();
    }
}
