package com.example.kaue_parking;

public class Driver extends User{
    private int ticketID;
    public Driver() {
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
}
