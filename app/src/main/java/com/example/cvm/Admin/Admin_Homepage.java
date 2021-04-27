package com.example.cvm.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cvm.R;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cvm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Admin_Homepage extends AppCompatActivity {

   public TextView Name;
   public Button Create;
   public ImageView Notification;
   public EditText Date,Time_slot,Patients;
   public Button Submit;
   public  String hospital_code;
   public RecyclerView recyclerView;
   public DatabaseReference ref2;
   public ArrayList<create_slot> list;
   public RecyclerAdapter_slots adapter;
   public DatabaseReference databaseReference;
   public FirebaseAuth auth;
    FirebaseUser user;
    private DatabaseReference ref;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__homepage);

        Name=findViewById(R.id.username);
        Create=(Button)findViewById(R.id.create);
        Notification=findViewById(R.id.notification);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<create_slot>();
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Name.setText("hello "+"Harini");

        hospital_code = getIntent().getStringExtra("hospital_code");

        auth = FirebaseAuth.getInstance();
        user =FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
       // FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
       // hospital_code = "erwt";
      /*  DatabaseReference userref= FirebaseDatabase.getInstance().getReference();
        DatabaseReference myref= userref.child("admin_signup").child(user.getUid()).child("Id");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    hospital_code = (Objects.requireNonNull(dataSnapshot.getValue()).toString());
               // hospital_code=dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
        Create.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          buttonclick();
                                          listitems();
                                      }
                                  });

        listitems();
    }
    public void listitems(){
        ref2 = FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(hospital_code).child("Slots");
        ref2.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3)
            {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot3.getChildren()) {

                    create_slot p = dataSnapshot1.getValue(create_slot.class);
                    list.add(p);
                }
                adapter = new RecyclerAdapter_slots(Admin_Homepage.this, list);
                recyclerView.setAdapter(adapter);


            }


            // recyclerView.setBackgroundColor(randomAndroidColor);


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void buttonclick(){

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Admin_Homepage.this);
                View mview = getLayoutInflater().inflate(R.layout.activity_slot_form, null);

                Date=mview.findViewById(R.id.date);
                Time_slot=mview.findViewById(R.id.time_slot);
                Patients=mview.findViewById(R.id.patients);
                Submit=mview.findViewById(R.id.submit);

                builder1.setView(mview);
                final AlertDialog dialog1 = builder1.create();
                dialog1.show();

                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String date = Date.getText().toString();
                        String time_slot = Time_slot.getText().toString();
                        String patients = Patients.getText().toString();

                       /* create_slot info = new create_slot(date,time_slot,patients);

                        FirebaseDatabase.getInstance().getReference("Hospitals").child(hospital_code).child("Slots")
                                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Admin_Homepage.this,"Slot added!",Toast.LENGTH_LONG).show();


                            }
                        });*/

                        ref = database.getReference("Hospitals").child(hospital_code).child("Slots").push();
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                ref.child("Date").setValue(date);
                                ref.child("Time_slot").setValue(time_slot);
                                ref.child("Max_Patient").setValue(patients);
                                Toast.makeText(Admin_Homepage.this,"Slot added!",Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    });
                }

}