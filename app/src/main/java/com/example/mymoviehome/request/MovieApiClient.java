package com.example.mymoviehome.request;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymoviehome.AppExecuters;
import com.example.mymoviehome.models.MovieModel;
import com.example.mymoviehome.response.MovieSearchResponse;
import com.example.mymoviehome.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //live data for search
    private MutableLiveData<List<MovieModel>> mMovies ;
    private static MovieApiClient instance;

    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    //live data for popular movies
    private MutableLiveData<List<MovieModel>> mMoviesPopular ;

    private RetrieveMoviesRunnablePoPular retrieveMoviesRunnablePopular;

    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){

        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){

        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPopular(){

        return mMoviesPopular;
    }

    public void searchMoviesApi(String query,int pageNumber){

        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecuters.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecuters.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call

                myHandler.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);



    }

    public void searchMoviesPopular(int pageNumber){

        if(retrieveMoviesRunnablePopular != null){
            retrieveMoviesRunnablePopular = null;
        }

        retrieveMoviesRunnablePopular = new RetrieveMoviesRunnablePoPular(pageNumber);

        final Future myHandler2 = AppExecuters.getInstance().networkIO().submit(retrieveMoviesRunnablePopular);

        AppExecuters.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call

                myHandler2.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);



    }

     private class RetrieveMoviesRunnable implements Runnable {

         private String query;
         private int pageNumber;
         boolean cancelRequest;

         public RetrieveMoviesRunnable(String query, int pageNumber) {
             this.query = query;
             this.pageNumber = pageNumber;
             cancelRequest = false;
         }


         @Override
         public void run() {
             //Getting the response object
             try {
                 Response response = getMovies(query,pageNumber).execute();
                 if (cancelRequest) {
                     return;
                 }

                 if (response.code() == 200) {
                     List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                     if (pageNumber == 1) {
                         mMovies.postValue(list);

                     } else {
                         List<MovieModel> currentMovies = mMovies.getValue();
                         currentMovies.addAll(list);
                         mMovies.postValue(currentMovies);
                     }

                 } else {
                     String error = response.errorBody().string();
                     Log.v("Tag", "Error" + error);
                     mMovies.postValue(null);
                 }

             } catch (IOException e) {
                 e.printStackTrace();
                 mMovies.postValue(null);
             }
         }
             //Search method/query
         private Call<MovieSearchResponse> getMovies(String query,int pageNumber){
             return Servicey.getMovieApi().searchMovie(
                     Credentials.API_KEY,
                     query,
                     pageNumber
             );

         }

         private void cancelRequest() {

             Log.v("Tag", "Cancelling Search Request");
             cancelRequest = true;

         }

    }

     private class RetrieveMoviesRunnablePoPular implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePoPular(int pageNumber) {

            this.pageNumber = pageNumber;
            cancelRequest = false;
        }


        @Override
        public void run() {
            //Getting the response object
            try {
                Response response2 = getMoviesPopular(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }

                if (response2.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {
                        mMoviesPopular.postValue(list);

                    } else {
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }

                } else {
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error" + error);
                    mMoviesPopular.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPopular.postValue(null);
            }
        }
        //Search method/query
        private Call<MovieSearchResponse> getMoviesPopular(int pageNumber){
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,

                    pageNumber
            );

        }

        private void cancelRequest() {

            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;

        }

    }

}
