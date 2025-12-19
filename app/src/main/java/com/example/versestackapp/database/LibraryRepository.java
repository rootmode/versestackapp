package com.example.versestackapp.database;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.versestackapp.Book;
import com.example.versestackapp.Borrow;
import com.example.versestackapp.User;

import java.util.List;

public class LibraryRepository {
    private final BorrowDAO mBorrowDAO;
    private final UserDAO mUserDAO;
    private final BookDAO mBookDAO;

    public LibraryRepository(Application application) {
        LibraryDatabase db = LibraryDatabase.getDatabase(application);
        mBorrowDAO = db.borrowDAO();
        mUserDAO = db.userDAO();
        mBookDAO = db.bookDAO();
    }

    public void insertUser(User user) {
        LibraryDatabase.databaseWriteExecutor.execute(() -> mUserDAO.insert(user));
    }

    public LiveData<User> getUserByUsername(String username) {
        return mUserDAO.getUserByUsername(username);
    }

    public LiveData<User> getUserById(int userId) {
        return mUserDAO.getUserByUserId(userId);
    }

    public void insertBook(Book book) {
        LibraryDatabase.databaseWriteExecutor.execute(() -> mBookDAO.insert(book));
    }

    public LiveData<Book> getBookByIsbn(int isbn) {
        return mBookDAO.getBookByIsbn(isbn);
    }

    public void deleteBookByIsbn(int isbn) {
        LibraryDatabase.databaseWriteExecutor.execute(() -> mBookDAO.deleteByIsbn(isbn));
    }

    public void insertBorrow(Borrow borrow) {
        LibraryDatabase.databaseWriteExecutor.execute(() -> {
            mBorrowDAO.insert(borrow);
            Log.i("Repository", "Book borrowed by User ID: " + borrow.getUserId());
        });
    }

    public void deleteBorrow(int borrowId) {
        LibraryDatabase.databaseWriteExecutor.execute(() -> mBorrowDAO.deleteByBorrowId(borrowId));
    }

    public LiveData<List<Borrow>> getBorrowsByUserId(int userId) {
        return mBorrowDAO.getBorrowsByUserId(userId);
    }
}