package com.kyd3snik.surfmemes.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.kyd3snik.surfmemes.R;

public class FragmentNav {
    private FragmentManager fragmentManager;
    private Fragment active;

    public FragmentNav(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void add(Fragment fragment, String tag) {
        fragmentManager.beginTransaction().add(R.id.fragmentHolder, fragment, tag).commit();
        active = fragment;
    }

    public void show(Fragment fragment) {
        fragmentManager.beginTransaction().hide(active).show(fragment).commit();
        active = fragment;
    }
}
