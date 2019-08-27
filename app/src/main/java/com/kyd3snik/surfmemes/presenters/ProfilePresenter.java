package com.kyd3snik.surfmemes.presenters;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;
import com.kyd3snik.surfmemes.storages.UserStorage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements Observer<List<Meme>> {
    private Context context;
    private ProfileView view;

    public ProfilePresenter(Context context, ProfileView view) {
        this.view = view;
        this.context = context;
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


    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        view.setMemes(memes);
    }

    public void showExitDialogView() {
        new AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle("Действительно хотите выйти?")
                .setPositiveButton("ВЫЙТИ", (dialog, which) -> {
                    NetworkService.getInstance().getAuthApi().logout();
                    UserStorage.clear();
                    view.loadLoginActivity();
                }).setNegativeButton("ОТМЕНА", (dialog, which) -> {
        }).show();
    }

    public interface ProfileView {
        void setName(String name);

        void setDescription(String description);

        void setMemes(List<Meme> memes);

        void loadLoginActivity();
    }
}
