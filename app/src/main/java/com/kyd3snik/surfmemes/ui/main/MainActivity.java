package com.kyd3snik.surfmemes.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.utils.FragmentNav;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentNav fragmentNav;
    private Fragment memesList;
    private Fragment profile;
    private Fragment errorLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.memes_list:
                        fragmentNav.show(memesList);
                        break;
                    case R.id.add:
                        showAddMemeActivity();
                        return false;
                    case R.id.profile:
                        fragmentNav.show(profile);
                        break;
                }
                return true;
            }
        });
    }

    private void initViews() {
        getWindow().setStatusBarColor(getColor(R.color.colorBackground2));
        fragmentNav = new FragmentNav(getSupportFragmentManager());
        memesList = new MemesListFragment();
        profile = new ProfileFragment();
        errorLoad = new ErrorLoadFragment();
        fragmentNav.add(memesList);
        fragmentNav.add(profile);
        fragmentNav.add(errorLoad);
        fragmentNav.show(memesList);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }

    void showAddMemeActivity() {
        Intent intent = new Intent(this, AddMemeActivity.class);
        startActivity(intent);
    }

    public void showErrorLoadFragment() {
        fragmentNav.show(errorLoad);
    }

}
