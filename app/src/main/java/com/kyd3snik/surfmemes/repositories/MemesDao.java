package com.kyd3snik.surfmemes.repositories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MemesDao {
    @Query("SELECT * FROM meme;")
    Flowable<List<Meme>> getAll();

    @Insert
    void insert(Meme meme);
}
