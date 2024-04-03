package com.example.mymoviehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymoviehome.R;

public class contactFragment extends Fragment {

    EditText et_subject,et_message;
    Button emailButton;
    LottieAnimationView lottieAnimationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);

        et_subject = view.findViewById(R.id.emailSubject);
        et_message = view.findViewById(R.id.emailMessage);
        emailButton = view.findViewById(R.id.emailSendButton);
        lottieAnimationView = view.findViewById(R.id.contactAnimation);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString().trim();
                String message = et_message.getText().toString().trim();
                String email = "canberkduman1998@gmail.com";
                if (subject.isEmpty()) {
                    Toast.makeText(getActivity(), "Please add Subject", Toast.LENGTH_SHORT).show();
                } else if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Please add some Message", Toast.LENGTH_SHORT).show();
                } else {
                    String mail = "mailto:" + email +
                            "?&subject=" + Uri.encode(subject) +
                            "&body=" + Uri.encode(message);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(mail));
                    try {
                        startActivity(Intent.createChooser(intent, "Send Email.."));
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } );

            lottieAnimationView.playAnimation();

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



}
