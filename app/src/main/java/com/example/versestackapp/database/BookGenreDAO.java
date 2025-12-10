package com.example.versestackapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.versestackapp.BookGenre;

import java.util.List;

@Dao
public interface BookGenreDAO {

    @Insert
    void insert(BookGenre genre);

    @Query("SELECT * FROM bookGenre")
    List<BookGenre> getAllGenres();

    @Query("SELECT * FROM bookGenre WHERE genreId = :genreId")
    BookGenre getGenreById(int genreId);

    @Query("DELETE FROM bookGenre")
    void deleteAll();
}