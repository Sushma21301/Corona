package com.example.cvm.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cvm.Admin.Admin_login_page;
import com.example.cvm.R;
import com.example.cvm.User.User_Login_page;

public class Authentication_page extends AppCompatActivity {

   public  Button admin,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_page);

        admin=(Button)findViewById(R.id.admin_authentication);
        user=(Button)findViewById(R.id.user_authentication);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Authentication_page.this, Admin_login_page.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Authentication_page.this, User_Login_page.class);
                startActivity(intent);
            }
        });
    }
}