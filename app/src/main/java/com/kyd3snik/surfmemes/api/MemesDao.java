package com.kyd3snik.surfmemes.api;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

@Dao
public interface MemesDao {
    @Query("SELECT * FROM meme;")
    LiveData<List<Meme>> getAll();

    @Insert
    void insert(Meme meme);
}
