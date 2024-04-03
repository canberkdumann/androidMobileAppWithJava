package com.example.mymoviehome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymoviehome.models.MovieModel;

public class MovieDetails extends AppCompatActivity {

    private ImageView imageViewDetails;
    private TextView titleDetails,descriptionDetails;
    private RatingBar ratingBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageViewDetails = findViewById(R.id.imageViewDetails);
        titleDetails = findViewById(R.id.textViewDetails);
        descriptionDetails = findViewById(R.id.textViewDescription);
        ratingBarDetails = findViewById(R.id.ratingBarDetails);

        GetDataFromIntent();

    }


    private void GetDataFromIntent() {

        //when the user click to the movie
        if(getIntent().hasExtra("movie")){

            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            titleDetails.setText(movieModel.getTitle());
            descriptionDetails.setText(movieModel.getMovie_overview());
            ratingBarDetails.setRating((movieModel.getVote_average())/2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"
                    +movieModel.getPoster_path())
                    .into(imageViewDetails);



        }
    }
}