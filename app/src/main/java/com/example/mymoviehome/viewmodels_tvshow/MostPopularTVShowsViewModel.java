package com.example.mymoviehome.viewmodels_tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviehome.repositories_tvshow.MostPopularTVShowsRepository;
import com.example.mymoviehome.response_tvshow.TVShowResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel(){

        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();

    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){

        return  mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }


}
