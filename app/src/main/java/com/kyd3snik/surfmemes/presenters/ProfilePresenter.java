package com.kyd3snik.surfmemes.presenters;

import android.util.Log;

import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;
import com.kyd3snik.surfmemes.repositories.UserStorage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter {
    private ProfileView view;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
    }

    public void renderView() {
        view.setName(UserStorage.getUserName());
        view.setDescription(UserStorage.getUserDescription());
        showMemes();
    }

    private void showMemes() {
        MemesRepository.getLocalMemes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(memes -> view.setMemes(memes),
                        e -> Log.d("ERROR_TAG", e.getMessage()));
    }

    public interface ProfileView {
        void setName(String name);

        void setDescription(String description);

        void setMemes(List<Meme> memes);

    }
}
