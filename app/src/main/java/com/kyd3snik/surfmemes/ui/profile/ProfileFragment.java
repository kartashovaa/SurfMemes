package com.kyd3snik.surfmemes.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.models.Meme;
import com.kyd3snik.surfmemes.presenters.ProfilePresenter;
import com.kyd3snik.surfmemes.ui.login.LoginActivity;

import java.util.List;


public class ProfileFragment extends Fragment implements ProfilePresenter.ProfileView {
    private View rootView;
    private TextView nameTv;
    private TextView descriptionTv;
    private ImageButton menuIb;
    private RecyclerView favoriteMemesRv;
    private MemesAdapter memesAdapter;
    private PopupMenu popupMenu;
    private ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViews(View view) {
        nameTv = view.findViewById(R.id.username_tv);
        descriptionTv = view.findViewById(R.id.user_description_tv);
        menuIb = view.findViewById(R.id.menu_ib);

        favoriteMemesRv = view.findViewById(R.id.memes_rv);
        favoriteMemesRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        popupMenu = new PopupMenu(
                new ContextThemeWrapper(getActivity(), R.style.PopupMenu), menuIb, Gravity.CENTER);
        popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());
    }

    private void initListeners() {
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.about:
                    Snackbar.make(getView(), "App for watch memes", Snackbar.LENGTH_LONG).show();
                    break;
                case R.id.logout:
                    presenter.showExitDialogView();
                    break;
            }
            return true;

        });

        menuIb.setOnClickListener(v -> popupMenu.show());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter = new ProfilePresenter(getContext(), this);
        initListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void setName(String name) {
        nameTv.setText(name);
    }

    @Override
    public void setDescription(String description) {
        descriptionTv.setText(description);
    }

    @Override
    public void setMemes(List<Meme> memes) {
        if (memesAdapter == null) {
            memesAdapter = new MemesAdapter(memes, getActivity());
            favoriteMemesRv.setAdapter(memesAdapter);
        } else {
            memesAdapter.addMemes(memes);
            memesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadLoginActivity() {
        Activity activity = getActivity();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

}
