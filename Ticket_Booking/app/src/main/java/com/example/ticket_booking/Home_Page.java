package com.example.ticket_booking;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ticket_booking.databinding.ActivityHomePageBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity {

    ActivityHomePageBinding binding;
    private List<ticket_history_Model> ticketList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.BottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.Home) {
                    replaceFragment(new Home());
                } else if (menuItem.getItemId() == R.id.Profile) {
                    replaceFragment(new Profile());
                } else if (menuItem.getItemId() == R.id.Settings) {
                    replaceFragment(new Settings());
                }

                return true;
            }
        });
        // Set default fragment
        if (savedInstanceState == null) {
            replaceFragment(new Home());
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Framing, fragment); // Replace the frame layout with the fragment
        fragmentTransaction.commit();
    }

    // Method to add a ticket to the list
    public void addTicket(ticket_history_Model ticket) {
        ticketList.add(ticket);
    }

    // Method to retrieve the list of tickets
    public List<ticket_history_Model> getTickets() {
        return ticketList;
    }
}
