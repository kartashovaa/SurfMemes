package com.kyd3snik.surfmemes.repositories;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kyd3snik.surfmemes.models.Meme;

@Database(entities = {Meme.class}, version = 1)
public abstract class MemesDatabase extends RoomDatabase {
    public abstract MemesDao memesDao();
}
