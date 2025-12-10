package com.example.versestackapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    @ColumnInfo(name = "libraryCardId", index = true) // Adding index for fast lookup
    private int libraryCardId; // 👈 New Field

    private String username;
    private String password;
    private String role;
    private boolean isAdmin = false;

    // Constructor with required fields for initial insertion
    public User(int libraryCardId, String username, String password, String role) {
        this.libraryCardId = libraryCardId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // NOTE: Keep the no-arg constructor or a constructor matching all fields for Room reading

    // Getters and Setters (required by Room)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    // Getter/Setter for the new field
    public int getLibraryCardId() { return libraryCardId; }
    public void setLibraryCardId(int libraryCardId) { this.libraryCardId = libraryCardId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
}