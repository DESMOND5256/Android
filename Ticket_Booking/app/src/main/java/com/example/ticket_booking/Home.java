package com.example.ticket_booking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Home extends Fragment {

    private final String[] fromLocations = {"Iddo", "Ebute Metta", "Mile 2", "Apapa", "Agege", "Ikeja", "Yaba", "Oshodi"};
    private final String[] toLocations = {"Benin City", "Owerri", "Onitsha", "Abuja", "Nnewi"};
    private final String[] numberOfPeople = {"+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10", "+11", "+12", "+13", "+14", "+15"};
    private final String[] trainClasses = {"First Class", "Business Class", "Sleeper Class", "Second Class", "Economy Class"};

    private final Map<String, Integer> classPrices = new HashMap<String, Integer>() {{
        put("First Class", 5500);
        put("Business Class", 5000);
        put("Sleeper Class", 4500);
        put("Second Class", 4000);
        put("Economy Class", 3500);
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI elements
        AutoCompleteTextView autoCompleteTextViewFrom = view.findViewById(R.id.auto_complete_text_from);
        AutoCompleteTextView autoCompleteTextViewTo = view.findViewById(R.id.auto_complete_text_to);
        DatePicker datePicker = view.findViewById(R.id.date_picker);
        AutoCompleteTextView autoCompleteTextViewPeople = view.findViewById(R.id.auto_complete_text_people);
        AutoCompleteTextView autoCompleteTextViewClass = view.findViewById(R.id.auto_complete_text_class);

        // Create adapters for AutoCompleteTextViews
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, fromLocations);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, toLocations);
        ArrayAdapter<String> peopleAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, numberOfPeople);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, trainClasses);

        // Set adapters to AutoCompleteTextViews
        autoCompleteTextViewFrom.setAdapter(fromAdapter);
        autoCompleteTextViewTo.setAdapter(toAdapter);
        autoCompleteTextViewPeople.setAdapter(peopleAdapter);
        autoCompleteTextViewClass.setAdapter(classAdapter);

        // Set default date to today's date for the DatePicker
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(currentYear, currentMonth, currentDay, null);

        // Add a listener to the DatePicker to enforce date constraints
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int finalCurrentYear = currentYear;
            final int finalCurrentMonth = currentMonth;
            final int finalCurrentDay = currentDay;
            datePicker.setOnDateChangedListener((view1, year, monthOfYear, dayOfMonth) -> {
                // Compare selected date with current date
                if (year < finalCurrentYear || (year == finalCurrentYear && monthOfYear < finalCurrentMonth) ||
                        (year == finalCurrentYear && monthOfYear == finalCurrentMonth && dayOfMonth < finalCurrentDay)) {
                    // Reset the DatePicker to current date
                    datePicker.init(finalCurrentYear, finalCurrentMonth, finalCurrentDay, null);
                } else if (year > finalCurrentYear) {
                    // Reset the year to current year if it exceeds the current year
                    datePicker.updateDate(finalCurrentYear, monthOfYear, dayOfMonth);
                }
            });
        }

        // Button to initiate payment
        TextView paying = view.findViewById(R.id.Pay_up);
        paying.setOnClickListener(v -> {
            // Show the seat selection dialog first
            showSeatSelectionDialog((date, seat, selectedClass) -> {
                // After dismissing the seat selection dialog, show the pay up dialog
                showPayUpLayout(date, seat, selectedClass, () -> showCardDemoLayout(selectedClass));
            });
        });

        return view;
    }

    private void showSeatSelectionDialog(SeatSelectionCallback callback) {
        // Create a LayoutInflater instance
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate the custom layout for the dialog
        View dialogView = inflater.inflate(R.layout.custom_layout, null);

        // Find views in the custom layout
        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        Spinner classSpinner = dialogView.findViewById(R.id.classSpinner);
        TextView confirmButton = dialogView.findViewById(R.id.confirmButton);

        // Set title
        titleTextView.setText("Select Your Seat");

        // Create an ArrayAdapter with class options based on the selected class
        ArrayAdapter<String> adapter;
        String selectedClass = ((AutoCompleteTextView) requireView().findViewById(R.id.auto_complete_text_class)).getText().toString();
        if (selectedClass.equals("First Class")) {
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.first_class_seats_array));
        } else if (selectedClass.equals("Business Class")) {
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.business_class_seats_array));
        } else if (selectedClass.equals("Sleeper Class")) {
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sleeper_class_seats_array));
        } else if (selectedClass.equals("Second Class")) {
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.second_class_seats_array));
        } else {
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.economy_class_seats_array));
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        classSpinner.setAdapter(adapter);

        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the custom layout for the dialog
        builder.setView(dialogView);

        // Set the dialog to be non-cancelable
        builder.setCancelable(false);

        // Create the AlertDialog
        final AlertDialog alertDialog = builder.create();

        // Set onClickListener for confirm button
        confirmButton.setOnClickListener(v1 -> {
            // Get the selected class
            String selectedSeat = classSpinner.getSelectedItem().toString();
            // Get the selected date
            DatePicker datePicker = requireView().findViewById(R.id.date_picker);
            String selectedDate = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();

            // Show the selected class and date in a Toast
            Toast.makeText(getActivity(), "Selected Class: " + selectedSeat + "\nSelected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

            // Dismiss the custom layout dialog
            alertDialog.dismiss();

            // Trigger the callback with the selected date, seat, and class
            callback.onSeatSelected(selectedDate, selectedSeat, selectedClass);
        });

        // Show the AlertDialog
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showPayUpLayout(String date, String seat, String selectedClass, Runnable afterDismiss) {
        // Create a LayoutInflater instance
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate the pay_up layout for the dialog
        View payUpView = inflater.inflate(R.layout.pay_up, null);

        // Find views in the pay_up layout
        TextView textViewDate = payUpView.findViewById(R.id.Date);
        TextView textViewSeat = payUpView.findViewById(R.id.Seat);
        TextView buttonDismiss = payUpView.findViewById(R.id.dismiss);

        // Set the date and seat information
        textViewDate.setText("Date: " + date);
        textViewSeat.setText("Seat: " + seat);

        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the pay_up layout for the dialog
        builder.setView(payUpView);

        // Create the AlertDialog
        final AlertDialog alertDialog = builder.create();

        // Set onClickListener for dismiss button
        buttonDismiss.setOnClickListener(v -> {
            alertDialog.dismiss();
            // Run the afterDismiss runnable to show the next dialog
            afterDismiss.run();
        });

        // Show the AlertDialog
        alertDialog.show();
    }

    private void showCardDemoLayout(String selectedClass) {
        // Create a LayoutInflater instance
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate the custom layout for the dialog
        View cardInformationView = inflater.inflate(R.layout.demo_payment, null);

        // Find the EditText and TextView elements in the custom layout
        EditText cardNumber = cardInformationView.findViewById(R.id.Card_number);
        EditText month = cardInformationView.findViewById(R.id.Month_year);
        EditText cvc = cardInformationView.findViewById(R.id.CVC);
        TextView confirm = cardInformationView.findViewById(R.id.confirmPayment);

        // Get the price for the selected class
        int price = classPrices.get(selectedClass);

        // Set the confirm button text to show the price
        confirm.setText("Pay ₦" + price);

        // Disable the confirm button initially
        confirm.setEnabled(false);

        // Create a TextWatcher to monitor changes in the EditText fields
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check if all fields are filled
                boolean isCardNumberFilled = cardNumber.getText().toString().trim().length() >= 12;
                boolean isMonthFilled = month.getText().toString().trim().length() >= 4; // Assuming MM/YY format
                boolean isCvcFilled = cvc.getText().toString().trim().length() >= 3;

                // Enable the confirm button if all fields are filled
                confirm.setEnabled(isCardNumberFilled && isMonthFilled && isCvcFilled);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        };

        // Add the TextWatcher to the EditText fields
        cardNumber.addTextChangedListener(textWatcher);
        month.addTextChangedListener(textWatcher);
        cvc.addTextChangedListener(textWatcher);

        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(cardInformationView);

        // Create the AlertDialog
        final AlertDialog alertDialog = builder.create();

        // Set a click listener for the Confirm TextView to dismiss the dialog
        confirm.setOnClickListener(v -> {
            // Get the card information entered by the user
            String cardNumberText = cardNumber.getText().toString();
            String monthText = month.getText().toString();
            String cvcText = cvc.getText().toString();

            // Display the card information in a Toast
            Toast.makeText(getActivity(), "Card Number: " + cardNumberText + "\nMonth/Year: " + monthText + "\nCVC: " + cvcText + "\nAmount: " + price + " USD", Toast.LENGTH_SHORT).show();

            // Add ticket details to the shared data structure
            Home_Page mainActivity = (Home_Page) getActivity();
            mainActivity.addTicket(new ticket_history_Model("Selected Date", "Selected Seat", "From Location", "To Location", selectedClass));

            // Dismiss the card information dialog
            alertDialog.dismiss();
        });

        // Show the AlertDialog
        alertDialog.show();
    }
    private void showTicketDetails(ticket_history_Model ticket) {
        // Create a LayoutInflater instance
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate the custom layout for the dialog
        View ticketDetailsView = inflater.inflate(R.layout.ticket_enter, null);

        // Find the TextView elements in the custom layout
        TextView date = ticketDetailsView.findViewById(R.id.dateTextView);
        TextView seat = ticketDetailsView.findViewById(R.id.seatTextView);
        TextView destinationFrom = ticketDetailsView.findViewById(R.id.fromTextView);
        TextView destinationTo = ticketDetailsView.findViewById(R.id.toTextView);
        TextView travelClass = ticketDetailsView.findViewById(R.id.classTextView);

        // Set the ticket details
        date.setText(ticket.getDate());
        seat.setText(ticket.getSeat());
        destinationFrom.setText(ticket.getFrom());
        destinationTo.setText(ticket.getTo());
        travelClass.setText(ticket.getTicketClass());

        // Create an AlertDialog.Builder instance
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(ticketDetailsView);
        builder.setTitle("Ticket Details");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        // Show the AlertDialog
        builder.show();
    }

    // Callback interface for seat selection
    interface SeatSelectionCallback {
        void onSeatSelected(String date, String seat, String selectedClass);
    }
}
