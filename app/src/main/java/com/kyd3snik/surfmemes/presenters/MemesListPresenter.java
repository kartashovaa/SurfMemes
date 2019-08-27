package com.kyd3snik.surfmemes.presenters;

import android.arch.lifecycle.LifecycleOwner;
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
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    view.showMemes(list);
                view.stopRefreshing();
                });
//        MemesRepository.getLocalMemes().observe(view.getLifecycleOwner(), this);
//        MemesRepository.getMemes().enqueue(new Callback<List<Meme>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Meme>> call, @NonNull Response<List<Meme>> response) {
//                if (response.isSuccessful())
//                    view.showMemes(response.body());
//                else
//                    view.showLoadError();
//                view.stopRefreshing();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Meme>> call, @NonNull Throwable t) {
//                view.stopRefreshing();
//                view.showLoadError();
//            }
//        });
    }

    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        view.showMemes(memes);
    }

    public interface MemesListView {

        void showMemes(List<Meme> memes);

        LifecycleOwner getLifecycleOwner();

        void showLoadError();

        void stopRefreshing();
    }
}
