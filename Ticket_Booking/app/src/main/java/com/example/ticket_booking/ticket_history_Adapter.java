package com.example.ticket_booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ticket_history_Adapter extends RecyclerView.Adapter<ticket_history_Adapter.ViewHolder> {

    private Context context;
    private List<ticket_history_Model> tickets;

    public ticket_history_Adapter(List<ticket_history_Model> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_enter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ticket_history_Model ticket = tickets.get(position);
        holder.date.setText(ticket.getDate());
        holder.seat.setText(ticket.getSeat());
        holder.from.setText(ticket.getFrom());
        holder.to.setText(ticket.getTo());
        holder.ticketClass.setText(ticket.getTicketClass());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, seat, from, to, ticketClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
            seat = itemView.findViewById(R.id.seatTextView);
            from = itemView.findViewById(R.id.fromTextView);
            to = itemView.findViewById(R.id.toTextView);
            ticketClass = itemView.findViewById(R.id.classTextView);
        }
    }
}
