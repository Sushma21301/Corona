package com.example.cvm.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cvm.R;
import com.example.cvm.User.User_Homepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Admin_registration_page extends AppCompatActivity {

    public Button register;
    public TextView signin_button;
    EditText Name,Hospital_Address,Hospital_name,Email,Password;
    FirebaseAuth mAuth;
    public String unique_key;
    DatabaseReference databaseReference;
    EditText phone_number,hospital_phone_number;
    public FirebaseUser user;
    public FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration_page);

        mAuth = FirebaseAuth.getInstance();
        signin_button=findViewById(R.id.sign_in_button);
        Name=findViewById(R.id.username);
        Hospital_Address=findViewById(R.id.hospital_address);
        Hospital_name=findViewById(R.id.hospital_name);
        phone_number=findViewById(R.id.Phone_number);
        hospital_phone_number=findViewById(R.id.hospital_number);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);

        unique_key= unique_key();
        final LottieAnimationView lottie_smily=findViewById(R.id.Covid_animation);
        lottie_smily.setSpeed(1);
        lottie_smily.playAnimation();

        register=findViewById(R.id.Register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_registration_page.this, Admin_login_page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void checkConnection(){
        databaseReference=FirebaseDatabase.getInstance().getReference("admin_signup");
        if(Name.length()==0){
            Name.setError("Enter Name!");
        }else if(Email.length()==0){
            Email.setError("Enter Email!");
        }else if(Hospital_Address.length()==0){
            Hospital_Address.setError("Enter Address!");
        }else if(phone_number.length()==0){
            phone_number.setError("Enter Phone Number!");
        }else if(hospital_phone_number.length()==0){
            hospital_phone_number.setError("Enter Phone Number!");
        }else if(Hospital_name.length()==0){
            Hospital_name.setError("Enter Age!");
        } else {
            final String Emails = Email.getText().toString();
            final String Names = Name.getText().toString();
            final String Hospital_addresss = Hospital_Address.getText().toString();
            final String Phone_number = phone_number.getText().toString();
            final String Hospital_number = hospital_phone_number.getText().toString();
            final String Hospital_names = Hospital_name.getText().toString();
            final String password = Password.getText().toString();

            user = FirebaseAuth.getInstance().getCurrentUser();

            mAuth.createUserWithEmailAndPassword(Emails, password)
                    .addOnCompleteListener(Admin_registration_page.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final admin_signup info = new admin_signup(Names, Hospital_addresss, Hospital_names, Phone_number, Hospital_number, Emails, unique_key, FirebaseAuth.getInstance().getCurrentUser().getUid());
                                final Hospital hospital_info = new Hospital(Hospital_addresss, Hospital_names, Hospital_number, unique_key);

                                FirebaseDatabase.getInstance().getReference("admin_signup").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            register.setVisibility(View.INVISIBLE);
                                            final LottieAnimationView lottiePreloader = findViewById(R.id.preloader_button);

                                            lottiePreloader.setVisibility(View.VISIBLE);
                                            lottiePreloader.setSpeed(1);
                                            lottiePreloader.playAnimation();

                                            Thread timer = new Thread() {
                                                public void run() {
                                                    try {
                                                        sleep(5000);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    } finally {


                                                        FirebaseDatabase.getInstance().getReference("Hospitals").child(unique_key)
                                                                .setValue(hospital_info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                            }
                                                        });
                                                    }
                                                }
                                            };
                                            timer.start();

                                        } else {
                                            Toast.makeText(Admin_registration_page.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(Admin_registration_page.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            database = FirebaseDatabase.getInstance();
            ref = database.getReference("Hospitals_List").push();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ref.child("hospital_address").setValue(Hospital_addresss);
                    ref.child("hospital_name").setValue(Hospital_names);
                    ref.child("hospital_number").setValue(Hospital_number);
                    ref.child("id").setValue(unique_key);


                    Intent intent = new Intent(Admin_registration_page.this, Admin_Homepage.class);
                    intent.putExtra("hospital_code", unique_key);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //code ends
        }
    }

    public String unique_key(){

        int length=5,i;
        char [] charr="0123456789".toCharArray();
        StringBuilder stringBuilder=new StringBuilder();
        Random random=new Random();
        for( i=0;i<length;i++){
            char c=charr[random.nextInt(charr.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}