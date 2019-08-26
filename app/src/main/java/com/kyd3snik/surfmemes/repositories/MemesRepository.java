package com.kyd3snik.surfmemes.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.kyd3snik.surfmemes.api.MemesDatabase;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import retrofit2.Call;

public class MemesRepository {
    private static MemesDatabase memesDatabaseInstance;

    public static void initializeDatabase(Context context) {
        memesDatabaseInstance = Room.databaseBuilder(
                context, MemesDatabase.class, "memesDatabase")
                .allowMainThreadQueries()
                .build();
    }

    public static Call<List<Meme>> getMemes() {
        return NetworkService.getInstance().getMemeApi().getMemes();
    }


    private static void checkDatabase() {
        if (memesDatabaseInstance == null)
            throw new RuntimeException("Database doesn't initialized!");
    }

    public static LiveData<List<Meme>> getLocalMemes() {
        checkDatabase();
        return memesDatabaseInstance.memesDao().getAll();

    }

    public static void putMeme(Meme meme) {
        checkDatabase();
        memesDatabaseInstance.memesDao().insert(meme);
    }
}
