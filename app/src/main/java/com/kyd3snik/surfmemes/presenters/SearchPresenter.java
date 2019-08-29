package com.kyd3snik.surfmemes.presenters;

import android.util.Log;

import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter {

    private SearchView view;

    public SearchPresenter(SearchView view) {
        this.view = view;
    }

    public void loadMemes() {
        MemesRepository.getAllMemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::addMemes, e -> Log.d("ERROR_TAG", e.getMessage()));
    }

    public interface SearchView {
        void addMemes(List<Meme> memes);
    }
}
