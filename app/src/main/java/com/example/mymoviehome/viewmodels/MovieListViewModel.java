package com.example.mymoviehome.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviehome.models.MovieModel;
import com.example.mymoviehome.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //this class is used for ViewModel

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();


    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPopular(){
        return movieRepository.getPopular();
    }

    //Calling the method in view-model
    public void searchMovieApi(String query,int pageNumber){

        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePopular(int pageNumber){

        movieRepository.searchMoviePopular( pageNumber);
    }


    public void searchNextpage(){

        movieRepository.searchNextPage();

    }



}
