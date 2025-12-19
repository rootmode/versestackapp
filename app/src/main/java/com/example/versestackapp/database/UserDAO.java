package com.example.versestackapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.versestackapp.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    LiveData<User> getUserByUserId(int userId);

    @Query("DELETE FROM users")
    void deleteAll();
}