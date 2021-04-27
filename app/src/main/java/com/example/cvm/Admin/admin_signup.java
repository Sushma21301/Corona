package com.example.cvm.Admin;

public class admin_signup {

    public String Username,Hospital_address,Hospital_name,phone_number,hospital_number,Email,Id,Userid;

    public admin_signup() {
    }

    public admin_signup(String username, String hospital_addresss, String hospital_name, String phone_number, String hospital_number, String email, String id, String userid) {
        this.Username = username;
        this.Hospital_address = hospital_addresss;
        this.Hospital_name = hospital_name;
        this.phone_number = phone_number;
        this.hospital_number = hospital_number;
        this.Email = email;
        this.Id = id;
        this.Userid = userid;
    }
}
