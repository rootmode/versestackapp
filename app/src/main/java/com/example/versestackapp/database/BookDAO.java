package com.example.versestackapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.versestackapp.Book;

@Dao
public interface BookDAO {
    @Insert
    void insert(Book book);

    @Query("SELECT bookId FROM books WHERE isbn = :isbn")
    Integer getBookIdByIsbn(int isbn);
}