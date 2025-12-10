package com.example.versestackapp.database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.versestackapp.Borrow;

@Dao
public interface BorrowDAO {
    @Insert
    void insert(Borrow borrow);
}