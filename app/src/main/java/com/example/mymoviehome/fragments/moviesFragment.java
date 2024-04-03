package com.example.mymoviehome.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviehome.MovieDetails;
import com.example.mymoviehome.R;
import com.example.mymoviehome.adapters.MovieRecyclerView;
import com.example.mymoviehome.adapters.OnMovieListener;
import com.example.mymoviehome.models.MovieModel;
import com.example.mymoviehome.viewmodels.MovieListViewModel;

import java.util.List;

public class moviesFragment extends Fragment implements OnMovieListener
{

    //Recycler View
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;
    private Toolbar toolbar;

    //view model
    private MovieListViewModel movieListViewModel;
    boolean isPopular = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movies_fragment,container,false);

        //toolbar
    /*    Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.movieicon);*/
        SetupSearchView(view);

        recyclerView = view.findViewById(R.id.recyclerView);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();

        movieListViewModel.searchMoviePopular(1);


        return view;
    }

    private void ObservePopularMovies() {

        movieListViewModel.getPopular().observe(getActivity(), new Observer<List<MovieModel>>() {
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


    //observing any data change
    private void ObserveAnyChange() {

        movieListViewModel.getMovies().observe(getActivity(), new Observer<List<MovieModel>>() {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

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

        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra("movie",movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category)
    {

    }

    //get data from rest_Api and query results in recycler view
    private void SetupSearchView(View view){

        final SearchView searchView = view.findViewById(R.id.searchView);
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

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });





    }


}
