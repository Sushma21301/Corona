package com.example.cvm.Database_service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateAdmin {
    private CreateAdmin() {}
    public static CreateAdmin shared = new CreateAdmin();

    void create(Admin admin, Hospital hospital) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference adminRef = database.getReference("admins");
//        ArrayList<Admin> li = new ArrayList<>();
//        li.add(new Admin());
//        li.add(new Admin());
//        myRef.setValue(li);
        adminRef.push().setValue(admin);

        DatabaseReference hospitalRef = database.getReference("hospitals");
        hospitalRef.push().setValue(hospital);
    }

}
