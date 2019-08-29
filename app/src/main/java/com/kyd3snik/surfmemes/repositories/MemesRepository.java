package com.kyd3snik.surfmemes.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MemesRepository {
    private static MemesDatabase memesDatabaseInstance;

    public static void initializeDatabase(Context context) {
        memesDatabaseInstance = Room.databaseBuilder(
                context, MemesDatabase.class, "memesDatabase")
                .build();
    }

    public static Observable<List<Meme>> getMemes() {
        return NetworkService.getInstance().getMemeApi().getMemes();
    }


    private static void checkDatabase() {
        if (memesDatabaseInstance == null)
            throw new RuntimeException("Database doesn't initialized!");
    }

    public static Observable<List<Meme>> getLocalMemes() {
        checkDatabase();
        return memesDatabaseInstance.memesDao().getAll().toObservable();

    }

    public static Observable<List<Meme>> getAllMemes() {
        return getMemes().mergeWith(getLocalMemes());
    }

    public static void putMeme(Meme meme) {
        checkDatabase();
        Observable.just(meme).subscribeOn(Schedulers.io()).subscribe(mem -> memesDatabaseInstance.memesDao().insert(mem));
    }
}
