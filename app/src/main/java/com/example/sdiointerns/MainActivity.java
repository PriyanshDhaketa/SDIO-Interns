package com.example.sdiointerns;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.animation.AnimationUtils;

import static android.view.animation.AnimationUtils.loadAnimation;
import static com.google.android.material.animation.AnimationUtils.*;
public class MainActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView tv1,tv2;
    private static final int DELAY= 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topAnim=loadAnimation(this,R.anim.top_animation);
        bottomAnim=loadAnimation(this,R.anim.bottom_animation);
        image=(ImageView)findViewById(R.id.imageView);
        tv1=(TextView)findViewById(R.id.t1);
        tv2=(TextView)findViewById(R.id.t2);
        image.setAnimation(topAnim);
        tv1.setAnimation(bottomAnim);
        tv2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent= new Intent(MainActivity.this,NextActivity.class);
                //startActivity(intent);
                //finish();
            }
        },DELAY);
    }
}