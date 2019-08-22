package com.kyd3snik.surfmemes;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemesListFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    public static MemesListFragment newInstance() {
        return new MemesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_memes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.memes_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMemes();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        showMemes();
    }
    void showMemes() {
        NetworkService.getInstance().getAPI().getMemes().enqueue(new Callback<List<Meme>>() {
            @Override
            public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
                List<Meme> memes = response.body();
                MemesAdapter memesAdapter = (MemesAdapter) recyclerView.getAdapter();
                if(memesAdapter!= null) {
                    memesAdapter.setMemes(memes);
                    memesAdapter.notifyDataSetChanged();
                } else
                    recyclerView.setAdapter(new MemesAdapter(memes));
            }

            @Override
            public void onFailure(Call<List<Meme>> call, Throwable t) {

            }
        });
    }
}
