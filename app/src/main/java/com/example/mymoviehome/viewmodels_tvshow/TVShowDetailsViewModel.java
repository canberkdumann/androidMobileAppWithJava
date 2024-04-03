package com.example.mymoviehome.viewmodels_tvshow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.mymoviehome.database.TVShowsDatabase;
import com.example.mymoviehome.models_tvshow.TVShow;
import com.example.mymoviehome.repositories_tvshow.TVShowDetailsRepository;
import com.example.mymoviehome.response_tvshow.TVShowDetailResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TVShowsDatabase tvShowsDatabase;

    public TVShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowDetailResponse> getTVShowDetails(String tvShowId){

        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
        
    }

    public Completable addToWatchList(TVShow tvShow){
         return tvShowsDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowFromWatchlist(String tvShowId){

        return tvShowsDatabase.tvShowDao().getTVShowFromWatchlist(tvShowId);
    }

    public Completable removeTVShowFromWatchlist(TVShow tvShow){
        return tvShowsDatabase.tvShowDao().removeFromWatchList(tvShow);
    }


}
