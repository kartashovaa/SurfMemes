package com.kyd3snik.surfmemes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainScreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Fragment memesList;
    Fragment profile;
    Fragment active;
    Fragment errorLoad;
    private void setFragment(Fragment fragment) {
        fragmentTransaction.detach(active);
        fragmentTransaction.attach(fragment);
        active = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getWindow().setStatusBarColor(getColor(R.color.colorBackground2));

        memesList = new MemesListFragment();
        profile = new ProfileFragment();
        errorLoad = new ErrorLoadFragment();
        active = memesList;
        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, memesList,"memesList");
        fragmentTransaction.add(R.id.frameLayout, profile, "profile");
        fragmentTransaction.add(R.id.frameLayout, errorLoad,"errorLoad");
        fragmentTransaction.detach(profile);
        fragmentTransaction.detach(errorLoad);
        fragmentTransaction.attach(active);
        fragmentTransaction.commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragmentTransaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.memes_list:
                        setFragment(memesList);
                        break;
                    case R.id.add:
                        Snackbar.make(findViewById(R.id.root),"TODO:",Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.profile:
                        setFragment(profile);
                        break;

                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }

}
