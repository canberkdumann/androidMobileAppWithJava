package com.example.mymoviehome.viewmodels_tvshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviehome.repositories_tvshow.SearchTVShowRepository;
import com.example.mymoviehome.response_tvshow.TVShowResponse;

public class SearchViewModel extends ViewModel {


    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel(){
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowResponse> searchTVShow(String query, int page){

         return  searchTVShowRepository.searchTVShow(query, page);
    }
}
