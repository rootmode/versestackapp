package com.example.versestackapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "borrow",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId", onDelete = CASCADE),
                @ForeignKey(entity = Book.class, parentColumns = "bookId", childColumns = "bookId", onDelete = CASCADE)
        }
)
public class Borrow {
    @PrimaryKey(autoGenerate = true)
    private int borrowId;

    private int userId;
    private int bookId; // Foreign Key for Book.bookId
    private int isbn;   // book ISBN

    // Constructor
    public Borrow(int userId, int isbn) {
        this.userId = userId;
        this.isbn = isbn;
        this.bookId = 0;
    }

    // Getters and Setters
    public int getBorrowId() { return borrowId; }
    public void setBorrowId(int borrowId) { this.borrowId = borrowId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public int getIsbn() { return isbn; }
    public void setIsbn(int isbn) { this.isbn = isbn; }
}