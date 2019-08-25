package com.kyd3snik.surfmemes.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.kyd3snik.surfmemes.api.MemesDatabase;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemesRepository {
    private static MemesDatabase memesDatabaseInstance;

    public static void initializeDatabase(Context context) {
        memesDatabaseInstance = Room.databaseBuilder(
                context, MemesDatabase.class, "memesDatabase")
                .allowMainThreadQueries()
                .build();
    }

    public static void getMemes(final OnLoadedMemesListener listener) {
        NetworkService.getInstance().getMemeApi().getMemes().enqueue(new Callback<List<Meme>>() {
            @Override
            public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
                if (response.isSuccessful())
                    listener.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Meme>> call, Throwable t) {
                listener.OnError("Network fault");
            }
        });
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

    public interface OnLoadedMemesListener {
        void OnSuccess(List<Meme> memes);

        void OnError(String errorMsg);
    }
}
