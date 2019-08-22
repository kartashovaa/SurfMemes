package com.kyd3snik.surfmemes.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemesListFragment extends Fragment implements Callback<List<Meme>> {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private MemesAdapter memesAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.memes_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBackground);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMemes();
            }
        });
        showMemes();
    }

    void setMemes(List<Meme> memes) {
        if (memesAdapter == null) {
            memesAdapter = new MemesAdapter(memes);
            recyclerView.setAdapter(memesAdapter);
        } else {
            memesAdapter.setMemes(memes);
            memesAdapter.notifyDataSetChanged();
        }

    }

    void showMemes() {
        NetworkService.getInstance().getMemeApi().getMemes().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
        if (response.isSuccessful())
            setMemes(response.body());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<List<Meme>> call, Throwable t) {

        swipeRefreshLayout.setRefreshing(false);
    }
}
