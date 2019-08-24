package com.kyd3snik.surfmemes.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kyd3snik.surfmemes.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Fragment memesList;
    Fragment profile;
    Fragment active;
    Fragment errorLoad;

    private void setFragment(Fragment fragment) {
        fragmentTransaction.hide(active);
        fragmentTransaction.show(fragment);
        active = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getColor(R.color.colorBackground2));

        memesList = new MemesListFragment();
        profile = new ProfileFragment();
        errorLoad = new ErrorLoadFragment();
        active = memesList;
        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHolder, memesList, "memesList");
        fragmentTransaction.add(R.id.fragmentHolder, profile, "profile");
        fragmentTransaction.add(R.id.fragmentHolder, errorLoad, "errorLoad");
        fragmentTransaction.hide(profile);
        fragmentTransaction.hide(errorLoad);
        fragmentTransaction.show(active);
        fragmentTransaction.commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.memes_list:
                        setFragment(memesList);
                        break;
                    case R.id.add:
                        showAddMemeActivity();
                        return false;
                    case R.id.profile:
                        setFragment(profile);
                        break;

                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }

    void showAddMemeActivity() {
        Intent intent = new Intent(this, AddMemeActivity.class);
        startActivity(intent);
    }

}
