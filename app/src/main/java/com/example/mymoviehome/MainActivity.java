package com.example.mymoviehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviehome.fragmentHelper.eaStackHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView verifyMsg;
    Button verifyEmail,moviesButton;
    FirebaseAuth auth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    FrameLayout keepFragment;
    BottomNavigationView bottomNavigation;

    private Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eaStackHelper.init(this);

        keepFragment= findViewById(R.id.keepFragment);
        bottomNavigation= findViewById(R.id.bottomNavigation);

        eaStackHelper.getInstance().addFragment(eaStackHelper.homepage);
        eaStackHelper.getInstance().addAndHideFragment(eaStackHelper.movies,eaStackHelper.movies);
        eaStackHelper.getInstance().addAndHideFragment(eaStackHelper.favourites,eaStackHelper.favourites);
        eaStackHelper.getInstance().addAndHideFragment(eaStackHelper.contact,eaStackHelper.contact);


        //menu clicker
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    //handle clicks
                    case R.id.firstNav:
                        eaStackHelper.getInstance().showFragment(eaStackHelper.homepage);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.movies);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.favourites);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.contact);
                        break;

                    case R.id.secondNav:
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.homepage);
                        eaStackHelper.getInstance().showFragment(eaStackHelper.movies);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.favourites);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.contact);
                        break;

                    case R.id.thirdNav:
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.homepage);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.movies);
                        eaStackHelper.getInstance().showFragment(eaStackHelper.favourites);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.contact);

                        break;
                    case R.id.fourthNav:
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.homepage);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.movies);
                        eaStackHelper.getInstance().hideFragment(eaStackHelper.favourites);
                        eaStackHelper.getInstance().showFragment(eaStackHelper.contact);

                        break;
                }
                return false;
            }
        });


       //BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
       //  NavController navController = Navigation.findNavController(this,  R.id.keepFragment);
       // NavigationUI.setupWithNavController(bottomNavigationView, navController);



      /*  BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.firstNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.secondNav:
                       startActivity(new Intent(getApplicationContext()
                                    ,MovieApp.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.firstNav:
                       return true;
                   case R.id.thirdNav:
                       startActivity(new Intent(getApplicationContext()
                               ,MovieApp.class));
                       overridePendingTransition(0,0);
                       return true;
               }
                return false;

            }
        });*/





    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

  /*  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.resetUserPassword){
          startActivity(new Intent(getApplicationContext(),ResetPassword.class));
        }

        if(item.getItemId() == R.id.updateEmailMenu){
            View view = inflater.inflate(R.layout.reset_pop,null);

            reset_alert.setTitle("Update email?")
                    .setMessage("Please enter new email address.")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //validate the email address and send the reset link
                            EditText email = view.findViewById(R.id.resetEmailPop);
                            if(email.getText().toString().isEmpty()){
                                email.setError("Required field!");
                                return;
                            }

                            FirebaseUser user = auth.getCurrentUser();
                            user.updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,"Email updated.",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });



                        }
                    }).setNegativeButton("Cancel",null)
                    .setView(view)
                    .create().show();

        }

        if(item.getItemId() == R.id.deleteAccountMenu){
            reset_alert.setTitle("Delete account permanently?")
            .setMessage("Are you sure?")
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    FirebaseUser user = auth.getCurrentUser();
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this,"Account deleted.",Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Account deleted.",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).setNegativeButton("Cancel",null)
            .create().show();

        }

        return super.onOptionsItemSelected(item);
    }*/
}