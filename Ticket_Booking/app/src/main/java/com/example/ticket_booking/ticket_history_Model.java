package com.example.ticket_booking;

public class ticket_history_Model {
    private String date;
    private String seat;
    private String from;
    private String to;
    private String ticketClass;

    public ticket_history_Model(String date, String seat, String from, String to, String ticketClass) {
        this.date = date;
        this.seat = seat;
        this.from = from;
        this.to = to;
        this.ticketClass = ticketClass;
    }

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getTicketClass() { return ticketClass; }
    public void setTicketClass(String ticketClass) { this.ticketClass = ticketClass; }
}
