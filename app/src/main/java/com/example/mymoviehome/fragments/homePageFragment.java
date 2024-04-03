package com.example.mymoviehome.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mymoviehome.Login;
import com.example.mymoviehome.MainActivity;
import com.example.mymoviehome.MovieApp;
import com.example.mymoviehome.R;
import com.example.mymoviehome.ResetPassword;
import com.example.mymoviehome.fragmentHelper.eaStackHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class homePageFragment extends Fragment
{
    TextView verifyMsg;
    Button verifyEmail;
    FirebaseAuth auth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    private eaStackHelper meaStackHelper;
    private Toolbar toolbar2;
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.homepage_fragment,container,false);

        auth = FirebaseAuth.getInstance();



        toolbar2 = view.findViewById(R.id.toolbar2);
        toolbar2.setTitle("TV Shows and Movies guide");
        toolbar2.setSubtitle("Explore the beauty of tv shows and movies");
        toolbar2.setLogo(R.drawable.movieicon);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar2);
        verifyMsg = view.findViewById(R.id.verifyEmailMsg);
        verifyEmail = view.findViewById(R.id.verifyEmailBtn);
        //moviesButton = view.findViewById(R.id.moviesButton);

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);


        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.whiplash));
        sliderItems.add(new SliderItem(R.drawable.stutterisland));
        sliderItems.add(new SliderItem(R.drawable.heath));
        sliderItems.add(new SliderItem(R.drawable.vendetta));
        sliderItems.add(new SliderItem(R.drawable.blackswann));
        sliderItems.add(new SliderItem(R.drawable.interstellarrrr));
        sliderItems.add(new SliderItem(R.drawable.godfather));
        sliderItems.add(new SliderItem(R.drawable.godfather2));
        sliderItems.add(new SliderItem(R.drawable.inceptionn));
        sliderItems.add(new SliderItem(R.drawable.allpacino));
        sliderItems.add(new SliderItem(R.drawable.deniro));
        sliderItems.add(new SliderItem(R.drawable.bale));
        sliderItems.add(new SliderItem(R.drawable.gonegirll));
        sliderItems.add(new SliderItem(R.drawable.forrest));
        sliderItems.add(new SliderItem(R.drawable.ateternitys));
        sliderItems.add(new SliderItem(R.drawable.greenmile));
        sliderItems.add(new SliderItem(R.drawable.paris));
        sliderItems.add(new SliderItem(R.drawable.wilsmith));
        sliderItems.add(new SliderItem(R.drawable.directorrr));
        sliderItems.add(new SliderItem(R.drawable.scorsese));
        sliderItems.add(new SliderItem(R.drawable.davidlynch));
        sliderItems.add(new SliderItem(R.drawable.kubrick));
        sliderItems.add(new SliderItem(R.drawable.nolan));
        sliderItems.add(new SliderItem(R.drawable.kathryn));
        sliderItems.add(new SliderItem(R.drawable.hitchock));
        sliderItems.add(new SliderItem(R.drawable.tarantino));
        sliderItems.add(new SliderItem(R.drawable.coppola));
        sliderItems.add(new SliderItem(R.drawable.sofia));
        sliderItems.add(new SliderItem(R.drawable.fellini));
        sliderItems.add(new SliderItem(R.drawable.bergman));
        sliderItems.add(new SliderItem(R.drawable.tarkovsky));


        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 4000); //slide duration
            }
        });


        reset_alert = new AlertDialog.Builder(getActivity());
        inflater = this.getLayoutInflater();

        if(!auth.getCurrentUser().isEmailVerified()){
            verifyEmail.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
        }

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"Verification email was sent!",Toast.LENGTH_SHORT).show();
                        verifyEmail.setVisibility(View.GONE);
                        verifyMsg.setVisibility(View.GONE);
                    }
                });
            }
        });

        /*moviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MovieApp.class));

                //detail will open in same container.
                //eaStackHelper.getInstance().addAndHideFragment(eaStackHelper.movies,eaStackHelper.homepage);
            }
        }); */

      

        return view;
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);

    }

  /*  @Override
    public void onResume() {
        super.onResume();
        sliderHandler.removeCallbacks(sliderRunnable);

    }*/



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,4000);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //handle issues when fragment is off.

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu,menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.resetUserPassword){
            startActivity(new Intent(getActivity(), ResetPassword.class));
        }

        if(item.getItemId() == R.id.updateEmailMenu){
            View view = getLayoutInflater().inflate(R.layout.reset_pop,null);

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
                                    Toast.makeText(getActivity(),"Email updated.",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getActivity(),"Account deleted.",Toast.LENGTH_SHORT).show();
                                    auth.signOut();
                                    startActivity(new Intent(getContext(),Login.class));
                                    getActivity().finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"Account deleted.",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).setNegativeButton("Cancel",null)
                    .show();

        }

        if(item.getItemId() == R.id.logoutMenu){

            FirebaseAuth.getInstance().signOut();
            eaStackHelper.killInstance();
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
