package com.kyd3snik.surfmemes.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.utils.MemeHelper;
import com.kyd3snik.surfmemes.utils.ShareUtil;

import java.util.ArrayList;
import java.util.List;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.ViewHolder> implements Filterable {
    private Activity activity;
    private List<Meme> memes;
    private List<Meme> filteredMemes;
    private MemeHelper.CanShowDetailActivity callback;

    public MemesAdapter(List<Meme> data, Activity activity, MemeHelper.CanShowDetailActivity callback) {
        setMemes(data);
        this.activity = activity;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_meme, viewGroup, false);
        return new ViewHolder(view, activity, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(filteredMemes.get(i));
    }

    @Override
    public int getItemCount() {
        return filteredMemes.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                filteredMemes = new ArrayList<>();
                FilterResults result = new FilterResults();

                if (!query.isEmpty())
                    for (Meme meme : memes)
                        if (meme.title.toLowerCase().contains(query))
                            filteredMemes.add(meme);
                result.values = filteredMemes;
                result.count = filteredMemes.size();
                Log.d("ADAPTER_TAG", query + " " + filteredMemes.size());
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredMemes = (ArrayList<Meme>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private void setMemes(List<Meme> memes) {
        this.memes = memes;
        this.filteredMemes = memes;
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

    private boolean containsMeme(Meme newMeme) {
        for (Meme oldMeme : this.memes)
            if (newMeme.id == oldMeme.id)
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Activity context;
        private Meme meme;
        private ImageView imageView;
        private TextView titleView;
        private ImageButton likeButton;
        private ImageButton shareButton;

        ViewHolder(@NonNull View itemView, final Activity context, MemeHelper.CanShowDetailActivity callback) {
            super(itemView);
            this.context = context;
            initViews();
            initListeners(callback);
        }

        void bind(Meme meme) {
            this.meme = meme;
            titleView.setText(meme.title);
            Glide.with(context).load(meme.photoUrl).into(imageView);
            setFavorite();
        }

        private void initViews() {
            imageView = itemView.findViewById(R.id.image_view);
            titleView = itemView.findViewById(R.id.title_tv);
            likeButton = itemView.findViewById(R.id.like_button);
            shareButton = itemView.findViewById(R.id.share_button);
        }

        private void initListeners(MemeHelper.CanShowDetailActivity callback) {
            itemView.setOnClickListener(v -> callback.showMemeDetailActivity(meme, titleView, imageView));
            likeButton.setOnClickListener(v -> {
                meme.isFavorite = !meme.isFavorite;
                setFavorite();
            });

            shareButton.setOnClickListener(v -> ShareUtil.shareMeme(context, meme));
        }

        private void setFavorite() {
            likeButton.setImageResource(
                    meme.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
        }
    }
}
