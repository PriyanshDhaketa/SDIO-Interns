package com.example.foodtab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class register extends AppCompatActivity {

    String uid;
    EditText Email, password,conpass, mobile, otp;
    Button Submit, verify,resend;
    String no,ps,el;
    ProgressDialog pd;
    Map<String, String> data;
    SharedPreferences sp;
    FirebaseAuth auth;
    private String mVerificationId;
    LinearLayout registerLL, verifyLL;
    FirebaseFirestore firestore;
    Task<Void> mDatabaseRef;
    String eml, pass, conpassword, mob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerLL = findViewById(R.id.registerLL);
        verifyLL = findViewById(R.id.otpLL);
        Email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        conpass = findViewById(R.id.conpassword);
        mobile = findViewById(R.id.mobile);
        Submit = findViewById(R.id.Submit);
        resend = findViewById(R.id.resend);
        verify = findViewById(R.id.verifyOTP);
        otp = findViewById(R.id.otp);

        sp = getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);

        EditText text = (EditText) findViewById(R.id.mobile);
        PhoneNumberUtils.formatNumber(text.getText().toString());

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
       // mDatabaseRef = FirebaseDatabase.getInstance().getReference("register");

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("Email") && sp.contains("password")){
            startActivity(new Intent(register.this,login.class));
            finish();   //finish current activity
        }


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eml = Email.getText().toString().trim();
                pass = password.getText().toString().trim();
                mob = mobile.getText().toString().trim();
                conpassword = conpass.getText().toString().trim();


                if (TextUtils.isEmpty(eml)) {
                    Toast.makeText(register.this, "plz enter your Email ", Toast.LENGTH_SHORT).show();
                    Email.setError(" plz enter your Email");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(register.this, "plz enter your password ", Toast.LENGTH_SHORT).show();
                    password.setError("Password Length Must Be  Atleast Six");
                    return;
                }
                if (!pass.equals(conpassword)) {
                    password.setError("Password Did Not Match");
                    return;
                }
                if (TextUtils.isEmpty(mob)) {
                    Toast.makeText(register.this, "plz enter your mobile No. ", Toast.LENGTH_SHORT).show();
                    mobile.setError("Length Must Be  Exactly 10 digit");
                    return;
                }


                no = mobile.getText().toString();
                //    ps = password.getText().toString();
                //  el = Email.getText().toString();
                //Intent intent = new Intent(MainActivity.this, otp.class);
                //intent.putExtra("mobile", no);
                //intent.putExtra("password", ps);
                //intent.putExtra("Email", el);
                //startActivity(intent);

                registerLL.setVisibility(View.INVISIBLE);
                verifyLL.setVisibility(View.VISIBLE);

                sendVerificationCode(no);


                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String code = otp.getText().toString().trim();
                        if (code.isEmpty() || code.length() < 6) {
                            otp.setError("Enter valid code");
                            otp.requestFocus();
                            return;
                        }

                        //verifying the code entered manually
                        verifyVerificationCode(code);

                    }
                });


            }
        });


    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            //startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            //finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    public void loginLink(View view) {
        startActivity(new Intent(getApplicationContext(), login.class));
    }
    public void loginLink1(View view) {
        startActivity(new Intent(getApplicationContext(), login.class));
    }
    public void resend(View view) {
        sendVerificationCode(no);
        // startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }





    private void sendVerificationCode(String no) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + no,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        pd = new ProgressDialog(register.this);
        pd.setTitle("Registering....");
        pd.show();


        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity


                            auth.createUserWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        eml = Email.getText().toString().trim();
                                        pass = password.getText().toString().trim();
                                        mob = mobile.getText().toString().trim();
                                        conpassword = conpass.getText().toString().trim();
                                        uid = auth.getCurrentUser().getUid();
                                        DocumentReference reference = firestore.collection("").document(uid);
                                        Map<String, Object> user = new HashMap<>();

                                        user.put("Email", eml);
                                        user.put("password", pass);
                                        user.put("mobile", mob);

                                        reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(register.this, "Registered SuccessFully", Toast.LENGTH_SHORT).show();

                                            //    Intent intent = new Intent(register.this, login.class);
                                              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                              //  startActivity(intent);

                                                //sendVerificationEmail();


                                                //   startActivity(new Intent(getApplicationContext(), otp.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(register.this, "Registration Failed \n" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    } else {

                                       // Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Toast.makeText(register.this, "" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        auth.createUserWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    user data = new user(eml, pass, mob);
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("sign_up")
                            .child(auth.getInstance().getCurrentUser().getUid())
                            .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(register.this, "Registered SuccessFully in realtime", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(register.this, login.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                    sendVerificationEmail();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(register.this, "Registration Failed \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
               else {
                   Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(register.this, register.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
               }
            }

        });
    }

    public void login(View view) {
        Intent intent = new Intent(register.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
