package com.example.sdiointerns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registration extends AppCompatActivity {

    String uid;
    TextInputEditText city,Email, password, mobile,name;
    Button Submit;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    Task<Void> mDatabaseRef;
    String eml, pass, uname, mob,mcity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        name =  findViewById(R.id.edt_name);
        Email =  findViewById(R.id.edt_email_reg);
        password =  findViewById(R.id.edt_pass_reg);
        mobile =  findViewById(R.id.edt_phone);
        city =  findViewById(R.id.edt_city);
        Submit = findViewById(R.id.btn_register);

         Submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 eml = Email.getText().toString().trim();
                 pass = password.getText().toString().trim();
                 mob = mobile.getText().toString().trim();
                 uname = name.getText().toString().trim();
                 mcity = city.getText().toString().trim();
                 auth = FirebaseAuth.getInstance();


                 if (TextUtils.isEmpty(uname)) {
                     name.setError("Name Field can't be Empty");
                     return;
                 }

                 if (TextUtils.isEmpty(eml)) {
                    // Toast.makeText(Registration.this, "plz enter your Email ", Toast.LENGTH_SHORT).show();
                     Email.setError("Email Field can't be Empty");
                     return;
                 }
                 if (TextUtils.isEmpty(pass)) {
                     //Toast.makeText(Registration.this, "plz enter your password ", Toast.LENGTH_SHORT).show();
                     password.setError("Password Field can't be Empty");
                     return;
                 }

                 if (TextUtils.isEmpty(mob)) {
                     //Toast.makeText(Registration.this, "plz enter your mobile No. ", Toast.LENGTH_SHORT).show();
                     mobile.setError("Mobile Number can't be Empty");
                     return;
                 }

                 if (TextUtils.isEmpty(mcity)) {
                     //Toast.makeText(Registration.this, "plz enter your city. ", Toast.LENGTH_SHORT).show();
                     city.setError("City Field can't be Empty");
                     return;
                 }


                 auth.createUserWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             user data = new user(eml, pass, uname, mcity, mob);
                             mDatabaseRef = FirebaseDatabase.getInstance().getReference("register")
                                     .child(auth.getInstance().getCurrentUser().getUid())
                                     .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             Toast.makeText(Registration.this, "Registered SuccessFully in realtime", Toast.LENGTH_SHORT).show();
                                             Intent intent = new Intent(Registration.this, Login.class);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent);
                                         }
                                     });
                         }
                         else {
                             Toast.makeText(Registration.this, "Registration Failed \n" , Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
         });
    }
}