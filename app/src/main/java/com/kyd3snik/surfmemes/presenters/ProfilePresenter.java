package com.kyd3snik.surfmemes.presenters;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.repositories.MemesRepository;
import com.kyd3snik.surfmemes.utils.PrefUtil;

import java.util.List;

public class ProfilePresenter implements PopupMenu.OnMenuItemClickListener, MemesRepository.OnLoadedMemesListener, Observer<List<Meme>> {
    private Context context;
    private ProfileView view;

    public ProfilePresenter(Context context, ProfileView view) {
        this.view = view;
        this.context = context;
        view.setName(PrefUtil.getInstance().getString(PrefUtil.Keys.USERNAME));
        view.setDescription(PrefUtil.getInstance().getString(PrefUtil.Keys.USER_DESCRIPTION));
        showMemes();
    }

    private void showMemes() {
        MemesRepository.getLocalMemes().observe(view.getLivecycleOwner(), this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Snackbar.make(view.getView(), "App for watch memes", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.logout:
                showExitDialogView();
                break;
        }
        return true;

    }

    @Override
    public void onChanged(@Nullable List<Meme> memes) {
        view.setMemes(memes);
    }

    private void showExitDialogView() {
        new AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle("Действительно хотите выйти?")
                .setPositiveButton("ВЫЙТИ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NetworkService.getInstance().getAuthApi().logout();
                        PrefUtil.getInstance().clear();
                        view.loadLoginActivity();
                    }
                }).setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override
    public void OnSuccess(List<Meme> memes) {
        view.setMemes(memes);

    }

    @Override
    public void OnError(String errorMsg) {
        Snackbar.make(view.getView(), errorMsg, Snackbar.LENGTH_LONG).show();

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
