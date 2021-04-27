package com.example.cvm.Database_service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateBookingPanel {
    private CreateBookingPanel() {}
    public static CreateBookingPanel shared = new CreateBookingPanel();

    public void create(Booking booking) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bookings");
        myRef.push().setValue(booking);
    }
}
