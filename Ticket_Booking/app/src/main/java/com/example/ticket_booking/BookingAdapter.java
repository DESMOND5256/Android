package com.example.ticket_booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
public class BookingAdapter extends ArrayAdapter<Booking> {
    public BookingAdapter(Context context, List<Booking> bookings) {
        super(context, 0, bookings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Booking booking = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.booking_enter, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.tvBookingTitle);
        TextView tvDetails = convertView.findViewById(R.id.tvBookingDetails);

        tvTitle.setText(booking.getTitle());
        tvDetails.setText(booking.getDetails());

        return convertView;
    }
}

