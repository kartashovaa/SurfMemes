package com.kyd3snik.surfmemes.ui.main;


import android.arch.lifecycle.LifecycleOwner;
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
import android.widget.TextView;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.presenters.MemesListPresenter;

import java.util.List;

public class MemesListFragment extends Fragment implements MemesListPresenter.MemesListView {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView loadErrorTv;
    private MemesAdapter memesAdapter;
    private ImageButton searchButton;
    private MemesListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MemesListPresenter(this);
    }

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
        presenter.showMemes();

    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.memes_recycle_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        searchButton = view.findViewById(R.id.search_button);
        loadErrorTv = view.findViewById(R.id.load_error_tv);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBackground);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.showMemes();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSearchMemeActivity();
            }
        });
    }

    private void loadSearchMemeActivity() {
        getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Override
    public void showMemes(List<Meme> memes) {
        hideLoadError();
        if (memesAdapter == null) {
            memesAdapter = new MemesAdapter(memes, getActivity());
            recyclerView.setAdapter(memesAdapter);
        } else {
            memesAdapter.addMemes(memes);
        }
    }

    @Override
    public void showLoadError() {
        recyclerView.setVisibility(View.GONE);
        loadErrorTv.setVisibility(View.VISIBLE);
    }

    private void hideLoadError() {
        loadErrorTv.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return getActivity();
    }


    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
