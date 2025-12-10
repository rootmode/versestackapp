package com.example.versestackapp.database;

import android.app.Application;
import android.util.Log;

import com.example.versestackapp.Book;
import com.example.versestackapp.Borrow;
import com.example.versestackapp.User;

public class LibraryRepository {
    private final BorrowDAO mBorrowDAO;
    private final UserDAO mUserDAO;
    private final BookDAO mBookDAO;

        // Constructor
        public LibraryRepository(Application application) {
            LibraryDatabase db = LibraryDatabase.getDatabase(application);
            mBorrowDAO = db.borrowDAO();
            mUserDAO = db.userDAO();
            mBookDAO = db.BookDAO();
        }

        // update record for a borrowed book
        public void insertBorrow(Borrow borrow) {
            LibraryDatabase.databaseWriteExecutor.execute(() -> {

                int inputCardId = borrow.getUserId();
                int inputIsbn = borrow.getIsbn();

                Integer inputUserId = mUserDAO.getUserIdByCardId(inputCardId);

                if (inputUserId == null) {
                    Log.e("Repository", "Error: Library Card ID " + inputCardId + " not found.");
                    return;
                }

                Integer inputBookId = mBookDAO.getBookIdByIsbn(inputIsbn);

                if (inputBookId == null) {
                    Log.e("Repository", "Error: Book with ISBN " + inputIsbn + " not found.");
                    return;
                }

                borrow.setUserId(inputUserId);
                borrow.setBookId(inputBookId);

                mBorrowDAO.insert(borrow);
                Log.i("Repository", "Book borrowing complete: User=" + inputUserId + ", Book=" + inputBookId);
            });
        }
    }