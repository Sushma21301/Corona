package com.example.cvm.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cvm.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Admin_login_page extends AppCompatActivity {

    EditText Email,Password;
    TextView Sign_up;
    Button Login;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    public static String hospital_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);

        Login = findViewById(R.id.login_button);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Sign_up = findViewById(R.id.sign_up_button);

        mAuth = FirebaseAuth.getInstance();

        final LottieAnimationView lottie_smily = findViewById(R.id.Covid_animation);
        lottie_smily.setSpeed(1);
        lottie_smily.playAnimation();


        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin_login_page.this, Admin_registration_page.class);
                startActivity(intent);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });

    }

    public void checkConnection(){
            //normal authentication
            mAuth = FirebaseAuth.getInstance();
            final String email=Email.getText().toString();
            final String password=Password.getText().toString();

            if(Email.length()==0){
                Email.setError("Enter Email!");
            }else if(Password.length()==0){
                Password.setError("Enter Password!");
            }else{
                user =FirebaseAuth.getInstance().getCurrentUser();
                // FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                // hospital_code = "erwt";
                DatabaseReference userref= FirebaseDatabase.getInstance().getReference();
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

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Admin_login_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Login.setVisibility(View.INVISIBLE);

                                    final LottieAnimationView lottiePreloader=findViewById(R.id.preloader_button);

                                    lottiePreloader.setVisibility(View.VISIBLE);
                                    lottiePreloader.setSpeed(1);
                                    lottiePreloader.playAnimation();

                                    Thread timer = new Thread() {
                                        public void run() {
                                            try {
                                                sleep(3000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            } finally {
                                                Intent intent = new Intent(Admin_login_page.this,Admin_Homepage.class);
                                                intent.putExtra("hospital_code",hospital_code);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    };
                                    timer.start();

                                } else {
                                    Toast.makeText(Admin_login_page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        }


}