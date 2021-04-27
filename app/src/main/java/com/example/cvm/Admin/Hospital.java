package com.example.cvm.Admin;

public class Hospital {
    private String hospital_address,hospital_name,hospital_number,id;

    public Hospital() {
    }

    public Hospital(String hospital_address, String hospital_name, String hospital_number, String id) {
        this.hospital_address = hospital_address;
        this.hospital_name = hospital_name;
        this.hospital_number = hospital_number;
        this.id = id;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_number() {
        return hospital_number;
    }

    public void setHospital_number(String hospital_number) {
        this.hospital_number = hospital_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
