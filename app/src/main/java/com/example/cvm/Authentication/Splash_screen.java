package com.example.cvm.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.cvm.R;

public class Splash_screen extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img = (ImageView) findViewById(R.id.logo);
        Animation vecl = (Animation) AnimationUtils.loadAnimation(Splash_screen.this, R.anim.mytransition);
        img.startAnimation(vecl);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(Splash_screen.this, Authentication_page.class);
                    startActivity(intent);
                    finish();
                }
            }

                }
                ;
                timer.start();
            }

}