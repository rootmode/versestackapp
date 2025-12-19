package com.example.versestackapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.example.versestackapp.database.LibraryRepository;
import com.example.versestackapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LibraryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new LibraryRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> verifyUser());
    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);

        userObserver.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userObserver.removeObserver(this);

                if (user != null && user.getPassword().equals(password)) {
                    SharedPreferences preferences = getSharedPreferences("versestack_prefs", MODE_PRIVATE);
                    preferences.edit().putInt("userId", user.getUserId()).apply();
                    startActivity(MainActivity.mainActivityIntentFactory(LoginActivity.this, user.getUserId()));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

}