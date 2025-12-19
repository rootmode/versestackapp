package com.example.versestackapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.versestackapp.Borrow;

import java.util.List;

@Dao
public interface BorrowDAO {
    @Insert
    void insert(Borrow borrow);

    @Query("DELETE FROM borrow WHERE borrowId = :borrowId")
    void deleteByBorrowId(int borrowId);

    @Query("SELECT * FROM borrow WHERE userId = :userId")
    LiveData<List<Borrow>> getBorrowsByUserId(int userId);
}