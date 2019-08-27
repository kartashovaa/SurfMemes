package com.kyd3snik.surfmemes.presenters;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemesListPresenter implements Observer<List<Meme>> {
    private MemesListView view;

    public MemesListPresenter(MemesListView view) {
        this.view = view;
    }

    public void showMemes() {
        MemesRepository.getMemes()
                .mergeWith(MemesRepository.getLocalMemes().toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    view.showMemes(list);
                    view.stopRefreshing();
                }, e -> {
                    view.stopRefreshing();
                    view.showLoadError();
                });
    }

    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        view.showMemes(memes);
    }

    public interface MemesListView {

        void showMemes(List<Meme> memes);

        void showLoadError();

        void stopRefreshing();
    }
}
