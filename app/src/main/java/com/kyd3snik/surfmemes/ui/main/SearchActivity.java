package com.kyd3snik.surfmemes.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.presenters.SearchPresenter;
import com.kyd3snik.surfmemes.utils.MemeHelper;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchPresenter.SearchView, MemeHelper.CanShowDetailActivity {

    private EditText searchEt;
    private TextView errorTv;
    private ImageButton closeButton;
    private RecyclerView memesRecycler;
    private MemesAdapter memesAdapter;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        presenter = new SearchPresenter(this);
        initViews();
        initListeners();
        presenter.loadMemes();
    }

    @Override
    public void addMemes(List<Meme> memes) {
        if (memesAdapter == null) {
            memesAdapter = new MemesAdapter(memes, this, this);
            memesRecycler.setAdapter(memesAdapter);
            memesAdapter.getFilter().filter("");
            searchEt.setEnabled(true);
        } else
            memesAdapter.addMemes(memes);
    }

    @Override
    public void showMemeDetailActivity(Meme meme, View titleView, View imageView) {
        MemeHelper.showMemeDetailActivity(this, meme, titleView, imageView);
    }

    private void initViews() {
        searchEt = findViewById(R.id.search_et);
        searchEt.setEnabled(false);
        errorTv = findViewById(R.id.error_tv);
        closeButton = findViewById(R.id.close_button);
        memesRecycler = findViewById(R.id.memes_recycle_view);
        memesRecycler.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initListeners() {
        closeButton.setOnClickListener(v -> finish());

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                hideError();
                String searchQuery = searchEt.getText().toString();
                memesAdapter.getFilter().filter(searchQuery, count -> {
                    if (!searchQuery.isEmpty() && count == 0)
                        showError();
                });
            }
        });
    }

    private void showError() {
        memesRecycler.setVisibility(View.GONE);
        errorTv.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        errorTv.setVisibility(View.GONE);
        memesRecycler.setVisibility(View.VISIBLE);
    }
}
