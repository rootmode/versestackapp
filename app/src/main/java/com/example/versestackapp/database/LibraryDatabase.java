package com.example.versestackapp.database;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.versestackapp.Book;
import com.example.versestackapp.Borrow;
import com.example.versestackapp.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Book.class, Borrow.class}, version = 1, exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract BookDAO BookDAO();
    public abstract BorrowDAO borrowDAO();

    private static final String DATABASE_NAME = "LibraryDatabase";
    private static volatile LibraryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static LibraryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LibraryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    LibraryDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("LibraryDatabase", "DATABASE CREATED!");
            db.execSQL("DELETE FROM users"); // clear table
            // adding data for testing main activity view
            db.execSQL("INSERT INTO users (libraryCardId, username, password, role, isAdmin) VALUES (1, 'TestUser', 'testpassword', 'user', 0)");
            db.execSQL("INSERT INTO books (title, isbn) VALUES ('OOP Fundamentals Guide', 5555)");
            db.execSQL("INSERT INTO books (title, isbn) VALUES ('National Geographic', 3333)");
        }
    };


}