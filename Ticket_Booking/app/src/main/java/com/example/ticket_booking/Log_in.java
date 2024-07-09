package com.example.ticket_booking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Log_in extends AppCompatActivity {
    private TextView login;
    private EditText usernameInput, passwordInput;
    private Ticket_Base db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database and shared preferences
        db = new Ticket_Base(this);
        sharedPreferences = getSharedPreferences("TicketBookingPrefs", Context.MODE_PRIVATE);

        // Initialize UI elements
        login = findViewById(R.id.Continue);
        usernameInput = findViewById(R.id.editText1);
        passwordInput = findViewById(R.id.editText2);

        // Set onClick listener for login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Check if the user exists in the database
                if (db.isUserExists(username)) {
                    // Check if the password matches
                    Ticket_Model user = db.getTicket(username);
                    if (user != null && user.getPassword().equals(password)) {
                        // Save username in SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                        Toast.makeText(Log_in.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                        // Proceed to Home_Page if credentials are correct
                        Intent intent = new Intent(Log_in.this, Home_Page.class);
                        startActivity(intent);
                    } else {
                        // Incorrect password
                        Toast.makeText(Log_in.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User does not exist
                    Toast.makeText(Log_in.this, "User does not exist. Please sign up.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
