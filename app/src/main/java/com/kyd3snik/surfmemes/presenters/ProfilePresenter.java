package com.kyd3snik.surfmemes.presenters;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;
import com.kyd3snik.surfmemes.storages.UserStorage;

import java.util.List;

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
        MemesRepository.getLocalMemes().observe(view.getLivecycleOwner(), this);
    }


    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        view.setMemes(memes);
    }

    public void showExitDialogView() {
        new AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle("Действительно хотите выйти?")
                .setPositiveButton("ВЫЙТИ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkService.getInstance().getAuthApi().logout();
                        UserStorage.clear();
                        view.loadLoginActivity();
                    }
                }).setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public interface ProfileView {
        void setName(String name);

        void setDescription(String description);

        void setMemes(List<Meme> memes);

        View getView();

        LifecycleOwner getLivecycleOwner();

        void loadLoginActivity();
    }
}
