package com.kyd3snik.surfmemes.ui.main;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

import java.util.List;

public class MemesListFragment extends Fragment implements MemesRepository.OnLoadedMemesListener, Observer<List<Meme>> {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MemesAdapter memesAdapter;
    private ImageButton searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initListeners();
        showMemes();

    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.memes_recycle_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        searchButton = view.findViewById(R.id.search_button);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBackground);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showMemes();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSearchActivity();
            }
        });
    }

    private void setMemes(List<Meme> memes) {
        if (memesAdapter == null) {
            memesAdapter = new MemesAdapter(memes);
            recyclerView.setAdapter(memesAdapter);
        } else {
            memesAdapter.addMemes(memes);
        }
    }

    public void showMemes() {
        MemesRepository.getLocalMemes().observe(getActivity(), this);
        MemesRepository.getMemes(this);
    }

    private void loadSearchActivity() {
        getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Override
    public void OnSuccess(List<Meme> memes) {
        setMemes(memes);
        swipeRefreshLayout.setRefreshing(false);
        memesAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnError(String errorMsg) {
        ((MainActivity) getActivity()).showErrorLoadFragment();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        setMemes(memes);
    }
}
