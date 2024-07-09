package com.example.ticket_booking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class Sign_up extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private TextView signUpButton;
    ImageView imageView1;
    private Ticket_Base db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        db = new Ticket_Base(this);

        // Initialize UI elements
        usernameInput = findViewById(R.id.editText1);
        passwordInput = findViewById(R.id.editText2);
        signUpButton = findViewById(R.id.Continue);
        imageView1 = findViewById(R.id.back_To);

        // Set onClick listener for sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (username.isEmpty()) {
                    usernameInput.setError("Please enter a username");
                    usernameInput.requestFocus();
                } else if (password.isEmpty()) {
                    passwordInput.setError("Please enter a password");
                    passwordInput.requestFocus();
                } else if (password.length() < 8) {
                    passwordInput.setError("Password must be at least 8 characters");
                    passwordInput.requestFocus();
                    Toast.makeText(Sign_up.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the user already exists
                    if (db.isUserExists(username)) {
                        Toast.makeText(Sign_up.this, "User already exists. Please log in.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add the user to the database
                        Ticket_Model newUser = new Ticket_Model(username, password);
                        db.addTicket(newUser);
                        Toast.makeText(Sign_up.this, "User registered successfully. Please log in.", Toast.LENGTH_SHORT).show();

                        // Navigate back to the login page
                        Intent intent = new Intent(Sign_up.this, Log_in.class);
                        startActivity(intent);
                        finish(); // Close the sign-up activity
                    }
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_up.this, Log_in.class);
                startActivity(intent);
            }
        });
    }
}
