package com.example.cvm.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvm.Admin.Hospital;
import com.example.cvm.Admin.create_slot;
import com.example.cvm.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

class RecyclerAdapter_hospitals extends RecyclerView.Adapter<RecyclerAdapter_hospitals.MyViewHolder> {

    Context context;
    ArrayList<Hospital> data;

    public RecyclerAdapter_hospitals(Context c, ArrayList<Hospital> p) {
        context = c;
        data = p;
    }

    @NonNull
    @Override
    public RecyclerAdapter_hospitals.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerAdapter_hospitals.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_hospitallist_user, parent, false));
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter_hospitals.MyViewHolder holder, final int position) {

        holder.Hospital_name.setText(data.get(position).getHospital_name());
        holder.Hospital_id.setText(data.get(position).getId());
        holder.Hospital_Address.setText(data.get(position).getHospital_address());
        holder.Hospital_number.setText(data.get(position).getHospital_number());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Hospital_name,Hospital_Address,Hospital_id,Hospital_number;

        @SuppressLint("UseCompatLoadingForDrawables")


        public MyViewHolder(View itemView) {
            super(itemView);

            Hospital_name=itemView.findViewById(R.id.hospitalName);
            Hospital_Address=itemView.findViewById(R.id.HospitalAddress);
            Hospital_id=itemView.findViewById(R.id.HospitalId);
            Hospital_number=itemView.findViewById(R.id.HospitalNumber);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}




