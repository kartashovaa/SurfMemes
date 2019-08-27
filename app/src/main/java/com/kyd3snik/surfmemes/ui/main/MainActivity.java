package com.kyd3snik.surfmemes.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.ui.profile.ProfileFragment;
import com.kyd3snik.surfmemes.utils.FragmentNav;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentNav fragmentNav;
    private Fragment memesList;
    private Fragment profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        initFragmentNav();
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.memes_list:
                    fragmentNav.show(memesList);
                    break;
                case R.id.add:
                    showAddMemeActivity();
                    return false;
                case R.id.profile:
                    if (profile == null) {
                        profile = new ProfileFragment();
                        fragmentNav.add(profile, "profile");
                    }
                    fragmentNav.show(profile);
                    break;
            }
            return true;
        });
    }

    private void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorBackground2));
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }

    private void initFragmentNav() {
        fragmentNav = new FragmentNav(getSupportFragmentManager());
        memesList = new MemesListFragment();
        fragmentNav.add(memesList, "memesList");
        fragmentNav.show(memesList);
    }

    private void showAddMemeActivity() {
        Intent intent = new Intent(this, AddMemeActivity.class);
        startActivity(intent);
    }
}
