package com.example.sdiointerns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 4000;
    Animation top_anim,bottom_anim;
    ImageView logo;
    TextView logo_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        //Animation
        Context context;
        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_anim );
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        logo = findViewById(R.id.img_logo);
        logo_title = findViewById(R.id.logoname);

        logo.setAnimation(top_anim);
        logo_title.setAnimation(bottom_anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Registration.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(logo,"logo_img");
                pairs[1] = new Pair<View,String>(logo_title,"logo_text");
                Activity activity;
                View view;
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent,activityOptions.toBundle());
            }
        },SPLASH_TIME);

    }
}