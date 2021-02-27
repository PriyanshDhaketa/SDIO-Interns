package com.example.sdiointerns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    TextInputEditText Email, password;
    Button btn_gotoregister,btn_login;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Email =  findViewById(R.id.edt_email);
        password =  findViewById(R.id.edt_pass);

        btn_gotoregister = findViewById(R.id.btn_go_to_register);
        btn_login = findViewById(R.id.btn_login);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        //THIS IS THE METHOD TO JUMP TO REGISTRATION PAGE
        btn_gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });




        //THIS IS THE METHOD TO LOGIN TO A ID
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String eml = Email.getText().toString().trim();
                final String pass = password.getText().toString().trim();
                System.out.println(eml);
                if (TextUtils.isEmpty(eml)) {
                    Toast.makeText(Login.this, "plz enter your Email ", Toast.LENGTH_SHORT).show();
                    Email.setError("Please enter your Email");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(Login.this, "Password Field can't be Empty", Toast.LENGTH_SHORT).show();
                    password.setError("Password Length Must Be  Atleast Six");
                    return;
                }

                auth.signInWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(getApplicationContext(), Home.class));
                            Intent intent = new Intent(Login.this, Home.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Login Failed \n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}



