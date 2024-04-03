package com.example.mymoviehome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mymoviehome.adapters.MovieRecyclerView;
import com.example.mymoviehome.adapters.OnMovieListener;
import com.example.mymoviehome.models.MovieModel;
import com.example.mymoviehome.request.Servicey;
import com.example.mymoviehome.response.MovieSearchResponse;
import com.example.mymoviehome.utils.Credentials;
import com.example.mymoviehome.utils.MovieApi;
import com.example.mymoviehome.viewmodels.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApp extends AppCompatActivity implements OnMovieListener {

    //Recycler View
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;
    private Toolbar toolbar;


    //view model
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_app);

        //toolbar
       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

       toolbar.setLogo(R.drawable.movieicon);

        SetupSearchView();

        recyclerView = findViewById(R.id.recyclerView);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();

    }


    //observing any data change
    private void ObserveAnyChange() {

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels)
            {
                if(movieModels != null){
                    for (MovieModel movieModel:movieModels){

                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
                else
                {
                    Log.e("viewmodel","is empty");
                }

            }
        });

    }


    private void ConfigureRecyclerView(){

        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //loading next pages...
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextpage();
                }

            }
        });

    }

    @Override
    public void onMovieClick(int position) {
        //Toast.makeText(this,"The position " +position,Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie",movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    //get data from rest_Api and query results in recycler view
    private void SetupSearchView(){

          final SearchView searchView = findViewById(R.id.searchView);
          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {

                  movieListViewModel.searchMovieApi(
                          query,
                          1
                  );
                  return false;

              }

              @Override
              public boolean onQueryTextChange(String newText) {
                  return false;
              }
          });
    }


/*
    private void GetRetrofitResponse() {

        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Jack+Reacher" ,//this parameter will change when a film filtered or queried.
                        1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if(response.code() == 200)
                {
                    //Log.v("Tag","the response" +response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for(MovieModel movie:movies){
                        //final String releaseDate= movie.getRelease_date();
                        Log.v("Tag","Name:" + movie.getRelease_date());
                    }
                }

                else{

                    try {
                        Log.v("Tag","Error" +response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Log.e("Tag",t.getMessage());

            }
        });

    }


    private void  GetRetrofitResponseAccordingToID(){

        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieModel> responseCall = movieApi
                .getMovie(
                        550,
                        Credentials.API_KEY
                        );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if(response.code() == 200){
                    MovieModel movie = response.body();
                    Log.v("Tag","The Response" +movie.getTitle());
                }
                else {
                    try {
                        Log.v("Tag","Error" +response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });


    }

*/


}