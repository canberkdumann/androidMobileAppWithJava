package com.example.mymoviehome.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymoviehome.models.MovieModel;
import com.example.mymoviehome.request.MovieApiClient;

import java.util.List;

public class MovieRepository {


    private static MovieRepository instance;


    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){

        if(instance == null){
            instance = new MovieRepository();
        }

        return instance;


    }

    private MovieRepository(){

        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPopular(){
        return movieApiClient.getMoviesPopular();
    }

    //Calling the method in repository

    public void searchMovieApi(String query,int pageNumber){

        mQuery = query;
        mPageNumber = pageNumber;

        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchMoviePopular(int pageNumber){

        mPageNumber = pageNumber;

        movieApiClient.searchMoviesPopular( pageNumber);
    }


    public void searchNextPage(){

        searchMovieApi(mQuery,mPageNumber+1);
    }


}


