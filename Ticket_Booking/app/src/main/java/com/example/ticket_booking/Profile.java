package com.example.ticket_booking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        TextView name = view.findViewById(R.id.text_name);
        TextView logout = view.findViewById(R.id.Log_out);
        ImageView history = view.findViewById(R.id.imageView4);
        ImageView ticket = view.findViewById(R.id.ImageView5);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TicketBookingPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");

        // Set username to the TextView
        name.setText(username);

        // Set up logout button click listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        // Set up history image button click listener
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.history, null);
                builder.setView(dialogView);

                ListView listView = dialogView.findViewById(R.id.listViewBookings);

                // Sample booking data
                List<Booking> bookingList = new ArrayList<>();
                bookingList.add(new Booking("Booking 1", "Details for Booking 1"));
                bookingList.add(new Booking("Booking 2", "Details for Booking 2"));
                bookingList.add(new Booking("Booking 3", "Details for Booking 3"));

                // Create and set the adapter
                BookingAdapter adapter = new BookingAdapter(getActivity(), bookingList);
                listView.setAdapter(adapter);

                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Set up ticket history click listener
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTicketHistoryDialog();
            }
        });
    }

    private void showTicketHistoryDialog() {
        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ticket History");

        // Inflate the RecyclerView layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ticket, null);
        builder.setView(dialogView);

        // Set up RecyclerView
        RecyclerView recyclerView = dialogView.findViewById(R.id.ticketRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Retrieve the ticket list from Home_Page
        Home_Page mainActivity = (Home_Page) getActivity();
        List<ticket_history_Model> ticketList = mainActivity.getTickets();

        // Create and set the adapter
        ticket_history_Adapter adapter = new ticket_history_Adapter(ticketList);
        recyclerView.setAdapter(adapter);

        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
