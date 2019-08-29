package com.kyd3snik.surfmemes.presenters;

import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemesListPresenter {
    private MemesListView view;

    public MemesListPresenter(MemesListView view) {
        this.view = view;
    }

    public void showMemes() {
        view.showProgressBar();
        MemesRepository.getMemes()
                .mergeWith(MemesRepository.getLocalMemes())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(list -> {
                    view.showMemes(list);
                    view.stopRefreshing();
                    view.hideProgressBar();
                }, e -> {
                    view.stopRefreshing();
                    view.showLoadError();
                });
    }

    public interface MemesListView {

        void showMemes(List<Meme> memes);

        void showLoadError();

        void showProgressBar();

        void hideProgressBar();

        void stopRefreshing();

    }
}
