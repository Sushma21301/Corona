package com.example.cvm.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cvm.Admin.Admin_Homepage;
import com.example.cvm.Admin.Admin_login_page;
import com.example.cvm.Admin.Admin_registration_page;
import com.example.cvm.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_Login_page extends AppCompatActivity {
    EditText Email,Password;
    TextView Sign_up;
    Button Login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__login_page);

        Login = findViewById(R.id.login_button);
        Email = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Sign_up = findViewById(R.id.sign_in_button);


        //checkbox checking starts

        mAuth = FirebaseAuth.getInstance();

        final LottieAnimationView lottie_smily = findViewById(R.id.Covid_animation);
        lottie_smily.setSpeed(1);
        lottie_smily.playAnimation();


        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(User_Login_page.this, User_registration_page.class);
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
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(User_Login_page.this, new OnCompleteListener<AuthResult>() {
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
                                            Intent intent = new Intent(User_Login_page.this, User_Homepage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                };
                                timer.start();

                            } else {
                                Toast.makeText(User_Login_page.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }
}