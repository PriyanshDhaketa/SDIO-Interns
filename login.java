package com.example.foodtab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class login extends AppCompatActivity {

    String uid;
    EditText Email, password;
    Button Submit;
    Map<String, String> data;
    SharedPreferences sp;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        Submit = findViewById(R.id.Submit);
        //signInButton = findViewById(R.id.sign_in_button);

        sp = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);

        Log.d("=======", "onCreate: "+sp.getString("Email",""));


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

//if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("Email") && sp.contains("password")){
            startActivity(new Intent(login.this,Home.class));
            finish();   //finish current activity
        }



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
//                loginCheck();
                final String eml = Email.getText().toString().trim();
                final String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(eml)) {
                    Toast.makeText(login.this, "plz enter your name ", Toast.LENGTH_SHORT).show();
                    Email.setError(" plz enter your name");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(login.this, "plz enter your password ", Toast.LENGTH_SHORT).show();
                    password.setError("Password Length Must Be  Atleast Six");
                    return;
                }
                auth.signInWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //   Toast.makeText(Activity1.this, "This is not a Verified account firstly please verify it with verification link than login   \n", Toast.LENGTH_SHORT).show();
                            sp = PreferenceManager.getDefaultSharedPreferences(login.this.getApplicationContext());
                            sp = getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
                            SharedPreferences.Editor e = sp.edit();
                            e.putString("Email", eml);
                            e.putString("password", pass);
                            e.commit();
                            // startActivity(new Intent(getApplicationContext(), Activity2.class));
                          /*  Intent intent = new Intent(Activity1.this, Activity2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/
                            checkIfEmailVerified();

                       /*     NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Activity1.this)
                                    .setSmallIcon(R.drawable.ic_sms)
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setContentTitle("My Application")
                                    .setContentText("Hi, you have successfully logIn");
                            // Set the intent to fire when the user taps on notification.
                            Intent resultIntent = new Intent(Activity1.this, Activity2.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(Activity1.this, 0, resultIntent, 0);
                            mBuilder.setContentIntent(pendingIntent);
                            // Sets an ID for the notification
                            int mNotificationId = 001;
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            // It will display the notification in notification bar
                            notificationManager.notify(mNotificationId, mBuilder.build());
*/
                        } else {
                            Toast.makeText(login.this, "Login Failed \n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            Toast.makeText(login.this, "Login Successful \n", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(login.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(login.this)
                    .setSmallIcon(R.mipmap.appicon_foreground)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle("Food Tab")
                    .setContentText("Hi, you have successfully logIn");
            // Set the intent to fire when the user taps on notification.
            Intent resultIntent = new Intent(login.this, Home.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(login.this, 0, resultIntent, 0);
            mBuilder.setContentIntent(pendingIntent);
            // Sets an ID for the notification
            int mNotificationId = 001;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // It will display the notification in notification bar
            notificationManager.notify(mNotificationId, mBuilder.build());

            // user is verified, so you can finish this activity or send user to activity which you want.
            finish();
            //       Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Toast.makeText(login.this, "This is not a Verified account firstly please verify it with verification link than login   \n", Toast.LENGTH_LONG).show();
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }

    /*public void loginLink(View view){
        startActivity(new Intent(getApplicationContext(), forgetpass.class));
    }*/


    public void signup(View view) {
        Intent intent = new Intent(login.this, register.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void forget(View view) {
        Intent intent = new Intent(login.this, forgetpass.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
