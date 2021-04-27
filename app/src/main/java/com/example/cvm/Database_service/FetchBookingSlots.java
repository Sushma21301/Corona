package com.example.cvm.Database_service;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Slot {
    public double bid, hid, time;
    public String hname, haddress;
    public int available_slots;

    public String toString() {
        return "Booking Id: " + bid + "Hospital Id: " + hid + "Time: " +
                time + "HospitalName: " + hname + "Hospital Address: " + haddress +
                "Available Slots: " + available_slots;
    }
}

interface FetchBookingsListener {
    void onFetch(List<Slot> slots);
}

public class FetchBookingSlots {
    private FetchBookingSlots() {}
    public static FetchBookingSlots shared = new FetchBookingSlots();

    public void fetch(double hid, FetchBookingsListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        List<Booking> bookings = new ArrayList<>();

        database.getReference("bookings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();

                while (dataSnapshots.hasNext()) {
                    DataSnapshot snap = dataSnapshots.next();
                    Object val = snap.getValue();
                    System.out.println(val);
                    Booking booking = snap.getValue(Booking.class);

                    if(booking.hid == hid)
                        bookings.add(booking);



                    database.getReference("hospitals").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();

                            Hospital hospital = null;

                            while (dataSnapshots.hasNext()) {
                                DataSnapshot snap = dataSnapshots.next();
                                Object val = snap.getValue();
                                System.out.println(val);
                                Hospital _hospital = snap.getValue(Hospital.class);

                                if(_hospital.hid == hid) {
                                    hospital = _hospital;
                                    break;
                                }

                            }

                            if (hospital == null) return;

                            List<Slot> slots = new ArrayList<>();
                            for(Booking booking: bookings) {
                                Slot slot = new Slot();
                                slot.bid = booking.bid;
                                slot.time = booking.time;
                                slot.available_slots = booking.max_patients_count - booking.filled_count;
                                slot.hid = booking.hid;
                                slot.hname = hospital.hname;
                                slot.haddress = hospital.haddress;

                                slots.add(slot);
                            }

                            listener.onFetch(slots);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("Data Error...");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Data Error...");
            }
        });

    }
}
