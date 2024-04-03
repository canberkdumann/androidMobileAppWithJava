package com.example.mymoviehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviehome.fragmentHelper.eaStackHelper;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    Button CreateAccount,loginBtn,forgetPasswordBtn;
    ImageView imageView2;

    EditText username,password;
    TextInputLayout textInputLayout2,textInputLayout;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    Animation downtoup;
    Animation uptodown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        imageView2 = findViewById(R.id.imageView2);

        CreateAccount = findViewById(R.id.CreateAccount);
        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);
        textInputLayout2= findViewById(R.id.textInputLayout2);
        textInputLayout= findViewById(R.id.textInputLayout);

        downtoup = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.downtoup);
        uptodown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.uptodown);


        imageView2.setAnimation(uptodown);
       
        CreateAccount.setAnimation(downtoup);
        loginBtn.setAnimation(downtoup);
        forgetPasswordBtn.setAnimation(downtoup);
        username.setAnimation(downtoup);
        password.setAnimation(downtoup);
        textInputLayout2.setAnimation(downtoup);
        textInputLayout.setAnimation(downtoup);

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });



        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = inflater.inflate(R.layout.reset_pop,null);

                reset_alert.setTitle("Reset forgot password?")
                        .setMessage("Please enter your email to get password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //validate the email address and send the reset link
                                 EditText email = view.findViewById(R.id.resetEmailPop);
                                 if(email.getText().toString().isEmpty()){
                                     email.setError("Required field!");
                                     return;
                                 }

                                 firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                         Toast.makeText(Login.this,"Reset email was sent!",Toast.LENGTH_SHORT).show();

                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                     }
                                 });

                            }
                        }).setNegativeButton("Cancel",null)
                        .setView(view)
                        .create().show();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked","here");
                if(username.getText().toString().isEmpty()){
                    username.setError("Email is missing!");
                    return;
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("Password is missing!");
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login is successfull

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.e("error",e.getMessage());
                    }
                });
            }
        });






    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }
    }
}