package com.kyd3snik.surfmemes;

import android.app.Application;

import com.kyd3snik.surfmemes.repositories.MemesRepository;
import com.kyd3snik.surfmemes.utils.PrefUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefUtil.initialize(getApplicationContext());
        MemesRepository.initializeDatabase(getApplicationContext());
    }
}
