package com.example.versestackapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookGenre")
public class BookGenre {
    @PrimaryKey(autoGenerate = true)
    private int genreId;
    private String genreName;

    public BookGenre(String genreName) {
        this.genreName = genreName;
    }

    // Getters and Setters (required by Room)
    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }
    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }
}