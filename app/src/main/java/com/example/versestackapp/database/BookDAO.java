package com.example.versestackapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.versestackapp.Book;

@Dao
public interface BookDAO {
    @Insert
    void insert(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books WHERE isbn = :isbn LIMIT 1")
    LiveData<Book> getBookByIsbn(int isbn);

    @Query("DELETE FROM books WHERE isbn = :isbn")
    void deleteByIsbn(int isbn);
}