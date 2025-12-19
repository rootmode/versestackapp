package com.example.versestackapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.versestackapp.database.LibraryRepository;
import java.util.List;

public class LibraryViewModel extends AndroidViewModel {

    private final LibraryRepository repository;

    public LibraryViewModel(@NonNull Application application) {
        super(application);
        repository = new LibraryRepository(application);
    }

    public LiveData<List<Borrow>> getBorrowsByUserId(int userId) {
        return repository.getBorrowsByUserId(userId);
    }

    public void insertBorrow(Borrow borrow) {
        repository.insertBorrow(borrow);
    }

    public void deleteBorrow(int borrowId) {
        repository.deleteBorrow(borrowId);
    }
}