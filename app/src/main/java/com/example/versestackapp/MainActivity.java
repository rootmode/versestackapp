package com.example.versestackapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.versestackapp.database.LibraryRepository;
import com.example.versestackapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LibraryRepository repository;
    private int loggedInUserId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = new LibraryRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("USER_ID", -1);
        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = LoginActivity.loginIntentFactory(MainActivity.this);
            startActivity(intent);
            finish();
        });

        repository.getUserById(loggedInUserId).observe(this, user -> {
            if (user != null) {
                currentUser = user;
                setupRoleBasedUI();
            }
        });
    }

    private void setupRoleBasedUI() {
        if (currentUser.isAdmin()) {
            binding.adminLayout.setVisibility(View.VISIBLE);
            binding.userLayout.setVisibility(View.GONE);
            setupAdminListeners();
        } else {
            binding.adminLayout.setVisibility(View.GONE);
            binding.userLayout.setVisibility(View.VISIBLE);
            setupUserListeners();
            setupRecyclerView();
        }
    }

    private void setupUserListeners() {
        binding.findBookButton.setOnClickListener(v -> {
            String isbn = binding.isbnInput.getText().toString();
            if (!isbn.isEmpty()) {
                repository.getBookByIsbn(Integer.parseInt(isbn)).observe(this, book -> {
                    if (book != null) {
                        binding.bookTitleDisplay.setText(book.getTitle());
                        binding.borrowButton.setVisibility(View.VISIBLE);
                        binding.borrowButton.setOnClickListener(view -> {
                            borrowBook(book);
                        });
                    } else {
                        binding.bookTitleDisplay.setText("Book not found");
                        binding.borrowButton.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void borrowBook(Book book) {
        if (loggedInUserId == -1) {
            Toast.makeText(this, "User is not found in the library.", Toast.LENGTH_SHORT).show();
            return;
        }

        Borrow newBorrow = new Borrow(loggedInUserId, book.getBookId(), book.getIsbn());
        repository.insertBorrow(newBorrow);
        binding.borrowButton.setVisibility(View.GONE);
        binding.isbnInput.setText("");
        binding.bookTitleDisplay.setText("Book borrowed successfully!");
    }

    private void setupAdminListeners() {
        binding.returnBookButton.setOnClickListener(v -> {
            int borrowId = Integer.parseInt(binding.borrowIdInput.getText().toString());
            repository.deleteBorrow(borrowId);
        });

        binding.addBookButton.setOnClickListener(v -> {
            int isbn = Integer.parseInt(binding.adminIsbnInput.getText().toString());
            String title = binding.adminTitleInput.getText().toString();
            repository.insertBook(new Book(title, isbn));
        });
    }

    private void setupRecyclerView() {
        LibraryAdapter adapter = new LibraryAdapter(new LibraryAdapter.BorrowDiff());
        binding.borrowedBooksRecyclerView.setAdapter(adapter);
        binding.borrowedBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getBorrowsByUserId(loggedInUserId).observe(this, borrows -> {
            adapter.submitList(borrows);
        });
    }

    public static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("USER_ID", userId);
        return intent;
    }
}