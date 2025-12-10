package com.example.versestackapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.versestackapp.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT userId FROM users WHERE libraryCardId = :cardId")
    Integer getUserIdByCardId(int cardId);
}