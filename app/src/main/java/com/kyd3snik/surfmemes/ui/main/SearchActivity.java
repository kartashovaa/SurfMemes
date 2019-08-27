package com.kyd3snik.surfmemes.ui.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements Observer<List<Meme>> {

    private EditText searchEt;
    private TextView errorTv;
    private ImageButton closeButton;
    private RecyclerView memesRecycler;
    private MemesAdapter memesAdapter;
    private List<Meme> allMemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        initListeners();
        loadMemes();
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
                showMemes(searchEt.getText().toString());
            }
        });
    }

    private void showMemes(String query) {
        errorTv.setVisibility(View.GONE);
        query = query.toLowerCase();
        if (query.isEmpty())
            memesRecycler.setVisibility(View.GONE);
        else {
            memesRecycler.setVisibility(View.VISIBLE);
            List<Meme> filterMemes = new ArrayList<>();
            for (Meme meme : allMemes)
                if (meme.title.toLowerCase().contains(query))
                    filterMemes.add(meme);

            if (filterMemes.isEmpty())
                showError();
            else if (memesAdapter == null) {
                memesAdapter = new MemesAdapter(filterMemes, this);
                memesRecycler.setAdapter(memesAdapter);
            } else {
                memesAdapter.setMemes(filterMemes);
                memesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showError() {
        memesRecycler.setVisibility(View.GONE);
        errorTv.setVisibility(View.VISIBLE);
    }

    private void addMemes(List<Meme> memes) {
        if (allMemes == null) {
            allMemes = memes;
            searchEt.setEnabled(true);
        } else
            allMemes.addAll(memes);
    }

    private void loadMemes() {
        MemesRepository.getAllMemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addMemes, e -> Log.d("ERROR_TAG", e.getMessage()));
    }


    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        addMemes(memes);
    }
}
