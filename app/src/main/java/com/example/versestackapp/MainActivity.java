package com.example.versestackapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.versestackapp.database.LibraryRepository;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "LibraryApp";

    private EditText libraryCardIdEditText;
    private EditText isbnEditText;
    private Button borrowButton;
    private LibraryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User interface initialization
        libraryCardIdEditText = findViewById(R.id.editTextLibraryCardId);
        isbnEditText = findViewById(R.id.editTextIsbn);
        borrowButton = findViewById(R.id.buttonBorrowBook);

        // Repository initialization
        repository = new LibraryRepository(getApplication());

        borrowButton.setOnClickListener(v -> {
            borrowBook();
        });
    }

    private void borrowBook() {
        // User input processing
        String userIdStr = libraryCardIdEditText.getText().toString();
        String isbnStr = isbnEditText.getText().toString();

        // User input validation
        if (userIdStr.isEmpty() || isbnStr.isEmpty()) {
            Toast.makeText(this, "Please enter Library Card ID and ISBN.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int userIdInput = Integer.parseInt(userIdStr);
            int isbnInput = Integer.parseInt(isbnEditText.getText().toString());

            // create borrow with input fields data
            Borrow newBorrow = new Borrow(userIdInput, isbnInput);

            // send borrow to database
            repository.insertBorrow(newBorrow);

            // display message to a user
            Toast.makeText(this, "You have borrowed a book", Toast.LENGTH_LONG).show();

            // clearing input fields
            libraryCardIdEditText.setText("");
            isbnEditText.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Exception has occurred.", Toast.LENGTH_SHORT).show();
        }
    }
}