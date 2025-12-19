package com.example.versestackapp.database;

import android.content.Context;
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
    public abstract BookDAO bookDAO();
    public abstract BorrowDAO borrowDAO();

    private static volatile LibraryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static LibraryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LibraryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LibraryDatabase.class, "LibraryDatabase")
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
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = INSTANCE.userDAO();
                BookDAO bookDAO = INSTANCE.bookDAO();

                userDAO.insert(new User("testuser1", "testuser1", false));
                userDAO.insert(new User("admin", "admin", true));

                bookDAO.insert(new Book("Dino History", 5555));
                bookDAO.insert(new Book("Winter Mountaineering", 3333));
                bookDAO.insert(new Book("Python Handbook", 4444));
                bookDAO.insert(new Book("Children Stories", 1111));
                bookDAO.insert(new Book("Fantastic Beasts", 2222));
            });
        }
    };
}