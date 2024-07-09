package com.example.ticket_booking;

public class Ticket_Model {
    private String username;
    private String password;

    // Constructor
    public Ticket_Model(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // toString method
    @Override
    public String toString() {
        return "Ticket_Model{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
