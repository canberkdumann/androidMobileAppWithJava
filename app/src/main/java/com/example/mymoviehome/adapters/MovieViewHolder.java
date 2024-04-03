package com.example.mymoviehome.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviehome.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


      //Widgets

      ImageView imageView;
      RatingBar ratingBar;

      OnMovieListener onMovieListener;



    public MovieViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;


        imageView = itemView.findViewById(R.id.movieImage);
        ratingBar = itemView.findViewById(R.id.ratingBar);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
