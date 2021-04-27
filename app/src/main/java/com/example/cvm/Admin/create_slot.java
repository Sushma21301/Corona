package com.example.cvm.Admin;

public class create_slot {

    String Date,Time_slot,Max_Patient;

    public create_slot() {
    }

    public create_slot(String date, String time_slot, String max_Patient) {
        Date = date;
        Time_slot = time_slot;
        Max_Patient = max_Patient;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime_slot() {
        return Time_slot;
    }

    public void setTime_slot(String time_slot) {
        Time_slot = time_slot;
    }

    public String getMax_Patient() {
        return Max_Patient;
    }

    public void setMax_Patient(String max_Patient) {
        Max_Patient = max_Patient;
    }
}
